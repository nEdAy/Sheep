package cn.neday.base.model

import com.squareup.moshi.Json

open class BaseModel {
    @Json(name = "ID")
    val id: Int? = null
    @Json(name = "CreateAt")
    val createAt: String? = null
    @Json(name = "UpdatedAt")
    val updatedAt: String? = null
    @Json(name = "DeletedAt")
    val deletedAt: String? = null
}