package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EscalationCalculation(
    @SerialName("first_response_at")
    var firstResposneAt: String? = null,
    @SerialName("last_update_at")
    var lastUpdateAt: String? = null,
    @SerialName("last_contact_at")
    var lastContactAt: String? = null,
    @SerialName("sla_id")
    var slaId: Int? = null,
    @SerialName("sla_updated_at")
    var slaUpdatedAt: String? = null,
    @SerialName("calendar_id")
    var calendarId: Int? = null,
    @SerialName("calendar_updated_at")
    var calendarUpdatedAt: String? = null,
    @SerialName("escalation_disabled")
    var escalationDisabled: Boolean? = null
)