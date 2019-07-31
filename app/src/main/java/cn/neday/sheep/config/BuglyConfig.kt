package cn.neday.sheep.config

import cn.neday.sheep.BuildConfig
import com.blankj.utilcode.util.Utils
import com.tencent.bugly.Bugly

object BuglyConfig {

    private const val BUGLY_APP_ID = "923c0825a2"

    fun init() {
        Bugly.init(Utils.getApp(), BUGLY_APP_ID, BuildConfig.DEBUG)
    }
}