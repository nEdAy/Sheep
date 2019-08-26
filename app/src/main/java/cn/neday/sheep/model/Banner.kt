package cn.neday.sheep.model

import cn.neday.base.model.BaseModel

/**
 * 广告
 */
data class Banner(
    // 序号
    val index: Int?,
    // 标题
    val title: String?,
    // 图片链接
    val picture: String?,
    // 点击链接
    val url: String?
) : BaseModel()
