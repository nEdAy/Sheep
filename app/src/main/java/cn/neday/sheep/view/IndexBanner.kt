package cn.neday.sheep.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.net.toUri
import cn.neday.sheep.R
import cn.neday.sheep.model.Banner
import com.bumptech.glide.Glide
import com.flyco.banner.widget.Banner.BaseIndicatorBanner


/**
 * 广告图片轮播
 *
 * @author nEdAy
 */
class IndexBanner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    BaseIndicatorBanner<Banner, IndexBanner>(context, attrs, defStyle) {

    override fun onCreateItemView(position: Int): View {
        val inflate = View.inflate(mContext, R.layout.adapter_banner, null)
        if (position < mDatas.size) {
            val data = mDatas[position]
            val itemWidth = mDisplayMetrics.widthPixels
            val itemHeight = (itemWidth * 0.417f).toInt()
            val ivBanner = inflate.findViewById<ImageView>(R.id.iv_banner)
            ivBanner.layoutParams = LinearLayout.LayoutParams(itemWidth, itemHeight)
            Glide.with(this)
                .load(data.picture?.toUri())
                .override(itemWidth, itemHeight)
                .centerCrop()
                .into(ivBanner)
        }
        return inflate
    }
}
