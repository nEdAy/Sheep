package cn.neday.sheep.model

data class Pages<out T>(
    val list: List<T>?,
    val totalNum: Int?,
    val pageId: String?
)