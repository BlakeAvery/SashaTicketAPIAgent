
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
@Serializable
data class AccessRequestWebhook(
    @SerialName("ticket_number")
    var ticketNumber: String,
    @SerialName("internal_id")
    var internalId: Int,
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
    @SerialName("original_requester")
    var originalRequester: Int
)