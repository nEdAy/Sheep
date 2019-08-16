package cn.neday.sheep.view

import android.widget.ImageView
import androidx.core.net.toUri
import cn.neday.sheep.R
import com.blankj.utilcode.util.NetworkUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(url: String?) {
    Glide.with(context)
        .load(
            if (NetworkUtils.is4G()) {
                (url + "_200x200.jpg").toUri()
            } else {
                (url + "_300x300.jpg").toUri()
            }
        )
        .thumbnail(
            Glide.with(context)
                .load((url + "_100x100.jpg").toUri())
        )
        .apply(
            RequestOptions().transform(RoundedCorners(10))
                .placeholder(R.drawable.icon_stub)
                .error(R.drawable.icon_error)
        ).into(this)
}