package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationSound(
    @SerialName("file")
    var `file`: String,
    @SerialName("enabled")
    var enabled: Boolean
)