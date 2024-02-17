package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketAPIObj(
    @SerialName("id")
    var id: Int? = null,
    @SerialName("group_id")
    var groupId: Int? = null,
    @SerialName("priority_id")
    var priorityId: Int? = null,
    @SerialName("state")
    var state: String? = null,
    @SerialName("state_id")
    var stateId: Int? = null,
    @SerialName("organization_id")
    var organizationId: Int? = null,
    @SerialName("number")
    var number: String? = null,
    @SerialName("title")
    var title: String? = null,
    @SerialName("owner_id")
    var ownerId: Int? = null,
    @SerialName("customer_id")
    var customerId: Int? = null,
    @SerialName("note")
    var note: String? = null,
    @SerialName("first_response_at")
    var firstResponseAt: String? = null,
    @SerialName("first_response_escalation_at")
    var firstResponseEscalationAt: String? = null,
    @SerialName("first_response_in_min")
    var firstResponseInMin: String? = null,
    @SerialName("first_response_diff_in_min")
    var firstResponseDiffInMin: String? = null,
    @SerialName("close_at")
    var closeAt: String? = null,
    @SerialName("close_escalation_at")
    var closeEscalationAt: String? = null,
    @SerialName("close_in_min")
    var closeInMin: String? = null,
    @SerialName("close_diff_in_min")
    var closeDiffInMin: String? = null,
    @SerialName("update_escalation_at")
    var updateEscalationAt: String? = null,
    @SerialName("update_in_min")
    var updateInMin: String? = null,
    @SerialName("update_diff_in_min")
    var updateDiffInMin: String? = null,
    @SerialName("last_close_at")
    var lastCloseAt: String? = null,
    @SerialName("last_contact_at")
    var lastContactAt: String? = null,
    @SerialName("last_contact_agent_at")
    var lastContactAgentAt: String? = null,
    @SerialName("last_contact_customer_at")
    var lastContactCustomerAt: String? = null,
    @SerialName("last_owner_update_at")
    var lastOwnerUpdateAt: String? = null,
    @SerialName("create_article_type_id")
    var createArticleTypeId: Int? = null,
    @SerialName("create_article_sender_id")
    var createArticleSenderId: Int? = null,
    @SerialName("article_count")
    var articleCount: Int? = null,
    @SerialName("article")
    var article: Article? = null,
    @SerialName("escalation_at")
    var escalationAt: String? = null,
    @SerialName("pending_time")
    var pendingTime: String? = null,
    @SerialName("type")
    var type: String? = null,
    @SerialName("time_unit")
    var timeUnit: String? = null,
    @SerialName("preferences")
    var preferences: Preferences? = null,
    @SerialName("updated_by_id")
    var updatedById: Int? = null,
    @SerialName("created_by_id")
    var createdById: Int? = null,
    @SerialName("created_at")
    var createdAt: String? = null,
    @SerialName("updated_at")
    var updatedAt: String? = null,
    @SerialName("media_name")
    var mediaName: String? = null,
    @SerialName("media_type")
    var mediaType: String? = null,
    @SerialName("media_location")
    var mediaLocation: List<String>? = listOf(),
    @SerialName("chore_location")
    var choreLocation: String? = null,
    @SerialName("chore_interval")
    var choreInterval: String? = null,
    @SerialName("it_system")
    var itSystem: List<String>? = listOf(),
    @SerialName("access_action")
    var accessAction: String? = null,
    @SerialName("bill_type")
    var billType: String? = null,
    @SerialName("amount_requested")
    var amountRequested: String? = null,
    @SerialName("due_date")
    var dueDate: String? = null,
    @SerialName("access_system")
    var accessSystem: String? = null,
    @SerialName("access_user")
    var accessUser: String? = null,
    @SerialName("access_group")
    var accessGroup: String? = null,
    @SerialName("it_need")
    var itNeed: String? = null,
    @SerialName("access_fname")
    var accessFname: String? = null,
    @SerialName("access_lname")
    var accessLname: String? = null,
    @SerialName("access_org")
    var accessOrg: String? = null,
    @SerialName("grocery_total")
    var groceryTotal: String? = null,
    @SerialName("grocery_store")
    var groceryStore: List<String>? = listOf(),
    @SerialName("zammad_change")
    var zammadChange: List<String>? = listOf(),
    @SerialName("house_expense_type")
    var houseExpenseType: String? = null,
    @SerialName("access_phone")
    var accessPhone: String? = null,
    @SerialName("access_email")
    var accessEmail: String? = null
)