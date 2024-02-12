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

val constants = Constants()
val utils = Utils()
val secrets = Secrets()
val newUserOnboarder = NewUserOnboarder()

fun main() {
    println("Kalimera! Welcome to SashaTicketAPIAgent v${constants.version}")
    embeddedServer(Netty, port = 80) {
        routing {
            post("/onboard") {
                val webhook = call.receiveText()
                println("Wow. A request? Received? No shot! Here is the info :)")
                println(webhook)
                val data = Json.decodeFromString<AccessRequestWebhook>(webhook)
                call.respondText("Poggers", ContentType.Text.Plain, HttpStatusCode.Accepted)
                newUserOnboarder.createUser(data, constants)
            }
            get("/onboard") {
                val fakeUser = AccessRequestWebhook("100143", "152", "leaabito05@gmail.com", "Lea", "Abito", "Southview", "2131231123","1")
                newUserOnboarder.createUser(fakeUser, constants)
                call.respondText("This method is not authorized for this resource... Yet :)", ContentType.Text.Plain, HttpStatusCode.Processing)
            }
            post("/newmediareq") {
                val webhook = call.receiveText()
                println("Wow. A request? Received? No shot! Here is the info :)")
                println(webhook)
                var data = Json.decodeFromString<MediaRequestWebhook>(webhook)
                call.respondText("Poggers", ContentType.Text.Plain, HttpStatusCode.Accepted)
                utils.patchMediaReqTitle(data)
            }
            get("/newmediareq") {
                call.respondText("This method is not authorized for this resource.", ContentType.Text.Plain, HttpStatusCode.NotImplemented)
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
