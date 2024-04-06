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
var lastOffBoardingId: Int? = 0

fun main() {
    println("Kalimera! Welcome to SashaTicketAPIAgent v${constants.version}")
    embeddedServer(Netty, port = 80) {
        routing {
            post("/onboard") {
                call.respondText("Poggers", ContentType.Text.Plain, HttpStatusCode.Accepted)
                val webhook = call.receiveText()
                println("Wow. A request? Received? No shot! Here is the info :)")
                println(webhook)
                val data = Json.decodeFromString<AccessRequestWebhook>(webhook)
                newUserOnboarder.createUser(data)
            }
            get("/onboard") {
                call.respondText("This method is not authorized for this resource... Yet :)", ContentType.Text.Plain, HttpStatusCode.Processing)
            }
            post("/newmediareq") {
                call.respondText("Poggers", ContentType.Text.Plain, HttpStatusCode.Accepted)
                val webhook = call.receiveText()
                println("Wow. A request? Received? No shot! Here is the info :)")
                println(webhook)
                var data = Json.decodeFromString<MediaRequestWebhook>(webhook)
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
                    call.respondText("This method is not yet implemented.", ContentType.Text.Plain, HttpStatusCode.Accepted)
                    val webhook = call.receiveText()
                    println(webhook)
                    var data = Json.decodeFromString<OffboardingWebhook>(webhook)
                    //call.respondText("This method is not yet implemented.", ContentType.Text.Plain, HttpStatusCode.Accepted)
                    if(lastOffBoardingId == data.id) {
                        println("This request was recently processed and will be ignored.")
                    } else {
                        utils.offboarding(data)
                    }
                }
            }
            route("/") {
                get {
                    call.respondText("This method is not authorized for this resource... Yet :)", ContentType.Text.Plain, HttpStatusCode(418, "I'm a Teapot :)"))
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
