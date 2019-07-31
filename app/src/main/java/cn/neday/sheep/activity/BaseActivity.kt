package cn.neday.sheep.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.ali.auth.third.login.LoginConstants.TOKEN
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.orhanobut.hawk.Hawk
import com.umeng.analytics.MobclickAgent
import io.github.inflationx.viewpump.ViewPumpContextWrapper


/**
 * Activity基类
 *
 * @author nEdAy
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int?

    open val isCheckLogin = false

    lateinit var mContext: Context

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isCheckLogin && StringUtils.isTrimEmpty(Hawk.get(TOKEN))) {
            ActivityUtils.startActivity(LoginActivity::class.java)
            ActivityUtils.finishActivity(this)
        }
        mContext = this
        layoutId?.let {
            setContentView(LayoutInflater.from(this).inflate(it, null))
        }
        prepareInitView()
        initView()
    }

    open fun prepareInitView() {
        // do nothing
    }

    abstract fun initView()

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AlibcTradeSDK.destory()
    }
}