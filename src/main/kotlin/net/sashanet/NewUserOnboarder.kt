package net.sashanet

import AccessRequestWebhook
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.util.*
import io.ktor.server.util.url
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.sashanet.ticketobj.*
import kotlin.text.get

private val client = HttpClient()
/**
 * NewUserOnboarder: Test class that implements everything we need for our workflow by hand.
 * This is going to be inserted into utils soon :)
 */
class NewUserOnboarder() {
    suspend fun createUser(webhook: AccessRequestWebhook, constants: Constants) {
        // First thing is to check if our user exists. If it does, we don't make it.
        val searchNewUser  = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = "sashaticketv2.net"
                appendEncodedPathSegments("/api/v1/users/search?query=email.keyword:${webhook.email}&limit=10")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, constants.userAgent)
            }
        }
        val searchResponse: String = searchNewUser.body()
        println(searchResponse)
        if(searchResponse == "[]") { //we proceed
            val newGuy = NewUser(
                login = webhook.email,
                email = webhook.email,
                firstname = webhook.firstName,
                lastname = webhook.lastName,
                organization = utils.parseOrgs(webhook.org),
                roles = arrayOf("SashaNet Agent").toList(),
                phone = webhook.phoneNumber, //TODO: convert phone number to E.164 using libphonenumber
                mobile = "",
                password = "SashaNet1!" //TODO: Randomly generate this password using Utils helper function
            )
            val newUser = apiAgent.createUser(newGuy)
            if(newUser != null) {
                apiAgent.modifyTicket(TicketAPIObj(id = webhook.internalId.toInt(), customerId = newUser.id, stateId = 4))
            }
        }
    }
}