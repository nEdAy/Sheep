package cn.neday.sheep.activity

import android.os.CountDownTimer
import android.text.InputType
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import cn.neday.base.activity.BaseVMActivity
import cn.neday.base.config.MMKVConfig.MOBILE
import cn.neday.base.config.MMKVConfig.TOKEN
import cn.neday.base.router.Router
import cn.neday.base.router.RouterPath
import cn.neday.sheep.KZ_YHSYXY
import cn.neday.sheep.R
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.viewmodel.LoginViewModel
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.*
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.mob.pushsdk.MobPush
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

/**
 * 登录页
 *
 * @author nEdAy
 */
@Route(path = RouterPath.LoginActivity)
class LoginActivity : BaseVMActivity<LoginViewModel>(R.layout.activity_login) {

    private var mTimeCount: TimeCount? = null
    private var mIsVoiceVerifyCode = false
    private var mIsAgreeAgreement = true

    override fun initView() {
        registerSMSSDK()
        initTitleAndBackgroundByTime()
        initEditViewByLastMobile()
        initClickView()
        initFocusChangeView()
        mViewModel.user.observe(this, Observer {
            kv.encode(TOKEN, it.token)
            kv.encode(MOBILE, it.mobile)
            MobPush.setAlias(it.mobile)
            ActivityUtils.finishActivity(this)
        })
        mViewModel.errMsg.observe(this, Observer {
            ToastUtils.showShort(it)
        })
    }

