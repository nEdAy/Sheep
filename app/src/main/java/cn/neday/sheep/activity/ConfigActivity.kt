package cn.neday.sheep.activity

import android.view.View
import cn.neday.base.activity.BaseVMActivity
import cn.neday.base.config.BuglyConfig
import cn.neday.base.config.MMKVConfig
import cn.neday.sheep.R
import cn.neday.sheep.viewmodel.ConfigViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.StringUtils
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_config.*

/**
 * 账户设置页
 *
 * @author nEdAy
 */
class ConfigActivity : BaseVMActivity<ConfigViewModel>(R.layout.activity_config) {

    override val isCheckLogin = true

    override fun initView() {
        titleBar_config.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            }
        }

        rl_update.setOnClickListener { BuglyConfig.checkUpgrade() }
        rl_clear.setOnClickListener { clearCache() }
        tv_btn_logout.setOnClickListener { logout() }

        showAppVersion()
        showCacheSize()
    }

    /**
     * 刷新显示缓存大小
     */
    private fun showCacheSize() {
        cacheSize.text = "FileUtil.getFormatSize(FileUtil.getFolderSize(file))"
    }

    /**
     * 清除缓存
     */
    private fun clearCache() {
        // 清除图片缓存
        // 清除图片上传中各步骤的垃圾
        // FileUtil.deleteFolderFile()
        // 重新刷新显示缓存大小
        // showCacheSize()
    }

    /**
     * 显示版本号
     */
    private fun showAppVersion() {
        curVersion.visibility = View.VISIBLE
        curVersion.text = StringUtils.getString(R.string.app_version, AppUtils.getAppVersionName())
    }

    /**
     * 退出登录
     */
    private fun logout() {
        val dialog = NormalDialog(this)
        dialog.content(getString(R.string.tx_inquiry_logout))
            .title(getString(R.string.logout_prompt))
            .style(NormalDialog.STYLE_TWO)
            .titleTextSize(23f)
            .show()
        dialog.setOnBtnClickL(
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL {
                dialog.dismiss()
                MMKVConfig.logout()
                ActivityUtils.startActivity(LoginActivity::class.java)
                ActivityUtils.finishActivity(this)
            })
    }
}
