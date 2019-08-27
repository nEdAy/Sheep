package cn.neday.base.util

import cn.neday.base.BuildConfig
import cn.neday.base.R
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.StringUtils.getString
import com.blankj.utilcode.util.ToastUtils

object SecurityUtils {

    private const val SIGNATURE_RELEASE = "EBB32FEB587FAA0B80C7DB915B3511ECC33BD7FF"

    fun checkIsAppRoot() {
        if (DeviceUtils.isDeviceRooted() && AppUtils.isAppRoot()) {
            ToastUtils.showLong(getString(R.string.root_tips))
        }
    }

    fun checkIsAppDebug() {
        if (!BuildConfig.DEBUG && AppUtils.isAppDebug()) {
            ToastUtils.showLong(getString(R.string.debug_tips))
            AppUtils.exitApp()
        }
    }

    // TODO : 类似token, 修改为网络验证
    fun checkSignature() {
        if (!BuildConfig.DEBUG && StringUtils.equalsIgnoreCase(SIGNATURE_RELEASE, AppUtils.getAppSignatureSHA1())) {
            ToastUtils.showLong(getString(R.string.signature_tips))
            AppUtils.exitApp()
        }
    }
}
