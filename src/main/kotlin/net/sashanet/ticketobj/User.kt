package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    var id: Int,
    @SerialName("organization_id")
    var organizationId: Int,
    @SerialName("login")
    var login: String,
    @SerialName("firstname")
    var firstname: String,
    @SerialName("lastname")
    var lastname: String,
    @SerialName("email")
    var email: String,
    @SerialName("password")
    var password: String?,
    @SerialName("image")
    var image: String,
    @SerialName("image_source")
    var imageSource: String?,
    @SerialName("web")
    var web: String,
    @SerialName("phone")
    var phone: String,
    @SerialName("fax")
    var fax: String,
    @SerialName("mobile")
    var mobile: String,
    @SerialName("department")
    var department: String,
    @SerialName("street")
    var street: String,
    @SerialName("zip")
    var zip: String,
    @SerialName("city")
    var city: String,
    @SerialName("country")
    var country: String,
    @SerialName("address")
    var address: String,
    @SerialName("vip")
    var vip: Boolean,
    @SerialName("verified")
    var verified: Boolean,
    @SerialName("active")
    var active: Boolean,
    @SerialName("note")
    var note: String,
    @SerialName("last_login")
    var lastLogin: String,
    @SerialName("source")
    var source: String?,
    @SerialName("login_failed")
    var loginFailed: Int,
    @SerialName("out_of_office")
    var outOfOffice: Boolean,
    @SerialName("out_of_office_start_at")
    var outOfOfficeStartAt: String?,
    @SerialName("out_of_office_end_at")
    var outOfOfficeEndAt: String?,
    @SerialName("out_of_office_replacement_id")
    var outOfOfficeReplacementId: String?,
    @SerialName("preferences")
    var preferences: Preferences,
    @SerialName("updated_by_id")
    var updatedById: Int,
    @SerialName("created_by_id")
    var createdById: Int,
    @SerialName("created_at")
    var createdAt: String,
    @SerialName("updated_at")
    var updatedAt: String,
    @SerialName("role_ids")
    var roleIds: List<Int>,
    @SerialName("two_factor_preference_ids")
    var twoFactorPreferenceIds: List<String>,
    @SerialName("organization_ids")
    var organizationIds: List<Int>,
    @SerialName("authorization_ids")
    var authorizationIds: List<String>,
    @SerialName("overview_sorting_ids")
    var overviewSortingIds: List<String>,
    @SerialName("group_ids")
    var groupIds: GroupIds
)