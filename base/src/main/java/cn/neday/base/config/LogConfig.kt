package cn.neday.base.config

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils

object LogConfig {

    fun init() {
        LogUtils.getConfig().isLogSwitch = AppUtils.isAppDebug()
    }
}