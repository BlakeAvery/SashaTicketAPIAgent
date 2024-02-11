package net.sashanet

/**
 * Utils: Does things for our APIAgent. Speaks to the APIAgent class to do its dirty work sometimes.
 * Very sparse right now.
 */
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