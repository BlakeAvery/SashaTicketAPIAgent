package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CriteriaXX(
    @SerialName("owned_by_me")
    var ownedByMe: Boolean,
    @SerialName("owned_by_nobody")
    var ownedByNobody: Boolean,
    @SerialName("subscribed")
    var subscribed: Boolean,
    @SerialName("no")
    var no: Boolean
)