package net.leopisang.tchallo.app.model

/**
 * Created by LeoPisanGG on 2018-03-11.
 */
class DataTables {
    data class UserInfo(
            val Id : Long,
            val Name : String,
            val NoKtp : String,
            val Username : String,
            val Password : String,
            val Email : String
    )
}