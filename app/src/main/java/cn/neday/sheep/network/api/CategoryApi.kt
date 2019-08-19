package cn.neday.sheep.network.api

import cn.neday.base.model.Response
import cn.neday.sheep.model.HotWords
import retrofit2.http.GET

interface CategoryApi {

    @GET("category/get-top100")
    suspend fun getTop100(): Response<HotWords>
}
