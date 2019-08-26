package cn.neday.sheep.network.repository

import cn.neday.base.model.Response
import cn.neday.sheep.model.HotWords
import cn.neday.sheep.network.api.CategoryApi

/**
 * RankingGoods Repository
 *
 * @author nEdAy
 */
class CategoryRepository(private val categoryApi: CategoryApi) {

    suspend fun getTop100(): Response<HotWords> = categoryApi.getTop100()
}