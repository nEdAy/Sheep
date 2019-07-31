package cn.neday.sheep.activity

import cn.neday.sheep.R
import cn.neday.sheep.viewmodel.SearchViewModel

class ShakeActivity : BaseVMActivity<SearchViewModel>() {

    override val layoutId = R.layout.activity_search

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java

    override fun initView() {
        mViewModel.getHotWords()
    }
}
