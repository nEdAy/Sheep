package cn.neday.sheep.viewmodel

import cn.neday.base.SingleLiveEvent
import cn.neday.base.config.MMKVConfig.ID
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
     * 注册用户 / 用户登录(密码) / 用户登录（短信验证码）
     *
     * @param mobile 手机号
     * @param password 原始密码
     * @param smsCode 短信验证码
     * @param inviteCode 邀请码
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