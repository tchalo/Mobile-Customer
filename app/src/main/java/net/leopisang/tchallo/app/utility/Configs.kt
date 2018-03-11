package net.leopisang.tchallo.app.utility

import net.leopisang.tchallo.BuildConfig

/**
 * Created by LeoPisanGG on 2018-02-17.
 */
class Configs {
    companion object {
        fun BUILD_URL(URL:String): String = BuildConfig.BASE_URL + URL
    }
}