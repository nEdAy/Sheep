package cn.neday.sheep.activity

import cn.neday.base.activity.BaseVMActivity
import cn.neday.base.router.Router
import cn.neday.sheep.KZ_TTY
import cn.neday.sheep.R
import cn.neday.sheep.viewmodel.ShakeViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_shake.*

class ShakeActivity : BaseVMActivity<ShakeViewModel>(R.layout.activity_shake) {

    override val isCheckLogin = true

    override fun initView() {
        titleBar_shake.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            } else if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                Router.alibabaService.showItemURLPage(this, KZ_TTY)
            }
        }
    }
}
