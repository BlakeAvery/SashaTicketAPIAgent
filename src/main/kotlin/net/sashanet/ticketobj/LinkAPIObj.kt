package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkAPIObj(
    @SerialName("link_type")
    var linkType: String? = null,
    @SerialName("link_object_target")
    var linkObjectTarget: String? = null,
    @SerialName("link_object_target_value")
    var linkObjectTargetValue: Int? = null,
    @SerialName("link_object_source")
    var linkObjectSource: String? = null,
    @SerialName("link_object_source_number")
    var linkObjectSourceNumber: String? = null,
    @SerialName("id")
    var id: Int? = null,
    @SerialName("link_type_id")
    var linkTypeId: Int? = null,
    @SerialName("link_object_source_id")
    var linkObjectSourceId: Int? = null,
    @SerialName("link_object_source_value")
    var linkObjectSourceValue: Int? = null,
    @SerialName("link_object_target_id")
    var linkObjectTargetId: Int? = null,
    @SerialName("created_at")
    var createdAt: String? = null,
    @SerialName("updated_at")
    var updatedAt: String? = null
)