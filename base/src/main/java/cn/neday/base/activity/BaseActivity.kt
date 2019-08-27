package cn.neday.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import cn.neday.base.config.MMKVConfig.TOKEN
import cn.neday.base.router.Router
import cn.neday.base.router.RouterPath
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.tencent.mmkv.MMKV

/**
 * Activity基类
 *
 * @author nEdAy
 */
abstract class BaseActivity(@get:LayoutRes val layoutId: Int?) : AppCompatActivity() {

    val kv: MMKV = MMKV.defaultMMKV()

    open val isCheckLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isCheckLogin && StringUtils.isTrimEmpty(kv.decodeString(TOKEN))) {
            Router.navigation(RouterPath.LoginActivity)
            ActivityUtils.finishActivity(this)
        }
        layoutId?.let {
            setContentView(LayoutInflater.from(this).inflate(it, null))
        }
        prepareInitView()
        initView()
    }

    open fun prepareInitView() {
        // do nothing
    }

    /**
     * onCreate
     */
    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        Router.alibabaService.destroySDK()
    }
}