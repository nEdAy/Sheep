package cn.neday.sheep.model

/**
 * 广告
 */
data class Banner(
    // 标题
    val title: String?,
    // 点击链接
    val url: String?,
    // 图片链接
    val picture: String?,
    // 状态
    val state: Boolean?
) : BaseModel()
