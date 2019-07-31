package cn.neday.sheep.activity

import android.os.Handler
import android.view.KeyEvent
import cn.neday.sheep.R
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ToastUtils
import com.orhanobut.hawk.Hawk


/**
 * 启动页
 *
 * @author nEdAy
 */
class SplashActivity : BaseActivity() {

    // 为保证启动速度，SplashActivity不调用setContentView()方法
    override val layoutId: Int? = null

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
        Handler().postDelayed({
            doStartActivity(checkIsFirstStartApp())
        }, SHOW_TIME_MIN)
    }

    /**
     * 检测是否是第一次启动并指定跳转页
     *
     * @return 页面序数
     */
    private fun checkIsFirstStartApp(): JumpPage {
        val userFirst = Hawk.get("isFirstStartApp", true)
        return if (userFirst) {
            JumpPage.GO_GUIDE
        } else {
            JumpPage.GO_MAIN
        }
    }

    /**
     * 跳转指定Activity
     *
     * @param jumpPage 页面序数
     */
    private fun doStartActivity(jumpPage: JumpPage) {
        when (jumpPage) {
            JumpPage.GO_GUIDE -> ActivityUtils.startActivity(GuideActivity::class.java)
            JumpPage.GO_MAIN -> ActivityUtils.startActivity(MainActivity::class.java)
        }
        ActivityUtils.finishActivity(this)
    }

    /**
     * 屏蔽物理返回按钮
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event)
    }

    companion object {

        private const val SHOW_TIME_MIN = 233L
    }

    enum class JumpPage {
        // 导航页
        GO_GUIDE,
        // 主页面
        GO_MAIN
    }
}
