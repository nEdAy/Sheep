package cn.neday.sheep.model

import java.io.Serializable

abstract class Goods : Serializable {

    // 大淘客的商品id Number 19259135
    val id: Int? = null
    // 淘宝商品id Number 590858626868
    val goodsId: String? = null
    // 淘宝标题	String	“西维里男士睡衣夏季韩版真丝短袖丝绸薄款宽松青年冰丝家居服套装”
    val title: String? = null
    // 短标题 String 【李佳琦推荐】奢华芯肌素颜爆水霜
    val dtitle: String? = null
    // 推广文案	String	“宽松舒适，难以磨损典，雅而优美，倍感丝滑，质感优越，庄严而心仪，简约设计，色调清新脱俗，适合各种场合”
    val desc: String? = null
    // 商品原价	Number	38.5
    val originalPrice: Double? = null
    // 券后价 Number 39.9
    val actualPrice: Double? = null
    // 描述分	Number	4.8
    val descScore: Double? = null
    // 30天销量	Number	1030
    val monthSales: Int? = null
    // 优惠券链接	String ""
    val couponLink: String? = null
    // 优惠券金额	Number	10
    val couponPrice: Double? = null
    // 领券量	Number	1000
    val couponReceiveNum: Int? = null
    // 券总量	Number	7000
    val couponTotalNum: Int? = null
    // 优惠券结束时间	String	“2019-04-08 23:59:59”
    val couponEndTime: String? = null
    // 优惠券开始时间	String	“2019-04-01 00:00:00”
    val couponStartTime: String? = null
    // 优惠券使用条件	String	“38”
    val couponConditions: String? = null

    abstract fun getPicUrl(): String?
    abstract fun isTmall(): Boolean
}
