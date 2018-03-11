package net.leopisang.tchallo.app.utility


/**
 * Created by LeoPisanGG on 2018-02-17.
 */
class Constant {
    companion object {
        ///////////// URL API /////////////
        val API_ORDER : String = Configs.BUILD_URL("/OrderAPI")
        val API_HISTORY : String = Configs.BUILD_URL("/HistoryAPI")

        ///////////// PREFERENCES KEY /////////////
        val PREF_APP_CONTXT : String = "APP_PARAM"
        val PREF_APP_FIRST_INSTALL : String = "FIRST_INSTALL"
        val PREF_LOGIN_CONTXT: String = "LOGIN_UNAME"
        val PREF_LOGIN_USER : String = "USERNAME"
    }
}