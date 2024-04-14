package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Preferences(
    @SerialName("notification_config")
    var notificationConfig: NotificationConfig? = null,
    @SerialName("locale")
    var locale: String? = null,
    @SerialName("intro")
    var intro: Boolean? = null,
    @SerialName("tickets_closed")
    var ticketsClosed: Int? = null,
    @SerialName("tickets_open")
    var ticketsOpen: Int? = null,
    @SerialName("two_factor_authentication")
    var twoFactorAuthentication: TwoFactorAuthentication? = null,
    @SerialName("theme")
    var theme: String? = null,
    @SerialName("notification_sound")
    var notificationSound: NotificationSound? = null,
    @SerialName("escalation_calculation")
    var escalationCalculation: EscalationCalculation? = null
)