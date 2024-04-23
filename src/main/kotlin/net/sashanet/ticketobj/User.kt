package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    var id: Int? = null,
    @SerialName("organization_id")
    var organizationId: Int? = null,
    @SerialName("login")
    var login: String? = null,
    @SerialName("firstname")
    var firstname: String? = null,
    @SerialName("lastname")
    var lastname: String? = null,
    @SerialName("email")
    var email: String? = null,
    @SerialName("password")
    var password: String? = null,
    @SerialName("image")
    var image: String? = null,
    @SerialName("image_source")
    var imageSource: String? = null,
    @SerialName("web")
    var web: String? = null,
    @SerialName("phone")
    var phone: String? = null,
    @SerialName("fax")
    var fax: String? = null,
    @SerialName("mobile")
    var mobile: String? = null,
    @SerialName("ham_callsign")
    var hamCallSign: String? = null,
    @SerialName("department")
    var department: String? = null,
    @SerialName("title")
    var title: String? = null,
    @SerialName("street")
    var street: String? = null,
    @SerialName("zip")
    var zip: String? = null,
    @SerialName("city")
    var city: String? = null,
    @SerialName("country")
    var country: String? = null,
    @SerialName("address")
    var address: String? = null,
    @SerialName("vip")
    var vip: Boolean? = null,
    @SerialName("verified")
    var verified: Boolean? = null,
    @SerialName("active")
    var active: Boolean? = null,
    @SerialName("note")
    var note: String? = null,
    @SerialName("last_login")
    var lastLogin: String? = null,
    @SerialName("source")
    var source: String? = null,
    @SerialName("login_failed")
    var loginFailed: Int? = null,
    @SerialName("out_of_office")
    var outOfOffice: Boolean? = null,
    @SerialName("out_of_office_start_at")
    var outOfOfficeStartAt: String? = null,
    @SerialName("out_of_office_end_at")
    var outOfOfficeEndAt: String? = null,
    @SerialName("out_of_office_replacement_id")
    var outOfOfficeReplacementId: String? = null,
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
    @SerialName("role_ids")
    var roleIds: List<Int?>,
    @SerialName("two_factor_preference_ids")
    var twoFactorPreferenceIds: List<String?>,
    @SerialName("organization_ids")
    var organizationIds: List<Int?>,
    @SerialName("authorization_ids")
    var authorizationIds: List<String?>,
    @SerialName("overview_sorting_ids")
    var overviewSortingIds: List<String?>,
    @SerialName("group_ids")
    var groupIds: GroupIds? = null
)