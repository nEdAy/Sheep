package cn.neday.sheep.network.repository

import cn.neday.sheep.model.Banner
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.ServiceManager
import org.koin.core.context.GlobalContext.get

/**
 * Banner Repository
 *
 * @author nEdAy
 */
class BannerRepository : BaseRepository() {

    suspend fun getBannerList(): Response<List<Banner>> {
        return apiCall { get().koin.get<ServiceManager>().bannerApi.bannerList() }
    }
}