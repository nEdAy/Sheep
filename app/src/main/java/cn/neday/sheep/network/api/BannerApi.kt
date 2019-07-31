package cn.neday.sheep.network.api

import cn.neday.sheep.model.Banner
import cn.neday.sheep.model.Response
import retrofit2.http.GET

interface BannerApi {

    @GET("banner")
    suspend fun bannerList(): Response<List<Banner>>
}
