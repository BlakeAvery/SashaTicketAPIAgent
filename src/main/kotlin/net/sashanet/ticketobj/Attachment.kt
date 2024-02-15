package net.sashanet.ticketobj

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    @SerialName("filename")
    var filename: String,
    @SerialName("data")
    var date: String,
    @SerialName("mime-type")
    var mimeType: String
)
