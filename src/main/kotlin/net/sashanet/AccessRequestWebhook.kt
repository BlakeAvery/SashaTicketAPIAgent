
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
@Serializable
data class AccessRequestWebhook(
    @SerialName("ticket_number")
    var ticketNumber: String,
    @SerialName("internal_id")
    var internalId: String,
    @SerialName("email")
    var email: String,
    @SerialName("first_name")
    var firstName: String,
    @SerialName("last_name")
    var lastName: String,
    @SerialName("org")
    var org: String,
    @SerialName("phone_number")
    var phoneNumber: String,
    @SerialName("text_consent")
    var textConsent: String,
    @SerialName("original_requester")
    var originalRequester: String
)