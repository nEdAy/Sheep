package cn.neday.alibaba

import android.app.Activity
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import cn.neday.base.util.ClipboardUtils
import com.alibaba.baichuan.android.trade.AlibcTrade
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback
import com.alibaba.baichuan.android.trade.model.AlibcShowParams
import com.alibaba.baichuan.android.trade.model.OpenType
import com.alibaba.baichuan.android.trade.page.AlibcBasePage
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType
import com.alibaba.baichuan.trade.biz.context.AlibcResultType
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams
import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils


/**
 * AliTradeHelper
 * 阿里接口方法
 *
 * @author nEdAy
 */
class AliTradeHelper(private val activity: Activity?) {

    /**
     * 打开电商组件
     *
     * @param alibcPage 页面类型,必填，不可为null，详情见下面tradePage类型介绍
     */
    internal fun showAlibcTradePage(alibcPage: AlibcBasePage, bizCode: String) {
        if (activity != null) {
            AlibcTrade.openByBizCode(
                activity,
                alibcPage,
                null,
                WebViewClient(),
                WebChromeClient(),
                bizCode,
                showParams,
                taokeParams,
                trackParams,
                AliTradeCallback()
            )
        }
    }

    /**
     * 以显示传入url的方式打开页面（第二个参数是套件名称）
     *
     * @param url 打开页面Url
     */
    internal fun openByUrl(url: String) {
        if (activity != null) {
            AlibcTrade.openByUrl(
                activity,
                "",
                url,
                null,
                WebViewClient(),
                WebChromeClient(),
                showParams,
                taokeParams,
                trackParams,
                AliTradeCallback()
            )
        }
    }

    // 登录
    internal fun login() {
        val alibcLogin = AlibcLogin.getInstance()
        alibcLogin.showLogin(AliLoginCallback())
    }

    // 登出
    internal fun logout() {
        val alibcLogin = AlibcLogin.getInstance()
        alibcLogin.logout(AliLoginCallback())
    }

    /**
     * 淘宝服务接口方法的回调
     */
    private inner class AliLoginCallback : AlibcLoginCallback {
        // loginResult(0--登录初始化成功；1--登录初始化完成；2--登录成功；3--登出成功) openId：用户id userNick: 用户昵称
        override fun onSuccess(loginResult: Int, openId: String, userNick: String) {
            when (loginResult) {
                0 -> ToastUtils.showShort("登录初始化成功")
                1 -> ToastUtils.showShort("登录初始化完成")
                2 -> ToastUtils.showShort("登录成功 openId = $openId, userNick = $userNick")
                3 -> ToastUtils.showShort("登出成功")
                else -> {
                    // do nothing
                }
            }
            LogUtils.e("操作成功 $loginResult")
        }

        // code：错误码  msg： 错误信息
        override fun onFailure(code: Int, msg: String) {
            ToastUtils.showShort("流程出错, 错误码 = $code, 错误信息 = $msg")
            LogUtils.e("操作失败 $code$msg")
        }
    }

    /**
     * 淘宝服务接口方法的回调
     */
    private inner class AliTradeCallback : AlibcTradeCallback {
        override fun onTradeSuccess(tradeResult: AlibcTradeResult) {
            if (tradeResult.resultType == AlibcResultType.TYPECART) {
                ToastUtils.showShort("加购成功")
            } else if (tradeResult.resultType == AlibcResultType.TYPEPAY) {
                val orderId = tradeResult.payResult.paySuccessOrders.toString()
                // 复制数据到剪切板
                ClipboardUtils.copyText(orderId)
                ToastUtils.showShort("支付成功,已复制订单号" + orderId + "到剪切板")
            }
            // 打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
            LogUtils.e("操作成功 $tradeResult")
        }

        // code：错误码；msg：错误信息
        override fun onFailure(code: Int, msg: String) {
            // 打开电商组件，用户操作中错误信息回调。
            ToastUtils.showShort("流程出错, 错误码 = $code, 错误信息 = $msg")
            LogUtils.e("操作失败 $code$msg")
        }
    }

    companion object {

        private val showParams = AlibcShowParams().apply {
            // OpenType（页面打开方式）： 枚举值（Auto和Native），Native表示唤端，Auto表示不做设置
            openType = OpenType.Auto
            // clientType表示唤端类型：taobao---唤起淘宝客户端；tmall---唤起天猫客户端
            clientType = "taobao"
            // BACK_URL（小把手）：唤端返回的scheme(如果不传默认将不展示小把手；如果想展示小把手，可以自己传入自定义的scheme，或者传入百川提供的默认scheme："alisdk://")
            backUrl = "alisdk://"
            // AlibcFailModeType（唤端失败模式）： 枚举值如下
            // AlibcNativeFailModeNONE：不做处理；
            // AlibcNativeFailModeJumpBROWER：跳转浏览器；
            // AlibcNativeFailModeJumpDOWNLOAD：跳转下载页；
            // AlibcNativeFailModeJumpH5：应用内webview打开）
            //（注：AlibcNativeFailModeJumpBROWER不推荐使用）
            nativeOpenFailedMode = AlibcFailModeType.AlibcNativeFailModeJumpDOWNLOAD
        }
        // 淘客Pid
        private const val DEFAULT_TAOKE_PID = "mm_108668197_20820254_70484723"
        // 1、如果走adzoneId的方式分佣打点，需要在extraParams中显式传入taokeAppkey，否则打点失败；
        // 2、如果是打开店铺页面(shop)，需要在extraParams中显式传入sellerId，否则同步打点转链失败）
        private val taokeParams = AlibcTaokeParams(DEFAULT_TAOKE_PID, "", "").apply {
            adzoneid = "70484723"
            extraParams = HashMap()
            // adzoneid是需要taokeAppkey参数才可以转链成功
            extraParams["taokeAppkey"] = "27697448"
            // 店铺页面需要卖家id（sellerId）
            // extraParams["sellerId"] = "xxxxx"
        }
        private val trackParams = HashMap<String, String>().apply {
            put("isv_code", BuildConfig.VERSION_NAME)
        }

        /**
         * 百川电商SDK初始化【异步】
         */
        fun asyncInit() {
            AlibcTradeSDK.asyncInit(Utils.getApp(), object : AlibcTradeInitCallback {
                override fun onSuccess() {
                    // 初始化成功，设置相关的全局配置参数
                    // 是否使用支付宝
                    AlibcTradeSDK.setShouldUseAlipay(true)
                    // 设置是否使用同步淘客打点
                    AlibcTradeSDK.setSyncForTaoke(true)
                    // 设置全局淘客参数，方便开发者用同一个淘客参数，不需要在show接口重复传入
                    AlibcTradeSDK.setTaokeParams(taokeParams)
                    // 设置渠道信息（如果有特殊渠道专享价，需要设置，默认不要使用）
                    // 注意：初始化完成后调用才能生效
                    // @param typeName : 渠道类型（默认为：0）
                    // @param channelName : 渠道名称（默认为：null）
                    AlibcTradeSDK.setChannel("0", null)
                    // 设置isv版本
                    // 提供isvcode全局接口
                    // 设置isv的版本 ，默认1.0.0
                    // 注意：初始化完成后调用才能生效
                    // @param isvVersion (默认1.0.0)
                    // @return 返回是否设置成功
                    AlibcTradeSDK.setISVVersion(BuildConfig.VERSION_NAME)
                    LogUtils.i("阿里百川TradeSDK初始化成功")
                }

                override fun onFailure(code: Int, msg: String) {
                    // 初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                    LogUtils.e("阿里百川TradeSDK初始化失败$code$msg")
                }
            })
        }
    }
}