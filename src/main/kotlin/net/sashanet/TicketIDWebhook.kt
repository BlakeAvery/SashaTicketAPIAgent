package net.sashanet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketIDWebhook(
    @SerialName("id")
    var id: Int
)
