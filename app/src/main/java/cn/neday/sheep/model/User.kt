package cn.neday.sheep.model

import cn.neday.base.model.BaseModel

data class User(
    // 手机号 11位 主键
    val mobile: String?,
    // 用户密码
    val password: String?,
    // 用户昵称
    val nickname: String?,
    // 用户头像URL
    val avatar: String?,
    // 口袋币 即等级 0=0 1>=100 2>=200 3>=500 4>=1000 5>=2000 6>=5000 7>=15000 8>=50000 9>=100000 10>=200000
    val credit: Int?,
    // Token
    val token: String?
) : BaseModel()