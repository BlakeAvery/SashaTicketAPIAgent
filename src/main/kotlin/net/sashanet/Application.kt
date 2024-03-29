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
                newUserOnboarder.createUser(data)
            }
            get("/onboard") {
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
            route("/offboard") {
                get {
                    call.respondText("This method is not authorized for this resource.", ContentType.Text.Plain, HttpStatusCode.NotImplemented)
                }
                post {
                    val webhook = call.receiveText()
                    println(webhook)
                    call.respondText("This method is not yet implemented.", ContentType.Text.Plain, HttpStatusCode.NotImplemented)
                }
            }
            route("/") {
                get {
                    call.respondText("This method is not authorized for this resource.", ContentType.Text.Plain, HttpStatusCode(418, "I'm a Teapot :)"))
                }
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
