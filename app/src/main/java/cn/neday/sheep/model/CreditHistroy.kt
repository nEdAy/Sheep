package cn.neday.sheep.model

import cn.neday.base.model.BaseModel

data class CreditHistroy(
    // 增减数值
    val value: Int?,
    // 增减原因
    val message: String?
) : BaseModel()