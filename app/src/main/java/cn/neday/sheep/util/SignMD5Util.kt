package cn.neday.sheep.util

import com.blankj.utilcode.util.EncryptUtils
import java.util.*

/**
 * 大淘客 SignMD5Util
 */
object SignMD5Util {
    // 应用App Secret
    private const val DATAOKE_APP_SECRET = "b4cd70a1cb837acefdbaa7a646cd050b"
    // 应用唯一验证App Key
    private const val DATAOKE_APP_KEY = "5d0a08100bfcc"
    // API接口版本号 当前版本： v1.0.0
    private const val DATAOKE_VERSION = "v1.0.0"

    /**
     * 获取签名的util
     *
     * @param parameterMap other parameter
     * @return sign appKey & version & sign & other parameter
     */
    fun getSignParameterMap(parameterMap: SortedMap<String, Any>): SortedMap<String, Any> {
        parameterMap["version"] = DATAOKE_VERSION
        parameterMap["appKey"] = DATAOKE_APP_KEY
        val stringBuilder = StringBuilder()
        val keySet = parameterMap.keys
        for (key in keySet) {
            stringBuilder.append("&").append(key).append("=").append(parameterMap[key])
        }
        stringBuilder.deleteCharAt(0)
        val parameterString = stringBuilder.toString()
        parameterMap["sign"] = EncryptUtils.encryptMD5ToString("$parameterString&key=$DATAOKE_APP_SECRET").toUpperCase()
        return parameterMap
    }
}

