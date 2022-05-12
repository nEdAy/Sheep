package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.viewmodel.SearchFlickrListViewModel

class SearchFlickrListFragment :
    PhotoListFragment<SearchFlickrListViewModel>(R.layout.fragment_goods_list) {

    override fun loadInitial(keyword: String) {
        mViewModel.getFlickrImageByKeyword(keyword)
    }

    override fun initAdapter() {
        super.initAdapter()
        mViewModel.photoList.observe(this, Observer {
            adapter.setNewData(it)
        })
    }
}