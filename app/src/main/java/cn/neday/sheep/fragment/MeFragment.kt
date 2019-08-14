package cn.neday.sheep.fragment

import android.net.Uri
import android.text.TextUtils
import android.view.View
import cn.neday.sheep.R
import cn.neday.sheep.activity.AboutActivity
import cn.neday.sheep.activity.LoginActivity
import cn.neday.sheep.config.HawkConfig.TOKEN
import cn.neday.sheep.enum.OrderType
import cn.neday.sheep.model.User
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.AppStoreUtils
import cn.neday.sheep.util.ClipboardUtils
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.view.ShareDialog
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_main_me.*

/**
 * 我的
 */
class MeFragment : BaseFragment(R.layout.fragment_main_me) {

    override fun initView() {
        dampView.setImageView(iv_damp)
        dampView.setOnRefreshListener { getUserInfo() }
//        ll_option
//            .setOnClickListener { ActivityUtils.startActivity(AccountActivity::class.java) }
//        iv_level
//            .setOnClickListener { ActivityUtils.startActivity(VipActivity::class.java) }
//        rl_me
//            .setOnClickListener { ActivityUtils.startActivity(AccountActivity::class.java) }
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
            //            val intent = Intent()
//            intent.putExtra("userId", mCurrentUser.id)
//            ActivityUtils.startActivity(CreditsHistoryActivity::class.java, intent)
        }

        tv_login.setOnClickListener { ActivityUtils.startActivity(LoginActivity::class.java) }

        rl_showMyCartsPage.setOnClickListener { AliTradeHelper(activity).showMyCartsPage() }
        rl_showMyOrdersPage_1.setOnClickListener {
            AliTradeHelper(activity).showMyOrdersPage(
                OrderType.DAT_FU_KUAN.index,
                true
            )
        }
        rl_showMyOrdersPage_2.setOnClickListener {
            AliTradeHelper(activity).showMyOrdersPage(
                OrderType.DAT_FA_HUO.index,
                true
            )
        }
        rl_showMyOrdersPage_3.setOnClickListener {
            AliTradeHelper(activity).showMyOrdersPage(
                OrderType.DAT_SHOU_HUO.index,
                true
            )
        }
        rl_showMyOrdersPage_4.setOnClickListener {
            AliTradeHelper(activity).showMyOrdersPage(
                OrderType.DAY_PING_JIA.index,
                true
            )
        }
    }


    override fun onResume() {
        super.onResume()
        initUserInfoAndChangeSkin()
    }


    /**
     * 更新用户信息、点击状态、换皮肤
     */
    private fun initUserInfoAndChangeSkin() {
        val isTokenEmpty = TextUtils.isEmpty(Hawk.get(TOKEN))
        if (!isTokenEmpty) {
//            getUserInfo(mCurrentUser.id)
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
        val selfCenterBgIndex = Hawk.get("self_center_bg_index", 0)
        iv_damp.setImageResource(selfCenterBgResIDs[selfCenterBgIndex])
    }

    /**
     * 获取当前用户信息
     */
    private fun getUserInfo() {
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//            .getUser(id),
//            { user ->
//                mCreditsValue.withNumber(user.getCredit())
//                mCreditsValue.start()
//                refreshUser(user)
//            },
//            { throwable -> LogUtils.e(throwable.getMessage()) })
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
//            mNickname.text = getString(R.string.default_nickname)
//            mNickname.setOnClickListener { ActivityUtils.startActivity(UpdateInfoActivity::class.java) }
        } else {
//            mNickname.text = nickname
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
        if (avatarUrl != null && avatarUrl != "") {
            val avatarUri = Uri.parse(avatarUrl)
            Glide.with(this)
                .load(avatarUri)
                .into(iv_user_avatar)
        } else {
            iv_user_avatar.setImageResource(R.drawable.avatar_default)
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
                    val intent = activity?.packageManager?.getLaunchIntentForPackage("com.tencent.mm")
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
