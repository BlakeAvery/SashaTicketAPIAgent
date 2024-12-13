package net.sashanet

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
import javax.swing.text.html.HTML.Tag
import kotlin.text.get

/**
 * APIAgent: This class implements (or will implement :)) all the methods of the Zammad API required for this program to function.
 * The values in SashaTicketAPIAgent are customized for my Zammad install.
 * You can always take out what you don't need.
 */


private val client = HttpClient()
class APIAgent {
    suspend fun newTicket(ticket: TicketAPIObj): TicketAPIObj? {
        println("Opening new ticket...")
        val sendTicket = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/tickets")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
            }
            val send = Json{ignoreUnknownKeys = true}.encodeToString(ticket)
            println(send)
            contentType(ContentType.Application.Json)
            setBody(send)
        }
        when (sendTicket.status.value) {
            in 200..299 -> {
                println("${sendTicket.status}")
                println(sendTicket.bodyAsText())//33
                return Json{ignoreUnknownKeys = true}.decodeFromString(sendTicket.bodyAsText())
            }
            else -> {
                println("${sendTicket.status} :(")
                println(sendTicket.bodyAsText())
                return null
            }
        }
    }
    suspend fun modifyTicket(ticket: TicketAPIObj): Boolean {
        println("Modifying ticket ${ticket.id}...")
        //Create the request...
        val request = client.put {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/tickets/${ticket.id}")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, constants.userAgent)
            }
            //Serialize our object... (any ticket fields should have been modified already by whoever created the object)
            val send = Json.encodeToString(ticket)
            println(send)
            contentType(ContentType.Application.Json)
            setBody(send)
        }
        val respBody = request.bodyAsText()
        when(request.status.value) {
            in 200..299 -> {
                println("${request.status} :)")
                println(respBody)
                return true
            }
            else -> {
                println("${request.status} :(")
                println(respBody)
                return false
            }
        }
    }
    suspend fun createUser(user: NewUser): User? {
        println("Creating user ${user.email}...")
        val sendUser = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/users")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
            }
            val send = Json.encodeToString(user)
            println(send)
            contentType(ContentType.Application.Json)
            setBody(send)
        }
        when (sendUser.status.value) {
            in 200..299 -> {
                println("${sendUser.status.value} ${sendUser.status.description} :)")
                println("New user provisioned :) Here is their raw JSON for your observation.")
                println(sendUser.bodyAsText())
                return Json.decodeFromString(sendUser.bodyAsText())
            }
            else -> {
                println("${sendUser.status.value} ${sendUser.status.description} :(")
                println(sendUser.bodyAsText())
                println("A user was not created.")
                return null
            }
        }
    }
    suspend fun searchUser(query: String): List<User> {
        println("Searching for users based on parameter: ${query}")
        val search = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/users/search?query=${query}")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, constants.userAgent)
            }
        }
        when (search.status.value) {
            in 200..299 -> {
                println("${search.status} :)")
                return Json.decodeFromString(search.bodyAsText())
            }

            else -> {
                println("${search.status} :(")
                return Json.decodeFromString(search.bodyAsText())
            }
        }
    }
    suspend fun getUser(id: Int): User? {
        val getting = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/users/$id")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, constants.userAgent)
            }
        }
        when(getting.status.value) {
            in 200..299 -> {
                println("${getting.status} :)")
                return Json{ignoreUnknownKeys = true}.decodeFromString(getting.bodyAsText())
            }
            else -> {
                println("${getting.status} :(")
                return null
            }
        }
    }
    suspend fun addTag(tag: String, ticket: Int) {
        println("Adding tag $tag to ticket ID $ticket...")
        val send = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/tags/add")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
            }
            val send = Json.encodeToString(TagAPIObj(tag, ticket, "Ticket"))
            println(send)
            contentType(ContentType.Application.Json)
            setBody(send)
        }
        val respBody = send.bodyAsText()
        when(send.status.value) {
            in 200..299 -> {
                println("${send.status} :)")
                println(respBody)
            }
            else -> {
                println("${send.status} :(")
                println(respBody)
            }
        }
    }
    suspend fun deleteTag(tag: String, ticket: Int) {
        println("Deleting tag $tag from ticket ID $ticket...")
        val send = client.delete {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/tags/remove")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
            }
            val send = Json.encodeToString(TagAPIObj(tag, ticket, "Ticket"))
            println(send)
            contentType(ContentType.Application.Json)
            setBody(send)
        }
        val respBody = send.bodyAsText()
        when(send.status.value) {
            in 200..299 -> {
                println("${send.status} :)")
                println(respBody)
            }
            else -> {
                println("${send.status} :(")
                println(respBody)
            }
        }
    }
    suspend fun getTicketArticles(id: Int): List<Article>? {
        println("Obtaining all articles attached to ticket id $id...")
        var retList: List<Article> = listOf()
        val send = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/ticket_articles/by_ticket/$id")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
            }
        }
        when(send.status.value) {
            in 200..299 -> {
                println("${send.status} :)")
                println(send.bodyAsText())
                retList = Json.decodeFromString(send.bodyAsText())
                return retList
            }
            else -> {
                println("${send.status} :(")
                println(send.bodyAsText())
                return null
            }
        }
    }
    suspend fun getArticle(id: Int): Article? {
        println("Getting article $id...")
        val send = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/ticket_articles/$id")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
            }
        }
        when(send.status.value) {
            in 200..299 -> {
                println("${send.status} :)")
                return Json.decodeFromString(send.bodyAsText())
            }

            else -> {
                println("${send.status} :(")
                return null
            }
        }
    }
    suspend fun newArticle(article: Article): Boolean {
        println("Creating new ${article.type} article (internal = ${article.internal})...")
        val send = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/ticket_articles")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
            }
            val send = Json.encodeToString(article)
            println(send)
            contentType(ContentType.Application.Json)
            setBody(send)
        }
        when(send.status.value) {
            in 200..299 -> {
                println("${send.status} :)")
                return true
            }
            else -> {
                println("${send.status} :(")
                return false
            }
        }
    }
    suspend fun getTicket(id: Int): TicketAPIObj? {
        println("Looking for ticket $id...")
        val send = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/tickets/$id")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
            }
        }
        when(send.status.value) {
            in 200..299 -> {
                println("${send.status} :)")
                println(send.bodyAsText())
                return Json.decodeFromString(send.bodyAsText())
            }

            else -> {
                println("${send.status} :(")
                println("I don't think we have ticket $id. Returning null.")
                return null
            }
        }
    }
    suspend fun newTicketLink(ticket1: Int, ticket2: Int, linkType: String = "normal"): Boolean {
        /**
         * Links ticket2 as linkType of ticket1, just as the GUI would.
         * Expects both to be ticket id numbers. The APIAgent will figure out the required fields.
         */
        println("Linking ticket $ticket2 with link type $linkType to ticket $ticket1...")
        var ticket1Number = getTicket(ticket1)?.number
        var linkCreate: LinkAPIObj = LinkAPIObj(
            linkType = linkType,
            linkObjectTarget = "Ticket",
            linkObjectTargetValue = ticket2,
            linkObjectSource = "Ticket",
            linkObjectSourceNumber = ticket1Number
        )
        val send = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = constants.host
                appendEncodedPathSegments("/api/v1/links/add")
            }
            headers {
                append(HttpHeaders.Authorization, "Token token=${secrets.apiKey}")
                append(HttpHeaders.UserAgent, "SashaTicketAPIAgent/${constants.version}")
            }
            val send = Json.encodeToString(linkCreate)
            println(send)
            contentType(ContentType.Application.Json)
            setBody(send)
        }
        when(send.status.value) {
            in 200..299 -> {
                println("${send.status} :)")
                return true
            }

            else -> {
                println("${send.status} :(")
                return false
            }
        }
    }
}