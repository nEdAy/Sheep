package cn.neday.sheep.network

import cn.neday.sheep.network.api.BannerApi
import cn.neday.sheep.network.api.CategoryApi
import cn.neday.sheep.network.api.GoodsApi
import cn.neday.sheep.network.api.UserApi

data class ServiceManager(
    val bannerApi: BannerApi,
    val categoryApi: CategoryApi,
    val goodsApi: GoodsApi,
    val userApi: UserApi
)