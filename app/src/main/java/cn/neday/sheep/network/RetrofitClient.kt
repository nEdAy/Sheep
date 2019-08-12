package cn.neday.sheep.network


import cn.neday.sheep.BuildConfig
import com.blankj.utilcode.util.Utils
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * 获取Retrofit实例
 *
 * @author nEdAy
 */
object RetrofitClient {

    private const val BASE_URL = "https://www.neday.cn/v1/"

    fun <T> getRetrofit(clazz: Class<T>): T {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(clazz)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(ChuckInterceptor(Utils.getApp()))
        .build()

    private val loggingInterceptor: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            }
            return httpLoggingInterceptor
        }
}
