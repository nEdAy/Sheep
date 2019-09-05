package cn.neday.sheep.fragment

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import cn.neday.base.config.MMKVConfig.ID
import cn.neday.base.config.MMKVConfig.TOKEN
import cn.neday.base.config.MMKVConfig.kv
import cn.neday.base.fragment.BaseVMFragment
import cn.neday.base.router.Router
import cn.neday.base.util.AppStoreUtils
import cn.neday.base.util.ClipboardUtils
import cn.neday.sheep.R
import cn.neday.sheep.activity.*
import cn.neday.sheep.enum.OrderType
import cn.neday.sheep.model.User
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.view.ShareDialog
import cn.neday.sheep.viewmodel.MeViewModel
import coil.api.load
import coil.transform.CircleCropTransformation
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import kotlinx.android.synthetic.main.fragment_main_me.*

/**
 * 我的
 */
class MeFragment : BaseVMFragment<MeViewModel>(R.layout.fragment_main_me) {

    override fun initView() {
        dampView.setImageView(iv_damp)
        dampView.setOnRefreshListener { mViewModel.getUserById() }
        ll_config.setOnClickListener { ActivityUtils.startActivity(ConfigActivity::class.java) }
        iv_level.setOnClickListener { ActivityUtils.startActivity(VipActivity::class.java) }
        ll_encourage.setOnClickListener { encourageWe() }
        ll_about.setOnClickListener { ActivityUtils.startActivity(AboutActivity::class.java) }
        ll_feedback.setOnClickListener { CommonUtils.joinQQGroup(activity) }
        ll_attention.setOnClickListener { attentionWe() }
        ll_share.setOnClickListener {
            ShareDialog.newInstance(
                getString(R.string.app_name), "口袋快爆-每天千款优惠券秒杀，一折限时疯抢！",
                "http://app-10046956.cos.myqcloud.com/toAvatar.png",
                "http://a.app.qq.com/o/simple.jsp?pkgname=com.neday.bomb"
            ).show(childFragmentManager, "ShareDialog")
        }
        rl_credits.setOnClickListener {
            ActivityUtils.startActivity(CreditHistoryActivity::class.java)
        }
        tv_login.setOnClickListener { ActivityUtils.startActivity(LoginActivity::class.java) }
        rl_showMyCartsPage.setOnClickListener { Router.alibabaService.showMyCartsPage(activity) }
        rl_showMyOrdersPage_1.setOnClickListener {
            Router.alibabaService.showMyOrdersPage(
                activity,
                OrderType.DAT_FU_KUAN.index,
                true
            )
        }
        rl_showMyOrdersPage_2.setOnClickListener {
            Router.alibabaService.showMyOrdersPage(
                activity,
                OrderType.DAT_FA_HUO.index,
                true
            )
        }
        rl_showMyOrdersPage_3.setOnClickListener {
            Router.alibabaService.showMyOrdersPage(
                activity,
                OrderType.DAT_SHOU_HUO.index,
                true
            )
        }
        rl_showMyOrdersPage_4.setOnClickListener {
            Router.alibabaService.showMyOrdersPage(
                activity,
                OrderType.DAY_PING_JIA.index,
                true
            )
        }
        mViewModel.user.observe(this, Observer {
            refreshUser(it)
        })
    }


    override fun onResume() {
        super.onResume()
        initUserInfoAndChangeSkin()
    }


    /**
     * 更新用户信息、点击状态、换皮肤
     */
    private fun initUserInfoAndChangeSkin() {
        val isTokenEmpty = StringUtils.isTrimEmpty(kv.decodeString(TOKEN))
        val isIDEmpty = kv.decodeInt(ID) != 0
        if (!isTokenEmpty && !isIDEmpty) {
            mViewModel.getUserById()
            rl_top.visibility = View.VISIBLE
            rl_user_info.visibility = View.VISIBLE
            rl_credits.visibility = View.VISIBLE
            tv_login.visibility = View.GONE
        } else {
            rl_top.visibility = View.GONE
            rl_user_info.visibility = View.GONE
            rl_credits.visibility = View.GONE
            tv_login.visibility = View.VISIBLE
        }
        val selfCenterBgIndex = kv.decodeInt("self_center_bg_index", 0)
        iv_damp.setImageResource(selfCenterBgResIDs[selfCenterBgIndex])
    }

