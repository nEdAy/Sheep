package cn.neday.sheep.network.api

import cn.neday.base.model.Response
import cn.neday.sheep.model.Banner
import retrofit2.http.GET

interface BannerApi {

    @GET("banner")
    suspend fun bannerList(): Response<List<Banner>>
}
