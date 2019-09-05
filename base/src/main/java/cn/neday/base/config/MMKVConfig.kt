package cn.neday.base.config

import com.blankj.utilcode.util.Utils
import com.tencent.mmkv.MMKV

object MMKVConfig {

    lateinit var kv: MMKV

    fun init() {
        MMKV.initialize(Utils.getApp())
        kv = MMKV.defaultMMKV()
    }

    const val ID = "Id"
    const val TOKEN = "Token"
    const val MOBILE = "Mobile"
    const val HOTWORDS = "HotWords"
    const val HISTORY_WORDS = "HistoryWords"
    const val IS_FIRST_START_APP = "isFirstStartApp"

    fun login(id: Int?, token: String?, mobile: String?) {
        kv.encode(ID, id ?: 0)
        kv.encode(TOKEN, token)
        kv.encode(MOBILE, mobile)
    }

    fun logout() {
        kv.removeValueForKey(ID)
        kv.removeValueForKey(TOKEN)
        kv.removeValueForKey(MOBILE)
    }
}