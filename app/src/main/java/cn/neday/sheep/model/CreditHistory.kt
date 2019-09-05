package cn.neday.sheep.model

import cn.neday.base.model.BaseModel

data class CreditHistory(
    val userId: Int?,
    val credit: Int?,
    // 增减数值
    val change: Int?,
    // 增减原因
    val message: String?
) : BaseModel()