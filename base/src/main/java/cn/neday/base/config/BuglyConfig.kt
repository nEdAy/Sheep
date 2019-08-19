package cn.neday.base.config

import cn.neday.base.BuildConfig
import com.blankj.utilcode.util.Utils
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

object BuglyConfig {

    private const val BUGLY_APP_ID = "923c0825a2"

    fun init() {
        Bugly.init(Utils.getApp(), BUGLY_APP_ID, BuildConfig.DEBUG)
    }

    fun checkUpgrade() {
        Beta.checkUpgrade()
    }
}