package cn.neday.sheep.network.api


import cn.neday.base.model.Pages
import cn.neday.base.model.Response
import cn.neday.sheep.model.CreditHistory
import cn.neday.sheep.model.User
import retrofit2.http.*

/**
 * User Api
 *
 * @author nEdAy
 */
interface UserApi {

    /**
     * 注册用户 / 用户登录(密码) / 用户登录（短信验证码）
     *
     * @param register   用户信息（帐号，密码MD5，短信验证码，邀请码）
     * @return 用户信息（token、id等）
     */
    @POST("registerOrLogin")
    suspend fun registerOrLogin(@Body register: Map<String, String>): Response<User>

    /**
     * @param id    用户Id
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

    /**
     * 获取当前用户的某个字段信息（返回用户积分信息||返回用户摇一摇信息||返回用户签到信息）
     *
     * @param id    用户Id
     * @param include  指定返回字段
     * @return 对应信息
     */
    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Int, @Query("include") include: String): Response<User>

    /**
     * 获取当前用户所有积分历史记录
     *
     * @param userId    用户Id
     * @param pageId  分页id	是	String	默认为-1
     * @param pageSize 每页条数	是	Number	默认20
     * @return 回调信息
     */
    @GET("creditHistory/{userId}/")
    suspend fun creditHistoryListByUserId(
        @Path("userId") userId: Int,
        @Query("pageId") pageId: String,
        @Query("pageSize") pageSize: Int
    ): Response<Pages<CreditHistory>>

//    /**
//     * 更新用户 需要Session-Token mobile和password可以更改，但是新的mobile不能重复
//     *
//     * @param objectId 用户ID
//     * @param user     需要更新的用户信息
//     * @return 回调信息 "updatedAt": YYYY-mm-dd HH:ii:ss
//     */
//    @PUT("user")
//    suspend fun updateUser(@Path("objectId") objectId: String, @Body user: User): Response<User>

//    /**
//     * 忘记密码
//     *
//     * @param mobile   手机号
//     * @param password 新密码
//     * @param code     验证码
//     * @return 回调信息
//     */
//    @PUT("user")
//    suspend fun resetUserPassword(
//        @Query("mobile") mobile: String,
//        @Query("password") password: String, @Query("code") code: String
//    ): Response<User>
//
//    /**
//     * 重置密码
//     *
//     * @param objectId    用户Id
//     * @param oldPassword 老密码(MD5后)
//     * @param newPassword 新密码(MD5后)
//     * @return 回调信息
//     */
//    @PUT("user/{objectId}")
//    suspend fun updateUserPassword(
//        @Path("objectId") objectId: String,
//        @Query("oldPassword") oldPassword: String, @Query("newPassword") newPassword: String
//    ): Response<User>

//    /**
//     * 签到
//     *
//     * @param objectId 用户Id
//     * @param type     签到类型
//     * @return 签到记录
//     */
//    @GET("signIn/{objectId}")
//    suspend fun signIn(@Path("objectId") objectId: String, @Query("type") type: Int?): Observable<CreditsHistory>
//
//    /**
//     * 摇一摇
//     *
//     * @param objectId 用户Id
//     * @return 摇一摇获得的积分变化值
//     */
//    @GET("shake/{objectId}")
//    suspend fun shake(@Path("objectId") objectId: String): Observable<Int>
}
