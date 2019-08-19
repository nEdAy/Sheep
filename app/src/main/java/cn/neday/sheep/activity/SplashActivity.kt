package cn.neday.sheep.activity

import android.view.KeyEvent
import cn.neday.base.activity.BaseActivity
import cn.neday.base.config.MMKVConfig.IS_FIRST_START_APP
import cn.neday.sheep.R
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.*

/**
 * 启动页
 *
 * @author nEdAy
 */
class SplashActivity : BaseActivity(null) {

    private var job: Job? = null

    override fun initView() {
        checkIntentAndIsTaskRoot()
        checkIsAppRoot()
        delayJumpPage()
    }

    private fun checkIsAppRoot() {
        if (DeviceUtils.isDeviceRooted() && AppUtils.isAppRoot()) {
            ToastUtils.showLong(getString(R.string.tx_root))
        }
    }

    private fun checkIntentAndIsTaskRoot() {
        if (!(intent != null && intent.extras != null) && !isTaskRoot) {
            ActivityUtils.finishActivity(this)
        }
    }

    private fun delayJumpPage() {
        job = GlobalScope.launch(Dispatchers.Main) {
            delay(SHOW_TIME_MIN)
            startNextActivity()
        }
    }

    /**
     * 检测是否是第一次启动并指定跳转页
     */
    private fun startNextActivity() {
        val userFirst = kv.decodeBool(IS_FIRST_START_APP, true)
        if (userFirst) {
            ActivityUtils.startActivity(GuideActivity::class.java)
        } else {
            ActivityUtils.startActivity(MainActivity::class.java)
        }
        ActivityUtils.finishActivity(this)
    }

    /**
     * 屏蔽物理返回按钮
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    companion object {

        private const val SHOW_TIME_MIN = 233L
    }
}
