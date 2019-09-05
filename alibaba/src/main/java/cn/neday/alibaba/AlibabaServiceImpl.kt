package cn.neday.alibaba

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import cn.neday.base.router.AlibabaService
import cn.neday.base.router.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage
import com.alibaba.baichuan.android.trade.page.AlibcShopPage
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils

@Route(path = RouterPath.AlibabaService)
class AlibabaServiceImpl : AlibabaService {

    override fun destroySDK() {
        AlibcTradeSDK.destory()
    }

    /**
     * 根据商品ID打开商品页
     *
     * @param itemId 支持itemId和openItemId的商品，必填，不允许为null；
     *               eg.37196464781L；AAHd5d-HAAeGwJedwSnHktBI；
     */
    override fun showDetailPage(activity: Activity?, itemId: String) {
        AliTradeHelper(activity).showAlibcTradePage(AlibcDetailPage(itemId), "detail")
    }

    /**
     * 根据店铺ID打开店铺页
     *
     * @param shopId 店铺id，支持明文id
     */
    override fun showShopPage(activity: Activity?, shopId: String) {
        AliTradeHelper(activity).showAlibcTradePage(AlibcShopPage(shopId), "shop")
    }

    /**
     * 添加指定商品ID的商品到购物车
     *
     * @param itemId 支持itemId和openItemId的商品，必填，不允许为null；
     *               eg.37196464781L；AAHd5d-HAAeGwJedwSnHktBI；
     */
    override fun showAddCartPage(activity: Activity?, itemId: String) {
        ToastUtils.showShort("暂不支持")
        // AliTradeHelper(activity).showAlibcTradePage(AlibcAddCartPage(itemId), "cart")
    }

    /**
     * 打开购物订单页 参数表示的是待发货、已付款等
     *
     * @param status   默认跳转页面；填写：0：全部；1：待付款；2：待发货；3：待收货；4：待评价
     * @param allOrder false 进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
     */
    override fun showMyOrdersPage(activity: Activity?, status: Int, allOrder: Boolean) {
        ToastUtils.showShort("暂不支持")
        // AliTradeHelper(activity).showAlibcTradePage(AlibcMyOrdersPage(status, allOrder), "trade")
    }

    /**
     * 打开用户购物车
     */
    override fun showMyCartsPage(activity: Activity?) {
        AliTradeHelper(activity).showAlibcTradePage(AlibcMyCartsPage(), "cart")
    }

    /**
     * 根据url打开阿里自带的webView
     *
     * @param url 要展示的url
     */
    override fun showItemURLPage(activity: Activity?, url: String) {
        if (!TextUtils.isEmpty(url)) {
            AliTradeHelper(activity).openByUrl(url)
        }
    }

    override fun init(context: Context?) {
        AliTradeHelper.asyncInit()
        LogUtils.d("Alibaba service impl init")
    }
}
