package cn.neday.sheep.view


import android.app.Dialog
import android.os.Bundle
import android.view.View
import cn.neday.sheep.R
import cn.neday.sheep.util.ClipboardUtils
import cn.sharesdk.onekeyshare.OnekeyShare
import cn.sharesdk.sina.weibo.SinaWeibo
import cn.sharesdk.system.text.ShortMessage
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.favorite.WechatFavorite
import cn.sharesdk.wechat.friends.Wechat
import cn.sharesdk.wechat.moments.WechatMoments
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * 六格分享页
 *
 * @author nEdAy
 */
class ShareDialog : BottomSheetDialogFragment() {

    private var mBehavior: BottomSheetBehavior<View>? = null

    private var mTitle: String? = null
    private var mText: String? = null
    private var mImageUrl: String? = null
    private var mClickUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTitle = arguments?.getString("title")
        mText = arguments?.getString("text")
        mImageUrl = arguments?.getString("imageUrl")
        mClickUrl = arguments?.getString("clickUrl")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.include_pop_six_icon, null)
        view.findViewById<View>(R.id.ll_share_wechat).setOnClickListener {
            showShare(Wechat.NAME)
            mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
        view.findViewById<View>(R.id.ll_share_wechatMoments).setOnClickListener {
            showShare(WechatMoments.NAME)
            mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
        view.findViewById<View>(R.id.ll_share_qq).setOnClickListener {
            showShare(QQ.NAME)
            mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
        view.findViewById<View>(R.id.ll_share_shortMessage).setOnClickListener {
            showShare(ShortMessage.NAME)
            mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
        view.findViewById<View>(R.id.ll_share_sinaWeibo).setOnClickListener {
            showShare(SinaWeibo.NAME)
            mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
        view.findViewById<View>(R.id.ll_share_wechatFavorite).setOnClickListener {
            showShare(WechatFavorite.NAME)
            mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
        view.findViewById<View>(R.id.tv_share_cancel).setOnClickListener { dismiss() }
        view.findViewById<View>(R.id.tv_share_copy).setOnClickListener {
            ClipboardUtils.copyText(mClickUrl)
            ToastUtils.showShort("已复制内容到剪切板")
        }
        dialog.setContentView(view)
        mBehavior = BottomSheetBehavior.from(view.parent as View)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        // 默认全屏展开
        mBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun showShare(platform: String?) {
        val oks = OnekeyShare()
        // 指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform)
        }
        // 关闭sso授权
        oks.disableSSOWhenAuthorize()
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mTitle)
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(mClickUrl)
        // text是分享文本，所有平台都需要这个字段
        oks.text = mText
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(mImageUrl)
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mClickUrl)
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("很好很强大~")
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("http://www.neday.cn")
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.neday.cn")
        // 启动分享
        oks.show(activity)
    }

    companion object {

        fun newInstance(title: String, text: String, imageUrl: String, clickUrl: String): ShareDialog {
            val shareDialog = ShareDialog()
            val args = Bundle()
            args.putString("title", title)
            args.putString("text", text)
            args.putString("imageUrl", imageUrl)
            args.putString("clickUrl", clickUrl)
            shareDialog.arguments = args
            return shareDialog
        }
    }
}