    /**
     * 更新用户信息前端显示
     *
     * @param user 用户信息
     */
    private fun refreshUser(user: User) {
        refreshAvatar(user.avatar)
        val nickname = user.nickname
        if (TextUtils.isEmpty(nickname) || nickname == getString(R.string.default_nickname)) {
            tv_user_nickname.text = getString(R.string.default_nickname)
            // tv_user_nickname.setOnClickListener { ActivityUtils.startActivity(UpdateInfoActivity::class.java) }
        } else {
            tv_user_nickname.text = nickname
        }
        val credit = user.credit
        if (credit != null) {
            when {
                credit >= 200000 -> iv_user_vip.setImageResource(R.drawable.level_10)
                credit >= 100000 -> iv_user_vip.setImageResource(R.drawable.level_9)
                credit >= 50000 -> iv_user_vip.setImageResource(R.drawable.level_8)
                credit >= 15000 -> iv_user_vip.setImageResource(R.drawable.level_7)
                credit >= 5000 -> iv_user_vip.setImageResource(R.drawable.level_6)
                credit >= 2000 -> iv_user_vip.setImageResource(R.drawable.level_5)
                credit >= 1000 -> iv_user_vip.setImageResource(R.drawable.level_4)
                credit >= 500 -> iv_user_vip.setImageResource(R.drawable.level_3)
                credit >= 200 -> iv_user_vip.setImageResource(R.drawable.level_2)
                credit >= 100 -> iv_user_vip.setImageResource(R.drawable.level_1)
                else -> iv_user_vip.setImageResource(R.drawable.level_0)
            }
        }
    }

    /**
     * 更新头像 refreshAvatar
     */
    private fun refreshAvatar(avatarUrl: String?) {
        iv_user_avatar.load(avatarUrl) {
            crossfade(true)
            placeholder(R.drawable.avatar_default)
            error(R.drawable.avatar_default)
            transformations(CircleCropTransformation())
        }
    }

    /**
     * 关注微信
     */
    private fun attentionWe() {
        val dialog = NormalDialog(activity)
        dialog.content("跳转微信—通讯录-添加朋友-查找公众号—搜索\"神马快爆订阅号\"(点击跳转微信可以直接粘贴公众号哦)")
            .style(NormalDialog.STYLE_TWO)
            .btnNum(3)
            .btnText(getString(R.string.tx_cancel), getString(R.string.tx_determine), "跳转微信")
            .show()
        dialog.setOnBtnClickL(
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL {
                // 复制数据到剪切板
                ClipboardUtils.copyText(getString(R.string.app_name))
                try {
                    val intent =
                        activity?.packageManager?.getLaunchIntentForPackage("com.tencent.mm")
                    if (intent != null) {
                        ActivityUtils.startActivity(intent)
                    }
                } catch (ignored: Exception) {
                    ToastUtils.showLong("您尚未安装微信APP")
                }
                dialog.superDismiss()
            })
    }

    /**
     * 鼓励我们----打开应用商店
     */
    private fun encourageWe() {
        val dialog = NormalDialog(activity)
        dialog.content("袋王亲，如果您觉得我们做的还不错，请给我一些鼓励吧！")
            .style(NormalDialog.STYLE_TWO)
            .btnNum(3)
            .btnText(getString(R.string.tx_cancel), getString(R.string.tx_determine), "跳转商店")
            .show()
        dialog.setOnBtnClickL(
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL {
                AppStoreUtils.getAppStoreIntent()
                dialog.superDismiss()
            }
        )
    }

    companion object {
        private val selfCenterBgResIDs = intArrayOf(
            R.drawable.selfcenter_bg_0,
            R.drawable.selfcenter_bg_1,
            R.drawable.selfcenter_bg_2,
            R.drawable.selfcenter_bg_3,
            R.drawable.selfcenter_bg_4
        )
    }
}
