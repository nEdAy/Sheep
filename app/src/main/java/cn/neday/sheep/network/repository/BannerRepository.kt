package cn.neday.sheep.network.repository

import cn.neday.base.model.Response
import cn.neday.base.network.repository.BaseRepository
import cn.neday.sheep.model.Banner
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