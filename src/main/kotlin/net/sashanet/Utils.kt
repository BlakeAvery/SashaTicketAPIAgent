package net.sashanet

class Utils {
    fun parseOrgs(org: String): String {
        when(org) {
            "fgss" -> return "FGSS"
            "beverly" -> return "Beverly"
            "southview" -> return "Southview"
            "thelma" -> return "Thelma"
            "pompano" -> return "Pompano"
            else -> return "Beverly"
        }
    }
}