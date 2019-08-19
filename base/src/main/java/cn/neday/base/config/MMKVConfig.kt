package cn.neday.base.config

import com.blankj.utilcode.util.Utils
import com.tencent.mmkv.MMKV

object MMKVConfig {

    fun init() {
        MMKV.initialize(Utils.getApp())
    }

    const val TOKEN = "Token"
    const val MOBILE = "Mobile"
    const val HOTWORDS = "HotWords"
    const val HISTORY_WORDS = "HistoryWords"
    const val IS_FIRST_START_APP = "isFirstStartApp"
}
