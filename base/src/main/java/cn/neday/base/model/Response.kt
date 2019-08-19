package cn.neday.base.model

data class Response<out T>(
    // 时间戳 1556418473705
    val time: Long?,
    // 返回状态，0-正确，其他见错误码列表
    val code: Int?,
    // 返回状态描述 “成功”
    val msg: String?,
    val data: T?
)