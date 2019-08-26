package cn.neday.sheep.network.repository

import cn.neday.base.model.Pages
import cn.neday.base.model.Response
import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.model.RankingGoods
import cn.neday.sheep.network.api.GoodsApi

/**
 * RankingGoods Repository
 *
 * @author nEdAy
 */
class GoodsRepository(private val goodsApi: GoodsApi) {

    suspend fun getRankingList(rankType: Int, cid: String): Response<List<RankingGoods>> =
        goodsApi.rankingList(rankType, cid)

    suspend fun getNineOpGoodsList(
        pageSize: Int,
        pageId: String,
        nineCid: String
    ): Response<Pages<CommonGoods>> = goodsApi.nineOpGoodsList(pageSize, pageId, nineCid)

    suspend fun getDtkSearchGoods(
        pageSize: Int,
        pageId: String,
        keyWords: String
    ): Response<Pages<CommonGoods>> = goodsApi.getDtkSearchGoods(pageSize, pageId, keyWords)

    suspend fun getListSimilerGoodsByOpen(id: Int, size: Int): Response<List<CommonGoods>> =
        goodsApi.listSimilerGoodsByOpen(id, size)

    suspend fun getListSuperGoods(
        type: Int, keyWords: String,
        tmall: Int, haitao: Int, sort: String
    ): Response<List<CommonGoods>> = goodsApi.listSuperGoods(type, keyWords, tmall, haitao, sort)
}