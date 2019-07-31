package cn.neday.sheep.network.repository

import cn.neday.sheep.model.Banner
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.RetrofitClient
import cn.neday.sheep.network.api.BannerApi

/**
 * Banner Repository
 *
 * @author nEdAy
 */
class BannerRepository : BaseRepository() {

    private val bannerApi: BannerApi by lazy { RetrofitClient().getRetrofit(BannerApi::class.java) }

    suspend fun getBannerList(): Response<List<Banner>> {
        return apiCall { bannerApi.bannerList() }
    }
}