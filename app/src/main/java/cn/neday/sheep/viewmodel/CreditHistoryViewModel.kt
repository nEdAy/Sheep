package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.config.HawkConfig
import cn.neday.sheep.model.HotWords
import cn.neday.sheep.network.repository.CategoryRepository
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * CreditHistoryViewModel
 *
 * @author nEdAy
 */
class CreditHistoryViewModel : BaseViewModel() {

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
                this@CreditHistoryViewModel.hotWords.value = response.data
                Hawk.put(HawkConfig.HOTWORDS, response.data)
            }, { errMsg.value = response.msg })
        }
    }
}