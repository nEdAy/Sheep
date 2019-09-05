package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.base.model.Pages
import cn.neday.base.network.requestAsync
import cn.neday.base.network.then
import cn.neday.base.viewmodel.BaseViewModel
import cn.neday.sheep.LOAD_INITIAL_PAGE_ID
import cn.neday.sheep.model.CreditHistory
import cn.neday.sheep.network.repository.UserRepository

/**
 * CreditHistoryViewModel
 *
 * @author nEdAy
 */
class CreditHistoryViewModel(private val repository: UserRepository) : BaseViewModel() {

    val creditHistories: MutableLiveData<Pages<CreditHistory>> = MutableLiveData()

    var mCurrentPageId: String = LOAD_INITIAL_PAGE_ID

    fun getCreditByUserId() {
//        requestAsync {
//            repository.getUserById("DDD")
//        }.then({
////            creditHistories.value = it.data
//        }, {
//            errMsg.value = it
//        })
    }


    fun getCreditHistoryListByUserId(userId: Int) {
        requestAsync {
            repository.getCreditHistoryListByUserId(userId)
        }.then({
            creditHistories.value = it.data
        }, {
            errMsg.value = it
        })
    }
}