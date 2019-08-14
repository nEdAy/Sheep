package cn.neday.sheep

import android.app.Application
import cn.neday.sheep.config.BuglyConfig
import cn.neday.sheep.config.LogConfig
import cn.neday.sheep.config.UmengConfig
import cn.neday.sheep.di.httpClientModule
import cn.neday.sheep.di.serviceModule
import cn.neday.sheep.di.viewModelModule
import cn.neday.sheep.util.AliTradeHelper
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.didichuxing.doraemonkit.DoraemonKit
import com.mob.MobSDK
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * 自定义全局Application类
 *
 * @author nEdAy
 */
class ThisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        LogConfig.init()
        UmengConfig.init()
        // Start Koin
        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()
            // use the Android context given there
            androidContext(this@ThisApplication)
            // load properties from assets/koin.properties file
            androidFileProperties()
            // module list
            modules(listOf(serviceModule, httpClientModule, viewModelModule))
        }
        if (ProcessUtils.isMainProcess()) {
            BuglyConfig.init()
            AliTradeHelper.asyncInit()
            Hawk.init(this).build()
            DoraemonKit.install(this)
            MobSDK.init(this)
        }
    }
}