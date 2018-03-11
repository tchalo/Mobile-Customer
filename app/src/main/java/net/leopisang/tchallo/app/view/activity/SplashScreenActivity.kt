package net.leopisang.tchallo.app.view.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.experimental.*
import net.leopisang.tchallo.R
import net.leopisang.tchallo.app.utility.Constant
import net.leopisang.tchallo.app.utility.Utils

class SplashScreenActivity : AppCompatActivity() {

    private val sleepTime : Long = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initial()
    }

    private fun initial() {
        llSplashLogo.setBackgroundColor(resources.getColor(R.color.apps_color))
        ivLogoSplash.setImageResource(R.mipmap.apps_logo)
        splashing()
    }


    private fun splashing() {
        if (!isFirstInstall()) {
            afterInstallScheme()
        }
        else {
            showLoading(true)
            firstInstallScheme()
        }
    }

    private fun afterInstallScheme() {
        launch {
            val dataMasterUpdated = async { updateDataMaster() }
            val isLogin = async { checkLogin() }
            if(isLogin.await()) {
                if (dataMasterUpdated.await()) {
                    changeScreen(true)
                } else {
                    Utils.popupBtnTwo(this@SplashScreenActivity, getString(R.string.msg_title_failed_update_data), getString(R.string.msg_content_failed_update_data),getString(R.string.btn_text_no), getString(R.string.btn_text_yes)) {
                        index ->
                        when(index) {
                            0 -> {this@SplashScreenActivity.finish()}
                            else -> launch { afterInstallScheme() }
                        }
                    }.show()
                }
            } else {
                changeScreen(false)
            }
        }
    }

    private fun firstInstallScheme() {
        launch {
            val dataMasterUpdated = updateDataMaster()
            if (dataMasterUpdated) {
                Utils.Companion.PreferenceHelper.customPrefs(this@SplashScreenActivity, Constant.PREF_APP_CONTXT)
                                .edit().putBoolean(Constant.PREF_APP_FIRST_INSTALL, !dataMasterUpdated).apply()
                runOnUiThread {
                    changeScreen(false)
                }
            } else {
                runOnUiThread {
                    Utils.popupBtnTwo(this@SplashScreenActivity, getString(R.string.msg_title_failed_update_data), getString(R.string.msg_content_failed_update_data),getString(R.string.btn_text_no), getString(R.string.btn_text_yes)) {
                        index ->
                        when(index) {
                            0 -> {this@SplashScreenActivity.finish()}
                            else -> launch { firstInstallScheme() }
                        }
                    }.show()
                }
            }
        }
    }

    private fun showLoading(show:Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        llSplashProgress.visibility = if (show) View.VISIBLE else View.GONE
        llSplashProgress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        llSplashProgress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }

    private fun isFirstInstall() : Boolean {
        return Utils.Companion.PreferenceHelper.customPrefs(this, Constant.PREF_APP_CONTXT)
                .getBoolean(Constant.PREF_APP_FIRST_INSTALL, true)
    }

    private fun changeScreen(changeToMain:Boolean) {
        showLoading(false);
        val intent : Intent = if (changeToMain)
            Intent(this, MainActivity::class.java)
        else
            Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private suspend fun checkLogin() : Boolean {
        // Check your login here
        delay(1000) // check if password changed
        return loginChecker()
    }

    private fun loginChecker(): Boolean {
        val loginCheck : String = Utils.Companion.PreferenceHelper.customPrefs(this, Constant.PREF_LOGIN_CONTXT)
                .getString(Constant.PREF_LOGIN_USER, "")
        Log.e("KAMPRET", loginCheck)
        return !loginCheck.equals("",true)
    }

    private suspend fun updateDataMaster() : Boolean {
//        Thread.sleep(sleepTime.toLong())
        delay(3000)
        return true
    }

}
