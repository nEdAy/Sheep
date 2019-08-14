package cn.neday.sheep.view

import android.net.Uri
import android.widget.ImageView
import cn.neday.sheep.R
import com.blankj.utilcode.util.NetworkUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(url: String?) {
    Glide.with(context)
        .load(
            if (NetworkUtils.is4G()) {
                Uri.parse(url + "_200x200.jpg")
            } else {
                Uri.parse(url + "_300x300.jpg")
            }
        )
        .thumbnail(
            Glide.with(context)
                .load(Uri.parse(url + "_100x100.jpg"))
        )
        .apply(
            RequestOptions().transform(RoundedCorners(10))
                .placeholder(R.drawable.icon_stub)
                .error(R.drawable.icon_error)
        ).into(this)
}