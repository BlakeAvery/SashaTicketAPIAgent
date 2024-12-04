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
import java.util.Date
import kotlin.text.get

private val client = HttpClient()
/**
 * NewUserOnboarder: Test class that implements everything we need for our workflow by hand.
 * This is inserted into Utils and will be deprecated soon :)
 */
class NewUserOnboarder() {
    suspend fun createUser(webhook: AccessRequestWebhook) {
        println(apiAgent)
        val ticket = TicketAPIObj(
            id = webhook.internalId.toInt(),
            ownerId = null,
            article = Article(
                internal = true,
                sender = "Agent",
                contentType = "text/html",
                type = "note",
                body = "Processing by ${constants.userAgent} begins at ${Date()}."
            )
        )
        apiAgent.modifyTicket(ticket)
        // First thing is to check if our user exists. If it does, we don't make it.
        val searchResponse = apiAgent.searchUser("email.keyword:${webhook.email}")
        if(searchResponse.size == 0) { //we proceed
            val newGuy = NewUser(
                login = webhook.email,
                email = webhook.email,
                firstname = webhook.firstName,
                lastname = webhook.lastName,
                organization = utils.parseOrgs(webhook.org),
                roles = arrayOf("SashaNet Agent").toList(),
                phone = webhook.phoneNumber, //TODO: convert phone number to E.164 using libphonenumber
                mobile = "",
                password = utils.generatePassword(8)
            )
            val newUser = apiAgent.createUser(newGuy)
            if(newUser != null) {
                apiAgent.modifyTicket(TicketAPIObj(
                    id = webhook.internalId.toInt(),
                    state = "closed",
                    ownerId = constants.apiAgentUserID,
                    article = Article(
                        internal = true,
                        sender = "System",
                        type = "email",
                        to = newUser.email,
                        contentType = "text/html",
                        subject = "Welcome to SashaTicket, ${newUser.firstname} :3",
                        body = """
                            <h4>Hello ${newUser.firstname},<br/><br/></h4>
                            <p>This email is to let you know that an account has been created for you on SashaTicket!<br/>
                            SashaTicket is your one source to request anything from SashaNet's team, up to and including Sasha herself :3<br/><br/></p>
                            <p>Your login information is as follows. Please be sure to change the password shown as soon as possible.<br/>
                            This password is automatically generated, and should not be considered secure.<br/><br/></p>
                            Username/login: ${newUser.login}<br/>
                            Password: ${newGuy.password}<br/><br/>
                            <p>If you would also like to receive SMS notifications/communications on tickets, please let us know by responding to this email.<br/><br/></p>
                            Log in <a href=https://99.38.119.115>here</a> (The site is safe. We do not register with any certificate authority.)<br/>
                            (Or if you happen to be on site at SashaNet's main Beverly location or VPNed in use <a href=https://sashaticketv2.net>this link</a> instead :3)<br/><br/>
                            SashaTicket System<br/>
                            Access provisioned under #REQ-${webhook.ticketNumber} by ${constants.userAgent}<br/>
                        """.trimIndent().trim('\n')
                    )
                ))
                apiAgent.modifyTicket(TicketAPIObj(
                    id = webhook.internalId.toInt(),
                    article = Article(
                        internal = true,
                        sender = "Agent",
                        type = "note",
                        contentType = "text/html",
                        body = "Processing by ${constants.userAgent} ends at ${Date()}."
                    )
                ))
            }
        } else {
            println("No action taken. Mark this as rejected and close ticket.")
            var originalRequestorEmail = utils.getUserEmailFromID(webhook.originalRequester.toInt())
            val ticket = TicketAPIObj(
                id = webhook.internalId.toInt(),
                state = "rejected",
                article = Article(
                    internal = false,
                    sender = "Agent",
                    type = "email",
                    to = originalRequestorEmail,
                    contentType = "text/html",
                    subject = "Access for ${webhook.email} rejected",
                    body = """
                        <h4>Hello,</h4>
                        <p>This email is to notify you that your access request for ${webhook.firstName} ${webhook.lastName} was rejected
                        because the account already likely exists. If you believe this was an error, please resubmit the request referencing this
                        ticket number: ${webhook.ticketNumber}.</p><br/>
                        SashaTicket System<br/>
                        Request rejected by ${constants.userAgent} at ${Date()}<br/>
                    """.trimIndent()
                )
            )
            apiAgent.modifyTicket(ticket)
        }
    }
}