package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.neday.base.network.requestAsync
import cn.neday.base.network.then
import cn.neday.base.viewmodel.BaseViewModel
import cn.neday.sheep.model.RankingGoods
import cn.neday.sheep.network.repository.GoodsRepository

/**
 * RankingListViewModel
 *
 * @author nEdAy
 */
class RankingListViewModel(private val repository: GoodsRepository) : BaseViewModel() {

    val rankGoods: MutableLiveData<List<RankingGoods>> = MutableLiveData()

    fun getRankingList(rankType: Int, cid: String = "") {
        requestAsync {
            repository.getRankingList(rankType, cid)
        }.then(viewModelScope, {
            rankGoods.value = it.data
        }, {
            errMsg.value = it
        }, {
            onComplete.call()
        })
    }
}