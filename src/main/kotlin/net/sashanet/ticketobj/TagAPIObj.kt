package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagAPIObj(
    @SerialName("item")
    val item: String?,
    @SerialName("o_id")
    val oId: Int?,
    @SerialName("object")
    val objectX: String?
)