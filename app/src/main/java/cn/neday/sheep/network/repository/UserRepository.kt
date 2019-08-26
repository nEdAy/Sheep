package cn.neday.sheep.network.repository

import cn.neday.base.model.Response
import cn.neday.sheep.model.User
import cn.neday.sheep.network.api.UserApi

/**
 * User Repository
 *
 * @author nEdAy
 */
class UserRepository(private val userApi: UserApi) {

    suspend fun registerOrLogin(register: Map<String, String>): Response<User> =
        userApi.registerOrLogin(register)
}