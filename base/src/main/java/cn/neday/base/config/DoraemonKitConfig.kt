package cn.neday.base.config

import com.blankj.utilcode.util.Utils
import com.didichuxing.doraemonkit.DoraemonKit

object DoraemonKitConfig {

    fun init() {
        DoraemonKit.install(Utils.getApp())
    }
}