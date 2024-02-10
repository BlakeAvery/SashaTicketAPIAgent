package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Create(
    @SerialName("criteria")
    var criteria: Criteria,
    @SerialName("channel")
    var channel: Channel
)