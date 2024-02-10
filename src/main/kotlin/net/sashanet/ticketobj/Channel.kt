package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Channel(
    @SerialName("email")
    var email: Boolean,
    @SerialName("online")
    var online: Boolean
)