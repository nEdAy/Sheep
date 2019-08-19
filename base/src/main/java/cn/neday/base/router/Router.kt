package cn.neday.base.router

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * SMT Router
 * ARouter Helper
 *
 * @author Kevin.SuTJ
 */
object Router {
    /**
     * Get instance of router. A
     * All feature U use, will be starts here.
     */
    private val instance = ARouter.getInstance()

    val alibabaService = getService(AlibabaService::class.java)
    val appService = getService(AppService::class.java)

    /**
     * Build the road map, draw a postcard.
     *
     * @param path Where you go.
     */
    fun build(path: String): Postcard {
        return instance.build(path)
    }

    /**
     * Navigation to the route with path in postcard.
     * No param, will be use application context.
     */
    fun navigation(path: String) {
        build(path).navigation()
    }

    /**
     * Launch the navigation by type
     *
     * @param service interface of service
     * @param <T>     return type
     * @return instance of service
    </T> */
    private fun <T> getService(service: Class<T>): T {
        return instance.navigation(service)
    }
}