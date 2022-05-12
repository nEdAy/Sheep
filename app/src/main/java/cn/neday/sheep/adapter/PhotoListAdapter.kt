package cn.neday.sheep.adapter

import android.net.Uri
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import cn.neday.sheep.R
import cn.neday.sheep.model.Photo
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Adapter for the [RecyclerView]
 *
 * @author nEdAy
 */
class PhotoListAdapter : BaseQuickAdapter<Photo, BaseViewHolder>(R.layout.list_item_photo, null) {

    override fun convert(helper: BaseViewHolder, photo: Photo) {
        helper.setText(R.id.tv_title, photo.title)
        // http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
        (helper.getView(R.id.iv_img_shower) as? ImageView)?.load(
            Uri.parse("http://farm" + photo.farm + ".static.flickr.com/" + photo.server + "/" + photo.id + "_" + photo.secret + ".jpg")
        ) {
            crossfade(true)
            placeholder(R.drawable.icon_stub)
            error(R.drawable.icon_error)
            transformations(RoundedCornersTransformation(10f))
        }
    }
}