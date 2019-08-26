package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.base.model.Pages
import cn.neday.base.network.requestAsync
import cn.neday.base.network.then
import cn.neday.base.viewmodel.BaseViewModel
import cn.neday.sheep.LOAD_INITIAL_PAGE_ID
import cn.neday.sheep.PAGE_SIZE
import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.network.repository.GoodsRepository

/**
 * NineListViewModel
 *
 * @author nEdAy
 */
class NineListViewModel(private val repository: GoodsRepository) : BaseViewModel() {

    val pageGoods: MutableLiveData<Pages<CommonGoods>> = MutableLiveData()

    var mCurrentPageId: String = LOAD_INITIAL_PAGE_ID

    fun getNineOpGoodsList(nineCid: String, pageId: String = LOAD_INITIAL_PAGE_ID) {
        mCurrentPageId = pageId
        requestAsync {
            repository.getNineOpGoodsList(PAGE_SIZE, pageId, nineCid)
        }.then({
            pageGoods.value = it.data
        }, {
            errMsg.value = it
        }, {
            onComplete.call()
        })
    }
}