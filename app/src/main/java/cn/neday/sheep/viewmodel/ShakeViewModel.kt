package cn.neday.sheep.viewmodel

import androidx.lifecycle.viewModelScope
import cn.neday.base.SingleLiveEvent
import cn.neday.base.config.MMKVConfig
import cn.neday.base.config.MMKVConfig.kv
import cn.neday.base.network.requestAsync
import cn.neday.base.network.then
import cn.neday.base.viewmodel.BaseViewModel
import cn.neday.sheep.model.User
import cn.neday.sheep.network.repository.UserRepository

/**
 * ShakeViewModel
 *
 * @author nEdAy
 */
class ShakeViewModel(private val repository: UserRepository) : BaseViewModel() {

    val user = SingleLiveEvent<User>()

    /**
     * 获取当前用户信息
     */
    fun getUserById() {
        requestAsync {
            repository.getUserById(kv.decodeInt(MMKVConfig.ID))
        }.then(viewModelScope, {
            user.value = it.data
        }, {
            errMsg.value = it
        })
    }
}