package cn.neday.sheep.activity

import cn.neday.base.activity.BaseVMActivity
import cn.neday.base.router.Router
import cn.neday.sheep.KZ_TTY
import cn.neday.sheep.R
import cn.neday.sheep.viewmodel.SignInViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_sign_in.*

/**
 * 签到页
 *
 * @author nEdAy
 */
class SignInActivity : BaseVMActivity<SignInViewModel>(R.layout.activity_sign_in) {

    override val isCheckLogin = true

    override fun initView() {
        titleBar_sign_in.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            } else if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                Router.alibabaService.showItemURLPage(this, KZ_TTY)
            }
        }

    }

//    fun onresumeafter() {
//        val currentuserid = currentuser.getobjectid()
//        tosubscribe(
//            rxfactory.getuserserviceinstance(null)
//                .getuser(currentuserid, "_user[sign_in]").map(???({ user.getsign_in() })),
//        { catloadingview!!.show(getsupportfragmentmanager(), tag) },
//        { isnosign_in ->
//            catloadingview!!.dismissallowingstateloss()
//            if (isnosign_in) {
//                tv_sign_in_1!!.setenabled(true)
//                tv_sign_in_2!!.setenabled(true)
//                tv_sign_in_3!!.setenabled(true)
//            } else {
//                commonutils.showtoast("当日已签到")
//            }
//        },
//        { throwable ->
//            catloadingview!!.dismissallowingstateloss()
//            commonutils.showtoast("签到状态异常")
//            logger.e(throwable.getmessage())
//        })
//        tv_sign_in_1!!.setonclicklistener({ v -> signin(currentuserid, 0) })
//        tv_sign_in_2!!.setonclicklistener({ v -> signin(currentuserid, 1) })
//        tv_sign_in_3!!.setonclicklistener({ v -> signin(currentuserid, 2) })
//    }

//    /**
//     * 开始签到
//     *
//     * @param currentuserid 当前用户id
//     * @param type          签到类型
//     */
//    private fun signin(currentuserid: string, type: Int) {
//        tosubscribe(rxfactory.getuserserviceinstance(null)
//            .signin(currentuserid, type),
//            { catloadingview!!.show(getsupportfragmentmanager(), tag) },
//            { creditshistory ->
//                catloadingview!!.dismissallowingstateloss()
//                tv_sign_in_1!!.setenabled(false)
//                tv_sign_in_2!!.setenabled(false)
//                tv_sign_in_3!!.setenabled(false)
//                //todo:xx
//                val nextget = 1
//                //                    switch (creditshistory.getuserid()) {
//                //                        case 0:
//                //                            nextget = "3";
//                //                            break;
//                //                        case 1:
//                //                            nextget = "3";
//                //                            break;
//                //                        case 2:
//                //                            nextget = "3";
//                //                            break;
//                //                        case 3:
//                //                            nextget = "5";
//                //                            break;
//                //                        case 4:
//                //                            nextget = "6";
//                //                            break;
//                //                        case 5:
//                //                            nextget = "6";
//                //                            break;
//                //                        case 6:
//                //                            nextget = "8";
//                //                            break;
//                //                        default:
//                //                            nextget = "签到类型异常";
//                //                            break;
//                //                    }
//                val dialog = normaldialog(mcontext)
//                dialog.content(
//                    "今日签到获得" + creditshistory.getchange() +
//                            "枚口袋币,您当前总口袋币数为" + creditshistory.getcredit() +
//                            "，您已累计签到" + creditshistory.gettype() +
//                            "天,明日连续签到将获得" + nextget + "枚口袋币"
//                )
//                    .btnnum(1)
//                    .btntext("朕已阅")
//                    .show()
//                dialog.setonbtnclickl(onbtnclickl { dialog.dismiss() } as onbtnclickl)
//            },
//            { throwable ->
//                catloadingview!!.dismissallowingstateloss()
//                commonutils.showtoast("签到异常")
//                logger.e(throwable.getmessage())
//            })
//    }
}
