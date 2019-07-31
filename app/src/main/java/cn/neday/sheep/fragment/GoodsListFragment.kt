package cn.neday.sheep.fragment

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.activity.GoodsDetailsActivity
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.enum.NineType
import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils.changePressedViewBg
import cn.neday.sheep.viewmodel.NineGoodsListViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.include_anything_list.*

/**
 * 超值精选
 */
class GoodsListFragment(private val nineType: NineType) : BaseVMFragment<NineGoodsListViewModel>() {

    override val layoutId: Int = R.layout.fragment_goods_list

    override fun providerVMClass(): Class<NineGoodsListViewModel>? = NineGoodsListViewModel::class.java

    override fun initView() {
        initAdapter()
        initSwipeToRefresh()
        loadInitial(nineType)
    }

    private fun initAdapter() {
        val adapter = GoodsListAdapter()
        adapter.bindToRecyclerView(rv_goods)
        addEmptyView(adapter)
        addClickListener(adapter)
        adapter.setPreLoadNumber(PREFETCH_DISTANCE)
        adapter.setOnLoadMoreListener({
            mViewModel.getNineOpGoodsList(nineType.index.toString(), mViewModel.mCurrentPageId)
        }, rv_goods)
        mViewModel.pageGoods.observe(this, Observer<Pages<CommonGoods>> {
            if (adapter.itemCount >= it.totalNum) {
                adapter.loadMoreEnd()
            } else {
                setAdapterData(adapter, it)
                mViewModel.mCurrentPageId = it.pageId
                adapter.loadMoreComplete()
            }
        })
        mViewModel.errMsg.observe(this, Observer {
            srl_goods.isRefreshing = false
            adapter.loadMoreFail()
        })
    }

    private fun addEmptyView(adapter: GoodsListAdapter) {
        adapter.setEmptyView(R.layout.include_no_data, rv_goods.parent.parent as ViewGroup)
        adapter.emptyView.setOnClickListener { loadInitial(nineType) }
    }

    private fun addClickListener(listAdapter: GoodsListAdapter) {
        listAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val goods = adapter.getItem(position) as Goods
            val bundle = Bundle()
            bundle.putSerializable(GoodsDetailsActivity.extra, goods)
            ActivityUtils.startActivity(bundle, GoodsDetailsActivity::class.java)
        }
        listAdapter.onItemLongClickListener = BaseQuickAdapter.OnItemLongClickListener { adapter, _, position ->
            val goods = adapter.getItem(position) as Goods
            AliTradeHelper(activity).showAddCartPage(goods.goodsId)
            true
        }
        listAdapter.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val goods = adapter.getItem(position) as Goods
                when (view.id) {
                    R.id.ll_get -> {
                        AliTradeHelper(activity).showItemURLPage(goods.couponLink)
                        changePressedViewBg(view, R.drawable.bg_get_btn, R.drawable.bg_get_btn_pressed)
                    }
                    R.id.tx_buy_url -> {
                        AliTradeHelper(activity).showDetailPage(goods.goodsId)
                        changePressedViewBg(view, R.drawable.bg_buy_btn, R.drawable.bg_buy_btn_pressed)
                    }
                }
            }
    }

    private fun setAdapterData(adapter: GoodsListAdapter, data: Pages<CommonGoods>) {
        if (mViewModel.mCurrentPageId == NineGoodsListViewModel.LOAD_INITIAL_PAGE_ID) {
            srl_goods.isRefreshing = false
            adapter.setNewData(data.list)
            adapter.disableLoadMoreIfNotFullPage()
        } else {
            adapter.addData(data.list)
        }
    }

    private fun initSwipeToRefresh() {
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { loadInitial(nineType) }
    }

    private fun loadInitial(nineType: NineType) {
        mViewModel.getNineOpGoodsList(nineType.index.toString())
    }

    companion object {

        private const val PREFETCH_DISTANCE = 20
    }
}