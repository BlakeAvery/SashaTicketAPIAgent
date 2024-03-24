package net.sashanet


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaRequestWebhook(
    @SerialName("internal_id")
    var internalId: String? = null,
    @SerialName("media_name")
    var mediaName: String? = null,
    @SerialName("media_type")
    var mediaType: String? = null,
    @SerialName("console")
    var console: String? = null
)