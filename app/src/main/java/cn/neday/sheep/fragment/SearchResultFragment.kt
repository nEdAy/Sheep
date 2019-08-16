package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import cn.neday.sheep.LOAD_INITIAL_PAGE_ID
import cn.neday.sheep.PREFETCH_DISTANCE
import cn.neday.sheep.R
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.viewmodel.SearchResultViewModel
import kotlinx.android.synthetic.main.include_anything_list.*

/**
 * 搜索结果列表页
 *
 * @author nEdAy
 */
class SearchResultFragment(private val keyWord: String) :
    GoodsListFragment<SearchResultViewModel>(R.layout.fragment_goods_list) {

    override fun loadInitial() {
        mViewModel.getDtkSearchGoods(keyWord)
    }

    override fun initAdapter() {
        super.initAdapter()
        adapter.setPreLoadNumber(PREFETCH_DISTANCE)
        adapter.setOnLoadMoreListener({
            mViewModel.getDtkSearchGoods(keyWord, mViewModel.mCurrentPageId)
        }, rv_goods)
        mViewModel.pageGoods.observe(this, Observer<Pages<CommonGoods>> {
            if (adapter.itemCount >= it.totalNum ?: 0) {
                adapter.loadMoreEnd()
            } else {
                setAdapterData(adapter, it)
                mViewModel.mCurrentPageId = it.pageId ?: LOAD_INITIAL_PAGE_ID
                adapter.loadMoreComplete()
            }
        })
    }

    private fun setAdapterData(adapter: GoodsListAdapter, data: Pages<CommonGoods>) {
        if (mViewModel.mCurrentPageId == LOAD_INITIAL_PAGE_ID) {
            srl_goods.isRefreshing = false
            adapter.setNewData(data.list)
            adapter.disableLoadMoreIfNotFullPage()
        } else {
            data.list?.let { adapter.addData(it) }
        }
    }
}