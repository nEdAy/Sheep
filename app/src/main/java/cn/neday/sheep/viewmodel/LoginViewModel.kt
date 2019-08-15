package cn.neday.sheep.viewmodel

import cn.neday.sheep.SingleLiveEvent
import cn.neday.sheep.model.User
import cn.neday.sheep.network.repository.UserRepository
import cn.neday.sheep.network.requestAsync
import cn.neday.sheep.network.then
import com.blankj.utilcode.util.EncryptUtils

/**
 * LoginViewModel
 *
 * @author nEdAy
 */
class LoginViewModel(private val repository: UserRepository) : BaseViewModel() {

    val user = SingleLiveEvent<User>()

    /**
     * 注册用户 / 用户登录(密码) / 用户登录（短信验证码）
     *
     * @param mobile 手机号
     * @param password 原始密码
     * @param smsCode 短信验证码
     * @param inviteCode 邀请码
     */
    fun registerOrLogin(mobile: String, password: String, smsCode: String, inviteCode: String) {
        val passwordMD5 = EncryptUtils.encryptMD5ToString(password).toUpperCase()
        requestAsync {
            repository.registerOrLogin(mobile, passwordMD5, smsCode, inviteCode)
        }.then({
            user.value = it.data
        }, {
            errMsg.value = it
        })
    }
}