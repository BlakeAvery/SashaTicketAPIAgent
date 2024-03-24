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
    suspend fun modifyTicket(ticket: TicketAPIObj): Boolean {
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
                println("${request.status.description} :)")
                println(respBody)
                return true
            }
            else -> {
                println("${request.status.description} :(")
                println(respBody)
                return false
            }
        }
    }
    suspend fun createUser(user: NewUser): User? {
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
                println("New user provisioned :) Here is their raw JSON for your observation.")
                println(sendUser.bodyAsText())
                return Json.decodeFromString(sendUser.bodyAsText())
            }
            else -> {
                println("${sendUser.status.description} :(")
                println(sendUser.bodyAsText())
                return null
            }
        }
    }
    suspend fun searchUser(query: String): String {
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
                println("${search.status.description} :)")
                return search.body()
            }

            else -> {
                println("${search.status.description} :)")
                return search.body()
            }
        }
    }
    suspend fun addTag(tag: String, ticket: Int) {
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
                println("${send.status.description} :)")
                println(respBody)
            }
            else -> {
                println("${send.status.description} :(")
                println(respBody)
            }
        }
    }
    suspend fun deleteTag(tag: String, ticket: Int) {
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
                println("${send.status.description} :)")
                println(respBody)
            }
            else -> {
                println("${send.status.description} :(")
                println(respBody)
            }
        }
    }
    suspend fun getTicketArticles(id: Int): List<Article> {
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
        retList = Json.decodeFromString(send.bodyAsText())
        return retList
    }
    suspend fun getArticle(id: Int): Article? {
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
                println("${send.status.description} :)")
                return Json.decodeFromString(send.bodyAsText())
            }

            else -> {
                println("${send.status.description} :(")
                return null
            }
        }
    }
    suspend fun newArticle(article: Article): Boolean {
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
                println("${send.status.description} :)")
                return true
            }
            else -> {
                println("${send.status.description} :(")
                return false
            }
        }
    }
}