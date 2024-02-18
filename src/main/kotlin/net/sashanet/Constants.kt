package net.sashanet

data class Constants(
    val version: String = "0.8alpha", //incremented when I feel like it
    val userAgent: String = "SashaTicketAPIAgent/$version", //autotemplate for our ua string
    val apiAgentUserID: Int = 16 //Change this to the account ID of the API Agent account for your Zammad install.
)
