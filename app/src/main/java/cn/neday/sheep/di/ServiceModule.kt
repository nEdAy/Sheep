package cn.neday.sheep.di

import cn.neday.sheep.network.ServiceManager
import cn.neday.sheep.network.api.BannerApi
import cn.neday.sheep.network.api.CategoryApi
import cn.neday.sheep.network.api.GoodsApi
import cn.neday.sheep.network.api.UserApi
import cn.neday.sheep.network.repository.BannerRepository
import cn.neday.sheep.network.repository.CategoryRepository
import cn.neday.sheep.network.repository.GoodsRepository
import cn.neday.sheep.network.repository.UserRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {

    single<BannerApi> {
        get<Retrofit>().create(BannerApi::class.java)
    }

    single { BannerRepository() }

    single<CategoryApi> {
        get<Retrofit>().create(CategoryApi::class.java)
    }

    single { CategoryRepository() }

    single<GoodsApi> {
        get<Retrofit>().create(GoodsApi::class.java)
    }

    single { GoodsRepository() }

    single<UserApi> {
        get<Retrofit>().create(UserApi::class.java)
    }

    single { UserRepository() }

    single {
        ServiceManager(get(), get(), get(), get())
    }
}