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
                apiAgent.modifyTicket(TicketAPIObj(
                    id = webhook.internalId.toInt(),
                    customerId = newUser.id,
                    //stateId = 3,
                    article = Article(
                        internal = true,
                        sender = "System",
                        type = "email",
                        to = newUser.email,
                        contentType = "text/html",
                        subject = "Welcome to SashaTicket, ${newUser.firstname} :3",
                        body = """
                            Hello ${newUser.firstname},<br/><br/>
                            This email is to let you know that an account has been created for you on SashaTicket!<br/>
                            SashaTicket is your one source to request anything from SashaNet's team, up to and including Sasha herself :3<br/><br/>
                            Your login information is as follows. Please be sure to change the password shown as soon as possible.<br/>
                            This password is automatically generated, and should not be considered secure.<br/><br/>
                            Username/login: ${newUser.login}<br/>
                            Password: ${newGuy.password}<br/><br/>
                            If you would also like to receive SMS notifications/communications on tickets, please let us know by responding to this email,<br/>
                            or by placing a SMS Opt-In ticket on SashaTicket.<br/><br/>
                            Log in <a href=http://99.38.119.115>here</a>!<br/>
                            (Or if you happen to be on site at SashaNet's main Beverly location use <a href=http://sashaticketv2.net>this link</a> instead :3)<br/><br/>
                            SashaTicket System<br/>
                            Access provisioned under #REQ-${webhook.ticketNumber} by ${constants.userAgent}<br/>
                        """.trimIndent().trim('\n')
                    )
                ))
            }
        }
    }
}