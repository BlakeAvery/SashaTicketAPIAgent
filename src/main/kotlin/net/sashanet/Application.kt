package net.sashanet

import AccessRequestWebhook
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.json.Json
import net.sashanet.plugins.*

val newUserOnboarder = NewUserOnboarder()
val constants = Constants()

fun main() {
    println("Kalimera! Welcome to SashaTicketAPIAgent ${constants.version}")
    embeddedServer(Netty, port = 80) {
        routing {
            post("/onboard") {
                val webhook = call.receiveText()
                println("Wow. A request? Received? No shot! Here is the info :)")
                println(webhook)
                val data = Json.decodeFromString<AccessRequestWebhook>(webhook)
                call.respondText("Poggers", ContentType.Text.Plain, HttpStatusCode.OK)
                newUserOnboarder.createUser(data, constants)
            }
            get("/onboard") {
                val fakeUser = AccessRequestWebhook("100001", "22", "leaabito05", "Lea", "Abito", "Southview", "2131231123", "y", "1")
                newUserOnboarder.createUser(fakeUser, constants)
                call.respondText("This method is not authorized for this resource.", ContentType.Text.Plain, HttpStatusCode.PaymentRequired)
            }
        }
    }.start(wait = true)
}

fun Application.module() {
    configureSockets()
    configureSerialization()
    configureHTTP()
    configureRouting()
}
