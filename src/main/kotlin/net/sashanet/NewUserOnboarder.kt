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

val client = HttpClient()
val secrets = Secrets()
val utils = Utils()
/**
 * NewUserOnboarder: Test class that implements everything we need for our workflow by hand.
 * This is going to be inserted into utils soon :)
 */
class NewUserOnboarder {
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
                email =  webhook.email,
                firstname = webhook.firstName,
                lastname = webhook.lastName,
                organization = utils.parseOrgs(webhook.org),
                roles = arrayOf("SashaNet Agent").toList(),
                phone = webhook.phoneNumber, //TODO: convert phone number to E.164 using libphonenumber
                mobile = "",
                password = "SashaNet1!"
            )
            val sendUser = client.post {
                url {
                    protocol = URLProtocol.HTTP
                    host = "sashaticketv2.net"
                    appendEncodedPathSegments("/api/v1/users")
                }
                headers {
                    append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                    append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
                }
                val send = Json.encodeToString(newGuy)
                println(send)
                contentType(ContentType.Application.Json)
                setBody(send)
            }
            when(sendUser.status.value) {
                in 200..299 -> {
                    println("New user provisioned :) Here is their raw JSON for your observation.")
                    println(sendUser.bodyAsText())
                    println("Converting JSON to User object...")
                    val weevil = Json.decodeFromString<User>(sendUser.bodyAsText())
                    //we move the ticket over to the new user next via API
                    val reassignAccessRequest = client.put {
                        url {
                            protocol = URLProtocol.HTTP
                            host = "sashaticketv2.net"
                            appendEncodedPathSegments("/api/v1/tickets/${webhook.internalId}")
                        }
                        headers {
                            append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                            append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
                        }
                        val response = """
                            { 
                                "id": ${webhook.internalId},
                                "customer_id": ${weevil.id},
                                "state": "pending close"
                            }
                        """.trimIndent()
                        setBody(response)
                    }
                    when(reassignAccessRequest.status.value) {
                        in 200..299 -> {
                            println("Ticket updated to new user.")
                        }
                        else -> {
                            println("${reassignAccessRequest.status}")
                            println(reassignAccessRequest.bodyAsText())
                        }
                    }
                }
                in 300..599 -> {
                    println("${sendUser.status}")
                    println(sendUser.bodyAsText())
                }
            }
        } else {
            println("No action needed. User account not provisioned. This ticket should be rejected.")
        }
    }
}