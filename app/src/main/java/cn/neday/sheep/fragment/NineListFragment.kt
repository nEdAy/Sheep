package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import cn.neday.sheep.LOAD_INITIAL_PAGE_ID
import cn.neday.sheep.PREFETCH_DISTANCE
import cn.neday.sheep.R
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.enum.NineType
import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.viewmodel.NineListViewModel
import kotlinx.android.synthetic.main.include_anything_list.*

/**
 * 超值精选
 */
class NineListFragment(private val nineType: NineType) :
    GoodsListFragment<NineListViewModel>(R.layout.fragment_goods_list) {

    override fun loadInitial() {
        mViewModel.getNineOpGoodsList(nineType.index.toString())
    }

    override fun initAdapter() {
        super.initAdapter()
        adapter.setPreLoadNumber(PREFETCH_DISTANCE)
        adapter.setOnLoadMoreListener({
            mViewModel.getNineOpGoodsList(nineType.index.toString(), mViewModel.mCurrentPageId)
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