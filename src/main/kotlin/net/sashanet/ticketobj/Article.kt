package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("attachments")
    val attachments: List<Attachment>? = listOf(),
    @SerialName("body")
    val body: String? = null,
    @SerialName("cc")
    val cc: String? = null,
    @SerialName("content_type")
    val contentType: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("created_by")
    val createdBy: String? = null,
    @SerialName("created_by_id")
    val createdById: Int? = null,
    @SerialName("from")
    val from: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("in_reply_to")
    val inReplyTo: String? = null,
    @SerialName("internal")
    val `internal`: Boolean? = null,
    @SerialName("message_id")
    val messageId: String? = null,
    @SerialName("message_id_md5")
    val messageIdMd5: String? = null,
    @SerialName("origin_by_id")
    val originById: Int? = null,
    @SerialName("preferences")
    val preferences: Preferences? = null,
    @SerialName("references")
    val references: String? = null,
    @SerialName("reply_to")
    val replyTo: String? = null,
    @SerialName("sender")
    val sender: String? = null,
    @SerialName("sender_id")
    val senderId: Int? = null,
    @SerialName("subject")
    val subject: String? = null,
    @SerialName("ticket_id")
    val ticketId: Int? = null,
    @SerialName("to")
    val to: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("type_id")
    val typeId: Int? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("updated_by")
    val updatedBy: String? = null,
    @SerialName("updated_by_id")
    val updatedById: Int? = null
)