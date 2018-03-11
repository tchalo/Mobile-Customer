package net.leopisang.tchallo.app.model

/**
 * Created by LeoPisanGG on 2018-03-11.
 */
class Responses {
    data class Login (
            val status : Boolean,
            val message : String,
            val data : DataTables.UserInfo
    )
}