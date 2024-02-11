package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Update(
    @SerialName("criteria")
    var criteria: CriteriaXX?,
    @SerialName("channel")
    var channel: Channel?
)