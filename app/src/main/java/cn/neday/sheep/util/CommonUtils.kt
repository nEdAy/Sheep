package cn.neday.sheep.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.view.View
import androidx.core.net.toUri
import java.math.BigDecimal

/**
 * 常用工具
 *
 * @author nEdAy
 */
object CommonUtils {

    /**
     * 去除数字里多余的0
     */
    fun getPrettyNumber(number: Double?): String {
        return number?.let { BigDecimal.valueOf(it).stripTrailingZeros().toPlainString() } ?: "0"
    }

    /**
     * 检验密码长度是否正确
     */
    fun isValidPassword(target: CharSequence?): Boolean {
        return target != null && target.length > 5 && target.length < 17
    }

    /**
     * 检验短信验证码长度是否正确
     */
    fun isValidSmsCode(target: CharSequence?): Boolean {
        return target != null && target.length == 4
    }

    /**
     * 点击时修改子控件背景样式并在0.5s后恢复
     *
     * @param view       要修改的子控件
     * @param bg         要恢复的背景
     * @param bgPressed 要变化的背景
     */
    @SuppressLint("CheckResult")
    fun changePressedViewBg(view: View, bg: Int, bgPressed: Int) {
        view.isPressed = true
        view.setBackgroundResource(bgPressed)
        Handler().postDelayed({
            view.isPressed = false
            view.setBackgroundResource(bg)
        }, 500)
    }

    /**
     * 发起添加群流程。群号：神马快爆15群(109339243) 的 key 为： seYgbMRGSIw_QOjspDBVp-1r1GTUGHCg
     * 调用 joinQQGroup(seYgbMRGSIw_QOjspDBVp-1r1GTUGHCg) 即可发起手Q客户端申请加群 神马快爆15群(109339243)
     *
     * @return 返回true表示呼起手Q成功，返回false表示呼起失败
     */
    fun joinQQGroup(activity: Activity?): Boolean {
        return try {
            activity?.startActivity(Intent().apply {
                data =
                    ("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "seYgbMRGSIw_QOjspDBVp-1r1GTUGHCg").toUri()
                // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
                // addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
            true
        } catch (e: RuntimeException) {
            // 未安装手Q或安装的版本不支持
            false
        }
    }
}
