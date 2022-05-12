package cn.neday.sheep.di

import cn.neday.sheep.network.api.*
import cn.neday.sheep.network.repository.*
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {

    single<BannerApi> {
        get<Retrofit>().create(BannerApi::class.java)
    }
    single { BannerRepository(get()) }

    single<CategoryApi> {
        get<Retrofit>().create(CategoryApi::class.java)
    }
    single { CategoryRepository(get()) }

    single<GoodsApi> {
        get<Retrofit>().create(GoodsApi::class.java)
    }
    single { GoodsRepository(get()) }

    single<UserApi> {
        get<Retrofit>().create(UserApi::class.java)
    }
    single { UserRepository(get()) }

    single<FlickrApi> {
        get<Retrofit>().create(FlickrApi::class.java)
    }
    single { FlickrRepository(get()) }
}