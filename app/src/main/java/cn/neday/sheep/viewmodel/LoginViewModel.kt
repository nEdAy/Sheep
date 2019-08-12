package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.User
import cn.neday.sheep.network.repository.UserRepository
import com.blankj.utilcode.util.EncryptUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * LoginViewModel
 *
 * @author nEdAy
 */
class LoginViewModel : BaseViewModel() {

    val user: MutableLiveData<User> = MutableLiveData()

    private val repository by lazy { UserRepository() }

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
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.registerOrLogin(mobile, passwordMD5, smsCode, inviteCode)
            }
            executeResponse(response, {
                user.value = response.data
            }, { errMsg.value = response.msg })
        }
    }
}