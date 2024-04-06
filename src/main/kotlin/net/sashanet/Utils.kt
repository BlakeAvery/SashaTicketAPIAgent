package net.sashanet

import kotlinx.serialization.json.Json
import net.sashanet.ticketobj.*
import java.text.DateFormat
import java.util.Date
import java.time.*
import java.time.format.*

/**
 * Utils: Does things for our APIAgent. Speaks to the APIAgent class to do its dirty work sometimes.
 * Very sparse right now.
 */

val apiAgent = APIAgent()
class Utils {
    var lastTicketProcessed: Int = 0
    fun parseOrgs(org: String): String {
        when(org) {
            "fgss" -> return "FGSS"
            "beverly" -> return "Beverly"
            "southview" -> return "Southview"
            "thelma" -> return "Thelma"
            "pompano" -> return "Pompano"
            else -> return "Off-site"
        }
    }
    fun parseMediaTypes(mediaType: String?): String {
        when(mediaType) {
            "tv_show" -> return "TV show"
            "movie" -> return "Movie"
            else -> return "Other"
        }
    }
    fun parseJsonDate() {

    }
    fun generatePassword(length: Int): String {
        val possibleChars: CharSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*"
        var ret = ""
        for(i in 0..length - 1) {
            ret += possibleChars.random()
        }
        println(ret)
        return ret
    }
    suspend fun getUserEmailFromID(id: Int): String {
        println("Searching user...")
        var search = apiAgent.searchUser("id:$id")
        if(search != "[]") {
            println("User found! Converting...")
            var user = Json.decodeFromString<List<User>>(search)
            return user[0].email!!
        } else {
            println("No user found. returning default value")
            return "noreply@sashanet.net"
        }
    }
    suspend fun patchMediaReqTitle(webhook: MediaRequestWebhook) {
        var modTitle = if(webhook.console != "") {
            "${webhook.mediaName} (${webhook.console} game)"
        } else {
            "${webhook.mediaName} (${parseMediaTypes(webhook.mediaType)})"
        }
        val ticket = TicketAPIObj(id = webhook.internalId?.toInt(),
            title = modTitle)
        apiAgent.modifyTicket(ticket)
    }
    suspend fun offboarding(webhook: OffboardingWebhook) {
        println("Getting ticket...")
        var offboardingTicket = apiAgent.getTicket(webhook.id)
        if(webhook.id == lastTicketProcessed) {
            println("Request id ${webhook.id}/#REQ-${offboardingTicket!!.number} has already been received by the system.")
        } else {
            lastTicketProcessed = webhook.id
            offboardingTicket!!.title = "Offboarding Request for ${offboardingTicket.accessFname} ${offboardingTicket.accessLname}"
            apiAgent.modifyTicket(offboardingTicket)
            //Now that we have the ticket, check the fields for what systems require offboarding.
            //On prem users get a ticket to IT Security under IT Incident to block network device access as well.
            if(offboardingTicket.offboardOnSite == true && offboardingTicket.accessOrg != "offsite") {
                val netblockTicket = apiAgent.newTicket(TicketAPIObj(
                    title = "SashaNet Offboarding: ${offboardingTicket.accessFname} ${offboardingTicket.accessLname} On-prem network access",
                    groupId = 8, //IT Security
                    customerId = constants.apiAgentUserID,
                    type = "SashaNet Offboarding",
                    dueDate = offboardingTicket.dueDate,
                    article = Article(
                        internal = false,
                        sender = "Customer",
                        contentType = "text/html",
                        type = "web",
                        body = """
                        This is an automatically generated ticket created as part of offboarding ticket #REQ-${offboardingTicket.number}.<br/>
                        Please disable site-level network device access for all devices associated with the following user:<br/>
                        ${offboardingTicket.accessFname} ${offboardingTicket.accessLname}<br/>
                        Site: ${offboardingTicket.accessOrg}<br/>
                        Please complete this by the due date listed on the parent ticket.<br/>
                        If this ticket is overdue, please consider it urgent and therefore high priority.
                    """.trimIndent()
                    ),
                    accessFname = offboardingTicket.accessFname,
                    accessLname = offboardingTicket.accessLname,
                    accessEmail = offboardingTicket.accessEmail
                ))
                apiAgent.newTicketLink(offboardingTicket.id!!, netblockTicket?.id!!, "parent")
            }
            for(i in offboardingTicket.accessOffboardSystems!!.indices) {
                var tempAccessOrg: String?
                var currentSystem: String = offboardingTicket.accessOffboardSystems!![i]
                var ticket = apiAgent.newTicket(TicketAPIObj(
                    title = "SashaNet Offboarding: Remove ${currentSystem} access for ${offboardingTicket.accessFname} ${offboardingTicket.accessLname}",
                    groupId = 9, //IT Access Management
                    customerId = constants.apiAgentUserID,
                    type = "Access Request",
                    dueDate = offboardingTicket.dueDate,
                    article = Article(
                        internal = false,
                        sender = "Customer",
                        contentType = "text/html",
                        type = "web",
                        body = """
                        This is an automatically generated ticket created as part of offboarding ticket #REQ-${offboardingTicket.number}.<br/>
                        Please disable/remove access to the ${currentSystem} application for the following user:<br/>
                        ${offboardingTicket.accessFname} ${offboardingTicket.accessLname} (${offboardingTicket.accessEmail}<br/>
                        Please complete this by the due date listed on the parent ticket.<br/>
                        If this ticket is overdue, please consider it urgent and therefore high priority.
                    """.trimIndent()
                    ),
                    accessAction = "remove",
                    accessSystem = currentSystem,
                    accessFname = offboardingTicket.accessFname,
                    accessLname = offboardingTicket.accessLname,
                    accessOrg = if(currentSystem == "sashaticket") offboardingTicket.accessOrg else null,
                    accessEmail = offboardingTicket.accessEmail,
                    accessUser = offboardingTicket.accessEmail
                ))
                apiAgent.newTicketLink(offboardingTicket.id!!, ticket?.id!!, "parent")
            }
        }

    }
}