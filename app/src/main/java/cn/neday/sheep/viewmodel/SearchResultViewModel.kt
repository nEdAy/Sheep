package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.config.HawkConfig
import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.network.repository.GoodsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class SearchResultViewModel : BaseViewModel() {

    private val repository by lazy { GoodsRepository() }

    val pageGoods: MutableLiveData<Pages<CommonGoods>> = MutableLiveData()

    var mCurrentPageId: String = LOAD_INITIAL_PAGE_ID

    fun getDtkSearchGoods(keyWords: String, pageId: String = GoodsListViewModel.LOAD_INITIAL_PAGE_ID) {
        addHistoryWords(keyWords)
        mCurrentPageId = pageId
        launch {
            val response = withContext(Dispatchers.IO) { repository.getDtkSearchGoods(PAGE_SIZE, pageId, keyWords) }
            executeResponse(response, { pageGoods.value = response.data }, { errMsg.value = response.msg })
        }
    }

    private fun addHistoryWords(keyWord: String) {
        val historyWordsString: String? = Hawk.get(HawkConfig.HISTORY_WORDS)
        var historyWords: LinkedHashSet<String> = linkedSetOf()
        if (historyWordsString != null) {
            historyWords = Gson().fromJson(historyWordsString, object : TypeToken<LinkedHashSet<String>>() {}.type)
            // 如果存在keyword，先移除
            historyWords.remove(keyWord)
            // 如果历史记录大于MAX_SIZE条, 移除最初的历史记录直到小于MAX_SIZE条
            while (historyWords.size >= HISTORY_KEYWORD_MAX_SIZE) {
                historyWords.remove(historyWords.first())
            }
        }
        // 加入新的keyword
        historyWords.add(keyWord)
        Hawk.put(HawkConfig.HISTORY_WORDS, Gson().toJson(historyWords))
    }

//    /**
//     * 超级搜索
//     * 通过关键字进行搜索，提供大淘客商品库的精准搜索结果及联盟的相关商品结果，为您带来优质的搜索体验，可用在CMS/APP的搜索引擎
//     *
//     * @param type 搜索类型 0-综合结果，1-大淘客商品，2-联盟商品
//     * @param keyWords 关键词搜索
//     * @param tmall 是否天猫商品 1-天猫商品，0-所有商品，不填默认为0
//     * @param haitao 是否海淘商品	 1-海淘商品，0-所有商品，不填默认为0
//     * @param sort 排序字段信息 销量（total_sales） 价格（price），排序_des（降序），排序_asc（升序）
//     */
//    fun getListSuperGoods(type: Int, keyWords: String, tmall: Int, haitao: Int, sort: String) {
//        launch {
//                val response = withContext(Dispatchers.IO) {
//                    repository.getListSuperGoods(type, keyWords, tmall, haitao, sort)
//                }
//                executeResponse(response, {
//                    mGoods.value = response.data
//                }, { errMsg.value = response.msg })
//        }
//    }

    companion object {

        const val HISTORY_KEYWORD_MAX_SIZE = 20

        private const val PAGE_SIZE = 50
        const val LOAD_INITIAL_PAGE_ID = "1"
    }
}