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
            else -> return "Beverly"
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
        var modTitle = if(webhook.console != null) {
            "${webhook.mediaName} (${webhook.console} game)"
        } else {
            "${webhook.mediaName} (${parseMediaTypes(webhook.mediaType)})"
        }
        val ticket = TicketAPIObj(id = webhook.internalId?.toInt(),
            title = modTitle)
        apiAgent.modifyTicket(ticket)
    }
}