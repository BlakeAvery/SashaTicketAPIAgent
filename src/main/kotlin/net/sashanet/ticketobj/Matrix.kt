package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Matrix(
    @SerialName("create")
    var create: Create,
    @SerialName("update")
    var update: Update,
    @SerialName("reminder_reached")
    var reminderReached: ReminderReached,
    @SerialName("escalation")
    var escalation: Escalation
)