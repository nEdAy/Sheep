package cn.neday.base.router

import android.app.Activity
import com.alibaba.android.arouter.facade.template.IProvider

interface AlibabaService : IProvider {

    fun destroySDK()

    fun showDetailPage(activity: Activity?, itemId: String?)

    fun showShopPage(activity: Activity?, shopId: String?)

    fun showAddCartPage(activity: Activity?, itemId: String?)

    fun showMyOrdersPage(activity: Activity?, status: Int, allOrder: Boolean)

    fun showMyCartsPage(activity: Activity?)

    fun showItemURLPage(activity: Activity?, url: String?)
}