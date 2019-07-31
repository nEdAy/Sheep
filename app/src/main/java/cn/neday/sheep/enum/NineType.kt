package cn.neday.sheep.enum

/**
 * 9.9精选
 * -1-精选
 * 1 -居家百货
 * 2 -美食
 * 3 -服饰
 * 4 -配饰
 * 5 -美妆
 * 6 -内衣
 * 7 -母婴
 * 8 -箱包
 * 9 -数码配件
 * 10 -文娱车品
 */
enum class NineType(val index: Int) {
    JING_XUAN(-1),
    JU_JUA_BAI_HUO(1),
    MEI_SHI(2),
    FU_SHI(3),
    PEI_SHI(4),
    MEI_ZHUANG(5),
    NEI_YI(6),
    MU_YING(7),
    XIANG_BAO(8),
    SHU_MA_PEI_JIAN(9),
    WEN_YU_CHE_PIN(10)
}