package cn.neday.sheep.activity

import cn.neday.base.activity.BaseActivity
import cn.neday.sheep.R
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_vip.*

/**
 * 会员中心页
 *
 * @author nEdAy
 */
class VipActivity : BaseActivity(R.layout.activity_vip) {

    override fun initView() {
        titleBar_vip.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            } else if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                // ActivityUtils.startActivity(VipHelpActivity::class.java)
            }
        }

        tv_sign_in.setOnClickListener { ActivityUtils.startActivity(SignInActivity::class.java) }
        rl_privilege.setOnClickListener { ToastUtils.showShort(R.string.tx_todo_fun) }
        rl_achievement.setOnClickListener { ToastUtils.showShort(R.string.tx_todo_fun) }
    }
}
