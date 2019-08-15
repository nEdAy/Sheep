package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.config.HawkConfig
import cn.neday.sheep.model.HotWords
import cn.neday.sheep.network.repository.CategoryRepository
import cn.neday.sheep.network.requestAsync
import cn.neday.sheep.network.start
import cn.neday.sheep.network.then
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.hawk.Hawk
import java.util.*

/**
 * SearchViewModel
 *
 * @author nEdAy
 */
class SearchViewModel(private val repository: CategoryRepository) : BaseViewModel() {

    val hotWords: MutableLiveData<HotWords> = MutableLiveData()
    val historyWords: MutableLiveData<LinkedHashSet<String>> = MutableLiveData()

    /**
     * 热搜记录
     * 该接口提供了昨日CMS端大淘客采集统计的前100名搜索热词
     */
    fun getHotWords() {
        start {
            hotWords.value = Hawk.get(HawkConfig.HOTWORDS) as? HotWords?
        }.requestAsync {
            repository.getTop100()
        }.then({
            hotWords.value = it.data
            Hawk.put(HawkConfig.HOTWORDS, it.data)
        }, {
            errMsg.value = it
        })
    }

    fun getHistoryWords() {
        val historyWordsJson: String? = Hawk.get(HawkConfig.HISTORY_WORDS)
        historyWords.value = Gson().fromJson(historyWordsJson, object : TypeToken<LinkedHashSet<String>>() {}.type)
    }

    fun removeHistoryWords(keyWords: String) {
        val historyWordsJson: String? = Hawk.get(HawkConfig.HISTORY_WORDS)
        val historyWords: LinkedHashSet<String> =
            Gson().fromJson(historyWordsJson, object : TypeToken<LinkedHashSet<String>>() {}.type)
        historyWords.remove(keyWords)
        Hawk.put(HawkConfig.HISTORY_WORDS, Gson().toJson(historyWords))
        this.historyWords.value = historyWords
    }

    fun cleanHistoryWords() {
        historyWords.value = null
        Hawk.delete(HawkConfig.HISTORY_WORDS)
    }
}