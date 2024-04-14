package net.sashanet

data class Constants(
    val version: String = "0.88alpha", //incremented when I feel like it
    val userAgent: String = "SashaTicketAPIAgent/$version", //autotemplate for our ua string
    val host: String = "10.0.0.6", //This is where your Zammad install is.
    val apiAgentUserID: Int = 16 //Change this to the account ID of the API Agent account for your Zammad install.
)
