package net.leopisang.tchallo.app.utility

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by LeoPisanGG on 2018-02-16.
 */
class Utils {
    companion object {
        fun asem() {
            var s:String = ""
        }
        fun checkPermissionContact(activity: Activity) : Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true
            }
            if (activity.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                return true
            }
            if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {

            } else {
                activity.requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 0)
            }
            return false
        }
        fun hideKeyboard(activity:Activity) {
            val view : View? = activity.getCurrentFocus();
            if (view != null) {
                val imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        fun popupBtnTwo(activity:Activity, strTitle:String, strMessage:String, strBtn1:String, strBtn2:String, callback:(Int)->Unit) : AlertDialog {
            var alertDialog : AlertDialog = AlertDialog.Builder(activity).create()
            alertDialog.setTitle(strTitle)
            alertDialog.setMessage(strMessage)
            alertDialog.setCancelable(false)
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,strBtn1, { _, _ ->
                callback.invoke(0)
            })
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,strBtn2, { _, _ ->
                callback.invoke(1)
            })
            return alertDialog
        }
        object PreferenceHelper {

            fun defaultPrefs(context: Context): SharedPreferences
                    = PreferenceManager.getDefaultSharedPreferences(context)

            fun customPrefs(context: Context, name: String): SharedPreferences
                    = context.getSharedPreferences(name, Context.MODE_PRIVATE)

            inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
                val editor = this.edit()
                operation(editor)
                editor.apply()
            }
        }
    }
}