    private fun initClickView() {
        iv_login_bg.setOnClickListener { KeyboardUtils.hideSoftInput(this) }
        btn_login.setOnClickListener { registerOrLogin() }
        tv_change_login_way.setOnClickListener { changeLoginWay() }
        tv_lostPassword.setOnClickListener { resetPassword() }
        iv_agreement.setOnClickListener { changeAgreementIv() }
        tv_agreement.setOnClickListener { Router.alibabaService.showItemURLPage(this, KZ_YHSYXY) }
        iv_password_visibility.setOnClickListener {
            changePasswordVisibility(et_password.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }
        ClickUtils.applySingleDebouncing(tv_request_verification_code) { requestVerificationCode() }
    }

    private fun initFocusChangeView() {
        et_sms.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                line_sms.setBackgroundColor(ColorUtils.getColor(R.color.red))
            } else {
                line_sms.setBackgroundColor(ColorUtils.getColor(R.color.gray))
            }
        }
        et_password.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                line_password.setBackgroundColor(ColorUtils.getColor(R.color.red))
            } else {
                line_password.setBackgroundColor(ColorUtils.getColor(R.color.gray))
            }
        }
    }

    private fun changePasswordVisibility(isInVisible: Boolean) {
        if (isInVisible) {
            iv_password_visibility.setImageResource(R.drawable.ic_visibility_off_white_24dp)
            et_password.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            iv_password_visibility.setImageResource(R.drawable.ic_visibility_white_24dp)
            et_password.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        et_password.setSelection(et_password.text?.length ?: 0)
    }

    override fun onBackPressed() {
        if (mTimeCount != null) {
            val dialog = NormalDialog(this)
            dialog.content(getString(R.string.tx_login_dialog_sms))
                .style(NormalDialog.STYLE_TWO)
                .titleTextSize(23f)
                .show()
            dialog.setOnBtnClickL(
                OnBtnClickL { dialog.dismiss() },
                OnBtnClickL {
                    dialog.superDismiss()
                    ActivityUtils.finishActivity(this)
                })
        } else {
            super.onBackPressed()
        }
    }

    private fun initEditViewByLastMobile() {
        val mobile = kv.decodeString(MOBILE)
        if (!StringUtils.isTrimEmpty(mobile)) {
            et_mobile.setText(mobile)
            et_password.isFocusable = true
            et_password.isFocusableInTouchMode = true
            et_password.requestFocus()
            et_password.requestFocusFromTouch()
        }
    }

    private fun initTitleAndBackgroundByTime() {
        val isDayNotNight =
            TimeUtils.getValueByCalendarField(TimeUtils.getNowDate(), Calendar.HOUR_OF_DAY) in 8..20
        if (isDayNotNight) {
            iv_login_bg.setImageResource(R.drawable.good_morning_img)
            tv_login_title.text = getString(R.string.tx_login_title_day)
        } else {
            iv_login_bg.setImageResource(R.drawable.good_night_img)
            tv_login_title.text = getString(R.string.tx_login_title_night)
        }
    }

    private fun registerOrLogin() {
        if (!mIsAgreeAgreement) {
            ToastUtils.showShort(getString(R.string.tx_not_agree_agreement))
            return
        }
        val mobile = et_mobile.text.toString().trim { it <= ' ' }.replace(" ", "")
        val password = et_password.text.toString().trim()
        val smsCode = et_sms.text.toString().trim()
        if (checkUserOk(mobile, password, smsCode)) {
            KeyboardUtils.hideSoftInput(this)
            mViewModel.registerOrLogin(mobile, password, smsCode, "")
        }
    }

    private fun changeLoginWay() {
        if (tv_change_login_way.text == getString(R.string.tx_change_login_via_password)) {
            tv_change_login_way.text = getString(R.string.tx_change_login_via_sms_code)
            rl_login_sms.visibility = View.GONE
            rl_login_password.visibility = View.VISIBLE
        } else {
            tv_change_login_way.text = getString(R.string.tx_change_login_via_password)
            rl_login_password.visibility = View.GONE
            rl_login_sms.visibility = View.VISIBLE
        }
    }

    private fun resetPassword() {

    }

    private fun requestVerificationCode() {
        val mobile = et_mobile.text.toString().trim { it <= ' ' }.replace(" ", "")
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showShort(R.string.toast_error_phone_null)
            return
        }
        if (!RegexUtils.isMobileExact(mobile)) {
            ToastUtils.showShort(R.string.toast_error_phone_error)
            return
        }
        // 先短信验证码，闲置30s后切换语音验证码
        if (mIsVoiceVerifyCode) {
            val dialog = NormalDialog(this)
            dialog.content(getString(R.string.tv_login_dialog_voice))
                .style(NormalDialog.STYLE_TWO)
                .titleTextSize(23f)
                .show()
            dialog.setOnBtnClickL(
                OnBtnClickL { dialog.dismiss() },
                OnBtnClickL {
                    dialog.dismiss()
                    SMSSDK.getVoiceVerifyCode(COUNTRY_NUMBER, mobile)
                })
        } else {
            SMSSDK.getVerificationCode(
                COUNTRY_NUMBER, mobile, "10085157"
            ) { _, _ -> false }
        }
        // 禁止点击获取验证码按钮和修改手机号
        tv_request_verification_code.isClickable = false
        et_mobile.isEnabled = false
    }

    private fun checkUserOk(mobile: String, password: String, smsCode: String): Boolean {
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showShort(R.string.toast_error_phone_null)
            return false
        }
        if (!RegexUtils.isMobileExact(mobile)) {
            ToastUtils.showShort(R.string.toast_error_phone_error)
            return false
        }
        if (rl_login_password.visibility == View.VISIBLE && TextUtils.isEmpty(password)) {
            ToastUtils.showShort(R.string.toast_error_password_null)
            return false
        }
        if (rl_login_password.visibility == View.VISIBLE && !CommonUtils.isValidPassword(password)) {
            ToastUtils.showShort(R.string.toast_error_password_error)
            return false
        }
        if (rl_login_sms.visibility == View.VISIBLE && TextUtils.isEmpty(smsCode)) {
            ToastUtils.showShort(R.string.toast_error_sms_null)
            return false
        }
        if (rl_login_sms.visibility == View.VISIBLE && !CommonUtils.isValidSmsCode(smsCode)) {
            ToastUtils.showShort(R.string.toast_error_sms_error, et_sms)
            return false
        }
        return true
    }

    private fun changeAgreementIv() {
        mIsAgreeAgreement = !mIsAgreeAgreement
        iv_agreement.setImageResource(if (mIsAgreeAgreement) R.drawable.check_normal else R.drawable.check_pressed)
    }

    private fun registerSMSSDK() {
        SMSSDK.registerEventHandler(object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                runOnUiThread {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        when (event) {
                            SMSSDK.EVENT_GET_VERIFICATION_CODE -> {
                                // 短信验证 30s 倒计时
                                mTimeCount = TimeCount(30000, 1000)
                                mTimeCount?.start()
                                tx_hint_verify_sms_code.text = getString(R.string.tv_login_sms)
                            }
                            SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE -> {
                                // 请求发送语音验证码，无返回
                                tx_hint_verify_sms_code.text = getString(R.string.tv_login_voice)
                                tv_request_verification_code.text = getString(R.string.tx_voice)
                            }
                        }
                    } else {
                        tv_request_verification_code.isEnabled = true
                        et_mobile.isEnabled = true
                        tv_request_verification_code.text = getString(R.string.tx_please_retry)
                        if (data is Throwable) {
                            ToastUtils.showShort(data.message)
                            data.printStackTrace()
                        }
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterAllEventHandler()
        mTimeCount?.cancel()
    }

    internal inner class TimeCount(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            tv_request_verification_code.text =
                String.format(getString(R.string.countdown_number), millisUntilFinished / 1000)
        }

        override fun onFinish() {
            tv_request_verification_code.isClickable = true
            mIsVoiceVerifyCode = true
            tv_request_verification_code.text = getString(R.string.tx_try_voice)
        }
    }

    companion object {

        private const val COUNTRY_NUMBER = "86"
    }
}