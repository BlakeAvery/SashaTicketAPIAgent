package net.sashanet

import net.sashanet.ticketobj.*

/**
 * Utils: Does things for our APIAgent. Speaks to the APIAgent class to do its dirty work sometimes.
 * Very sparse right now.
 */

val apiAgent = APIAgent()
class Utils {
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
    suspend fun patchMediaReqTitle(webhook: MediaRequestWebhook) {
        val ticket = TicketAPIObj(id = webhook.internalId?.toInt(),
            title = "${webhook.mediaName} (${parseMediaTypes(webhook.mediaType)})")
        apiAgent.modifyTicket(ticket)
    }
}