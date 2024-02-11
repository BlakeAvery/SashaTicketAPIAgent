package net.sashanet.ticketobj


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
*
*/

@Serializable
data class NewUser(
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
    @SerialName("organization")
    var organization: String,
    @SerialName("roles")
    var roles: List<String>,
    @SerialName("phone")
    var phone: String,
    @SerialName("mobile")
    var mobile: String,
    @SerialName("vip")
    var vip: Boolean = false,

)