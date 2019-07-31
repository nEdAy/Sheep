package cn.neday.sheep.network.api

import cn.neday.sheep.model.HotWords
import cn.neday.sheep.model.Response
import retrofit2.http.GET

interface CategoryApi {

    @GET("category/get-top100")
    suspend fun getTop100(): Response<HotWords>
}
