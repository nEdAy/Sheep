package cn.neday.sheep.viewmodel

import androidx.lifecycle.viewModelScope
import cn.neday.base.SingleLiveEvent
import cn.neday.base.network.requestAsync
import cn.neday.base.network.then
import cn.neday.base.viewmodel.BaseViewModel
import cn.neday.sheep.model.User
import cn.neday.sheep.network.repository.UserRepository
import com.blankj.utilcode.util.EncryptUtils
import java.util.*

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
        val map = HashMap<String, String>()
        map["mobile"] = mobile
        map["password"] = EncryptUtils.encryptMD5ToString(password).toUpperCase(Locale.getDefault())
        map["smsCode"] = smsCode
        map["inviteCode"] = inviteCode
        requestAsync {
            repository.registerOrLogin(map)
        }.then(viewModelScope, {
            user.value = it.data
        }, {
            errMsg.value = it
        })
    }
}