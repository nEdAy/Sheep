package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.config.HawkConfig
import cn.neday.sheep.model.HotWords
import cn.neday.sheep.network.repository.CategoryRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * SearchViewModel
 *
 * @author nEdAy
 */
class SearchViewModel : BaseViewModel() {

    val hotWords: MutableLiveData<HotWords> = MutableLiveData()
    val historyWords: MutableLiveData<LinkedHashSet<String>> = MutableLiveData()

    private val repository by lazy { CategoryRepository() }

    /**
     * 热搜记录
     * 该接口提供了昨日CMS端大淘客采集统计的前100名搜索热词
     */
    fun getHotWords() {
        val hotWords: HotWords? = Hawk.get(HawkConfig.HOTWORDS)
        if (hotWords != null) {
            this.hotWords.value = hotWords
        }
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getTop100()
            }
            executeResponse(response, {
                this@SearchViewModel.hotWords.value = response.data
                Hawk.put(HawkConfig.HOTWORDS, response.data)
            }, { errMsg.value = response.msg })
        }
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