package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.network.repository.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * GoodsListViewModel
 *
 * @author nEdAy
 */
class GoodsListViewModel(private val repository: GoodsRepository) : BaseViewModel() {

    val pageGoods: MutableLiveData<Pages<CommonGoods>> = MutableLiveData()

    var mCurrentPageId: String = LOAD_INITIAL_PAGE_ID

    fun getNineOpGoodsList(cid: String, pageId: String = LOAD_INITIAL_PAGE_ID) {
        mCurrentPageId = pageId
        launch {
            val response = withContext(Dispatchers.IO) { repository.getNineOpGoodsList(PAGE_SIZE, pageId, cid) }
            executeResponse(response, { pageGoods.value = response.data }, { errMsg.value = response.msg })
        }
    }

    companion object {

        private const val PAGE_SIZE = 50
        const val LOAD_INITIAL_PAGE_ID = "1"
    }
}