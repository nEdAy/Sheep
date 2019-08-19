package cn.neday.sheep.activity

import cn.neday.base.activity.BaseActivity
import cn.neday.sheep.R
import com.blankj.utilcode.util.ActivityUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_about.*

/**
 * 关于与合作页
 *
 * @author nEdAy
 */
class AboutActivity : BaseActivity(R.layout.activity_about) {

    override fun initView() {
        titleBar_about.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            }
        }
    }
}
