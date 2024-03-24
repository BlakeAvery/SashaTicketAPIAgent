package net.sashanet

data class Constants(
    val version: String = "0.81alpha", //incremented when I feel like it
    val userAgent: String = "SashaTicketAPIAgent/$version", //autotemplate for our ua string
    val host: String = "192.168.1.55",
    val apiAgentUserID: Int = 16 //Change this to the account ID of the API Agent account for your Zammad install.
)
