package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.base.network.requestAsync
import cn.neday.base.network.then
import cn.neday.base.viewmodel.BaseViewModel
import cn.neday.sheep.model.Banner
import cn.neday.sheep.model.RankingGoods
import cn.neday.sheep.network.repository.BannerRepository
import cn.neday.sheep.network.repository.GoodsRepository

/**
 * IndexViewModel
 *
 * @author nEdAy
 */
class IndexViewModel(private val bannerRepository: BannerRepository, private val goodsRepository: GoodsRepository) :
    BaseViewModel() {

    val banners: MutableLiveData<List<Banner>> = MutableLiveData()
    val rankGoods: MutableLiveData<List<RankingGoods>> = MutableLiveData()

    fun getBannerList() {
        requestAsync {
            bannerRepository.getBannerList()
        }.then({
            banners.value = it.data
        }, {
            errMsg.value = it
        })
    }

    fun getRankingList(rankType: Int, cid: String = "") {
        requestAsync {
            goodsRepository.getRankingList(rankType, cid)
        }.then({
            rankGoods.value = it.data
        }, {
            errMsg.value = it
        }, {
            onComplete.call()
        })
    }
}