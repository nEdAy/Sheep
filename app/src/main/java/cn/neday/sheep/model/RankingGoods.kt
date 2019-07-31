package cn.neday.sheep.model

data class RankingGoods(
    // 榜单名次 Number 1
    val ranking: Int,
    // 佣金比例 Number 20.01
    val commissionRate: Double,
    // 2小时销量 Number 1138
    val twoHoursSales: Int,
    // 当天销量 Number 389
    val dailySales: Int,
    // 热推值 Number 42
    val hotPush: Int,
    // 商品主图 String https://img.alicdn.com/imgextra/i2/748376657/O1CN01LKI9Km1z2x8p5sGeN_!!748376657.jpg
    val pic: String,
    // 商品描述 String ‘多款可选！显瘦高腰韩版阔腿裤五分裤，不起球，不掉色。舒适面料，不挑身材，高腰设计’
    val description: String,
    // 商品原价 Number 22.9
    val originPrice: Double,
    // 佣金类型 Number 3
    val commissionType: Int,
    // onSaleTime String ‘2019-06-03 17:55:18’
    val onSaleTime: String,
    // 活动类型 Number 1
    val activityType: Int,
    // 营销图 Array ‘https://img.alicdn.com/imgextra/i2/1687451966/O1CN01WNuZcl1QOTCM9NsrO_!!1687451966.jpg,https://img.alicdn.com/imgextra/i4/1687451966/O1CN01h2ih4v1QOTCOxlZDj_!!1687451966.jpg‘
    val picList: String,
    // 放单人名称 String 易折网
    val guideName: String,
    // 店铺类型 Number 1
    val istmall: Int,
    // 优惠券使用条件 Number 22.01
    val quanUsageCondition: Double
) : Goods() {

    override fun getPicUrl(): String {
        return pic
    }

    override fun isTmall(): Boolean {
        return istmall == 1
    }
}