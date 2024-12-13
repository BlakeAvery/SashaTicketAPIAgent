package net.sashanet

import kotlinx.serialization.encodeToString
import java.util.*
import net.sashanet.ticketobj.*
import kotlinx.serialization.json.Json


/**
 * APIAgentConsole
 * This kotlin program is meant to run a subset of APIAgent commands as a quick utility interface to the Zammad API.
 * Please do NOT use this for workflow testing. Holy fuck. That's delusional behavior.
 */

suspend fun main (args: Array<String>) {
    println("Kalimera! Welcome to SashaTicketAPIAgent Console v${constants.version}!")
    println("(c) 2024-2025 SashaNet")
    //set up persistent variables
    var user: User? = null
    var ticket: TicketAPIObj? = null
    while(true) {
        print("Request> ")
        var input = readLine()
        when(input!!.lowercase()) {
            "" -> {}
            "getuser" -> {
                print("Please enter the user ID. ")
                var id: Int = try {
                    readLine()!!.toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                if(id == 0) {
                    println("User 0 does not exist.")
                    user = null
                } else {
                    user = apiAgent.getUser(id)
                    val userString = Json.encodeToString(user).split(",")
                    for(i in userString) {
                        println(i)
                    }
                    println("This user has been stored for now.")
                }
            }
            "getticket" -> {
                print("Please enter the ticket ID. ")
                var id = try {
                    readLine()!!.toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                if(id == 0) {
                    println("Ticket 0 does not exist.")
                    ticket = null
                } else {
                    ticket = apiAgent.getTicket(id)
                    val ticketString = Json.encodeToString(ticket).split(",")
                    for(i in ticketString) {
                        println(i)
                    }
                    println("This ticket has been stored for now.")
                }
            }
        }
    }
}