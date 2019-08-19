package cn.neday.base.di

import cn.neday.base.BuildConfig
import com.blankj.utilcode.util.Utils
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT_SECONDS = 10L
private const val BASE_URL = "https://www.neday.cn/v1/"

val httpClientModule = module {

    single { Retrofit.Builder() }

    single { OkHttpClient.Builder() }

    single<Retrofit> {
        get<Retrofit.Builder>()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single(named<HttpLoggingInterceptor>()) {
        HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single(named<ChuckInterceptor>()) {
        ChuckInterceptor(Utils.getApp())
    }

    single {
        get<OkHttpClient.Builder>()
            .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(get(named<HttpLoggingInterceptor>()) as Interceptor)
            .addInterceptor(get(named<ChuckInterceptor>()) as Interceptor)
            .build()
    }
}