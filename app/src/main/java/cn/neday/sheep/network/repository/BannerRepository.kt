package cn.neday.sheep.network.repository

import cn.neday.sheep.model.Banner
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.api.BannerApi

/**
 * Banner Repository
 *
 * @author nEdAy
 */
class BannerRepository(private val bannerApi: BannerApi) : BaseRepository() {

    suspend fun getBannerList(): Response<List<Banner>> {
        return apiCall { bannerApi.bannerList() }
    }
}