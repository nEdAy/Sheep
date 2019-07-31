package cn.neday.sheep.adapter

import cn.neday.sheep.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class KeyWordsListAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.list_item_key_words, null) {

    override fun convert(helper: BaseViewHolder, keyWords: String) {
        helper.setText(R.id.rtv_keyWords, keyWords)
    }
}
