package cn.neday.sheep.enum

/**
 * 淘宝订单
 * 0：全部
 * 1：待付款
 * 2：待发货
 * 3：待收货
 * 4：待评价
 */
enum class OrderType(val index: Int) {
    ALL(0),
    DAT_FU_KUAN(1),
    DAT_FA_HUO(2),
    DAT_SHOU_HUO(3),
    DAY_PING_JIA(4)
}