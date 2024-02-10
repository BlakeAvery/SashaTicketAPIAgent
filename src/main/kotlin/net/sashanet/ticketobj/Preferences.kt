package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Preferences(
    @SerialName("notification_config")
    var notificationConfig: NotificationConfig,
    @SerialName("locale")
    var locale: String,
    @SerialName("intro")
    var intro: Boolean,
    @SerialName("tickets_closed")
    var ticketsClosed: Int,
    @SerialName("tickets_open")
    var ticketsOpen: Int,
    @SerialName("two_factor_authentication")
    var twoFactorAuthentication: TwoFactorAuthentication,
    @SerialName("theme")
    var theme: String,
    @SerialName("notification_sound")
    var notificationSound: NotificationSound
)