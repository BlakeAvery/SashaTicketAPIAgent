package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationConfig(
    @SerialName("matrix")
    var matrix: Matrix
)