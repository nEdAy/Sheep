package cn.neday.sheep.util

import android.app.Activity
import android.text.TextUtils
import cn.neday.sheep.BuildConfig
import com.alibaba.baichuan.android.trade.AlibcTrade
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback
import com.alibaba.baichuan.android.trade.model.AlibcShowParams
import com.alibaba.baichuan.android.trade.model.OpenType
import com.alibaba.baichuan.android.trade.page.*
import com.alibaba.baichuan.trade.biz.context.AlibcResultType
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams
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
     * 根据商品ID打开商品页
     *
     * @param itemId 支持itemId和openItemId的商品，必填，不允许为null；
     *               eg.37196464781L；AAHd5d-HAAeGwJedwSnHktBI；
     */
    fun showDetailPage(itemId: String?) {
        showAlibcTradePage(AlibcDetailPage(itemId))
    }

    /**
     * 根据店铺ID打开店铺页
     *
     * @param shopId 店铺id，支持明文id
     */
    fun showShopPage(shopId: String?) {
        showAlibcTradePage(AlibcShopPage(shopId))
    }

    /**
     * 添加指定商品ID的商品到购物车
     *
     * @param itemId 支持itemId和openItemId的商品，必填，不允许为null；
     *               eg.37196464781L；AAHd5d-HAAeGwJedwSnHktBI；
     */
    fun showAddCartPage(itemId: String?) {
        showAlibcTradePage(AlibcAddCartPage(itemId))
    }

    /**
     * 打开购物订单页 参数表示的是待发货、已付款等
     *
     * @param status   默认跳转页面；填写：0：全部；1：待付款；2：待发货；3：待收货；4：待评价
     * @param allOrder false 进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
     */
    fun showMyOrdersPage(status: Int, allOrder: Boolean) {
        // ToastUtils.showShort("升级中...暂时关闭此功能");
        showAlibcTradePage(AlibcMyOrdersPage(status, allOrder))
    }

    /**
     * 打开用户购物车
     */
    fun showMyCartsPage() {
        // ToastUtils.showShort("升级中...暂时关闭此功能");
        showAlibcTradePage(AlibcMyCartsPage())
    }

    /**
     * 根据url打开阿里自带的webView
     *
     * @param url 要展示的url
     */
    fun showItemURLPage(url: String?) {
        if (!TextUtils.isEmpty(url)) {
            showAlibcTradePage(AlibcPage(url))
        }
    }

    /**
     * 打开电商组件
     *
     * @param alibcPage 页面类型,必填，不可为null，详情见下面tradePage类型介绍
     */
    private fun showAlibcTradePage(alibcPage: AlibcBasePage) {
        if (activity != null) {
            AlibcTrade.show(
                activity,
                alibcPage,
                alibcShowParams,
                alibcTaokeParams,
                alibcExParams,
                AliTradeCallback()
            )
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

        override fun onFailure(code: Int, msg: String) {
            // 打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
            ToastUtils.showShort("流程出错, 错误码 = $code, 错误信息 = $msg")
            LogUtils.e("操作失败 $code$msg")
        }
    }

    companion object {
        // 淘客Pid
        private const val DEFAULT_TAOKE_PID = "mm_108668197_20820254_70484723"
        // 页面打开方式，默认，H5，Native
        private val alibcShowParams = AlibcShowParams(OpenType.Native, false)
        // 淘宝客PID
        private val alibcTaokeParams = AlibcTaokeParams(DEFAULT_TAOKE_PID, "", "").apply {
            adzoneid = "70484723"
            extraParams = HashMap()
            extraParams["taokeAppkey"] = "27697448"
        }
        private val alibcExParams = HashMap<String, String>().apply {
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
                    // 是否走强制H5的逻辑，为true时全部页面均为H5打开
                    AlibcTradeSDK.setForceH5(false)
                    // 设置全局淘客参数，方便开发者用同一个淘客参数，不需要在show接口重复传入
                    AlibcTradeSDK.setTaokeParams(alibcTaokeParams)
                    // 设置渠道信息(如果有渠道专享价，需要设置)
                    // AlibcTradeSDK.setChannel(typeName, channelName)
                    // 设置isvCode
                    AlibcTradeSDK.setISVCode(BuildConfig.VERSION_CODE.toString())
                    // 设置isv的版本 ，通常为三方app版本，可以不进行设置；默认1.0.0
                    AlibcTradeSDK.setISVVersion(BuildConfig.VERSION_NAME)
                    LogUtils.i("百川SDK初始化成功")
                }

                override fun onFailure(code: Int, msg: String) {
                    // 初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                    LogUtils.e("百川SDK初始化失败$code$msg")
                }
            })
        }
    }
}
