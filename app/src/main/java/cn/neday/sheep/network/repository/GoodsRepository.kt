package cn.neday.sheep.network.repository

import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.model.RankingGoods
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.ServiceManager
import cn.neday.sheep.network.api.GoodsApi
import org.koin.core.context.GlobalContext

/**
 * RankingGoods Repository
 *
 * @author nEdAy
 */
class GoodsRepository : BaseRepository() {

    private val goodsApi: GoodsApi by lazy { GlobalContext.get().koin.get<ServiceManager>().goodsApi }

    suspend fun getRankingList(rankType: Int, cid: String): Response<List<RankingGoods>> {
        return apiCall { goodsApi.rankingList(rankType, cid) }
    }

    suspend fun getNineOpGoodsList(pageSize: Int, pageId: String, cid: String): Response<Pages<CommonGoods>> {
        return apiCall { goodsApi.nineOpGoodsList(pageSize, pageId, cid) }
    }

    suspend fun getDtkSearchGoods(pageSize: Int, pageId: String, keyWords: String): Response<Pages<CommonGoods>> {
        return apiCall { goodsApi.getDtkSearchGoods(pageSize, pageId, keyWords) }
    }

    suspend fun getListSimilerGoodsByOpen(id: Int, size: Int): Response<List<CommonGoods>> {
        return apiCall { goodsApi.listSimilerGoodsByOpen(id, size) }
    }

    suspend fun getListSuperGoods(
        type: Int, keyWords: String,
        tmall: Int, haitao: Int, sort: String
    ): Response<List<CommonGoods>> {
        return apiCall { goodsApi.listSuperGoods(type, keyWords, tmall, haitao, sort) }
    }
}