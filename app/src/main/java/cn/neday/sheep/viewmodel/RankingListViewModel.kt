package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.RankingGoods
import cn.neday.sheep.network.repository.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * RankingListViewModel
 *
 * @author nEdAy
 */
class RankingListViewModel : BaseViewModel() {

    private val repository by lazy { GoodsRepository() }

    val rankGoods: MutableLiveData<List<RankingGoods>> = MutableLiveData()

    fun getRankingList(rankType: Int, cid: String = "") {
        launch {
            val response = withContext(Dispatchers.IO) { repository.getRankingList(rankType, cid) }
            executeResponse(response, { rankGoods.value = response.data }, { errMsg.value = response.msg })
        }
    }
}