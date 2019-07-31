package cn.neday.sheep.enum

/**
 * 各大榜单
 * 1.实时销量榜
 * 2.全天销量榜
 * 3.热推榜
 */
enum class RankType(val index: Int) {
    SHI_SHI_XIAO_XIANG_BANG(1),
    QUAN_TIAN_XIAO_LIANG_BANG(2),
    RE_TUI_BANG(3)
}