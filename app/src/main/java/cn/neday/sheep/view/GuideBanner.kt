package cn.neday.sheep.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.neday.sheep.R
import com.flyco.banner.widget.Banner.BaseIndicatorBanner

/**
 * 导航滚动翻页
 *
 * @author nEdAy
 */
class GuideBanner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    BaseIndicatorBanner<Int, GuideBanner>(context, attrs, defStyle) {

    private var mOnGuideJumpClick: (() -> Unit)? = null

    override fun onCreateItemView(position: Int): View {
        setBarShowWhenLast(false)
        val inflate = View.inflate(mContext, R.layout.adapter_guide, null)

        val ivGuideImg = inflate.findViewById<ImageView>(R.id.iv_guide_img)
        ivGuideImg.setImageResource(mDatas[position])

        val tvGuideJumpBtn = inflate.findViewById<TextView>(R.id.tv_guide_jump_btn)
        val lastPageIndex = mDatas.size - 1
        tvGuideJumpBtn.visibility = if (position == lastPageIndex) View.VISIBLE else View.GONE
        tvGuideJumpBtn.setOnClickListener {
            mOnGuideJumpClick?.invoke()
        }
        return inflate
    }

    fun setOnGuideJumpClick(onGuideJumpClick: () -> Unit) {
        mOnGuideJumpClick = onGuideJumpClick
    }
}
