package cn.neday.sheep.network.repository

import cn.neday.base.model.Pages
import cn.neday.base.model.Response
import cn.neday.sheep.model.CreditHistory
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

    suspend fun getUserById(id: Int): Response<User> =
        userApi.getUserById(id)

    suspend fun getCreditHistoryListByUserId(
        userId: Int,
        pageId: String,
        pageSize: Int
    ): Response<Pages<CreditHistory>> =
        userApi.creditHistoryListByUserId(userId, pageId, pageSize)
}