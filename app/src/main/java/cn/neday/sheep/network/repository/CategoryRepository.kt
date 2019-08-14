package cn.neday.sheep.network.repository

import cn.neday.sheep.model.HotWords
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.ServiceManager
import org.koin.core.context.GlobalContext

/**
 * RankingGoods Repository
 *
 * @author nEdAy
 */
class CategoryRepository : BaseRepository() {

    suspend fun getTop100(): Response<HotWords> {
        return apiCall { GlobalContext.get().koin.get<ServiceManager>().categoryApi.getTop100() }
    }
}