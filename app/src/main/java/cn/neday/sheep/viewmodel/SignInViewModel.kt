package cn.neday.sheep.viewmodel

import cn.neday.base.SingleLiveEvent
import cn.neday.base.config.MMKVConfig.ID
import cn.neday.base.config.MMKVConfig.kv
import cn.neday.base.network.requestAsync
import cn.neday.base.network.then
import cn.neday.base.viewmodel.BaseViewModel
import cn.neday.sheep.model.User
import cn.neday.sheep.network.repository.UserRepository

/**
 * SignInViewModel
 *
 * @author nEdAy
 */
class SignInViewModel(private val repository: UserRepository) : BaseViewModel() {

    val user = SingleLiveEvent<User>()

    /**
     * 获取当前用户信息
     */
    fun getUserById() {
        requestAsync {
            repository.getUserById(kv.decodeInt(ID))
        }.then({
            user.value = it.data
        }, {
            errMsg.value = it
        })
    }
}