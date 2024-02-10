package net.sashanet.routes

import AccessRequestWebhook
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.APIAgentRouting() {
    route("/onboard") {
        post {
            val webhook = call.receive<AccessRequestWebhook>()
            println("Wow. A request? Received? No shot! Here is the info :)")
            println("Ticket number: ${webhook.ticketNumber}")
            println("Ticket number: ${webhook.ticketNumber}")
            println("Ticket number: ${webhook.ticketNumber}")
            println("Ticket number: ${webhook.ticketNumber}")
        }
        get {

        }
    }
}