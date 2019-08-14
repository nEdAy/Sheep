package cn.neday.sheep.fragment

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.activity.GoodsDetailsActivity
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.enum.RankType
import cn.neday.sheep.model.Goods
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.viewmodel.RankingListViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.include_anything_list.*

/**
 * 各大榜单
 * 1.实时销量榜
 * 2.全天销量榜
 * 3.热推榜
 */
class RankingListFragment(private val rankType: RankType) :
    BaseVMFragment<RankingListViewModel>(R.layout.fragment_goods_list) {

    override fun initView() {
        initAdapter()
        initSwipeToRefresh()
        loadInitial()
    }

    private fun initAdapter() {
        val adapter = GoodsListAdapter()
        adapter.bindToRecyclerView(rv_goods)
        addEmptyView(adapter)
        addClickListener(adapter)
        mViewModel.rankGoods.observe(this, Observer {
            adapter.setNewData(it)
            srl_goods.isRefreshing = false
        })
        mViewModel.errMsg.observe(this, Observer {
            ToastUtils.showShort(it)
            srl_goods.isRefreshing = false
            adapter.loadMoreFail()
        })
    }

    private fun addEmptyView(adapter: GoodsListAdapter) {
        adapter.setEmptyView(R.layout.include_no_data, rv_goods.parent.parent as ViewGroup)
        adapter.emptyView.setOnClickListener { loadInitial() }
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
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_get_btn, R.drawable.bg_get_btn_pressed)
                    }
                    R.id.tx_buy_url -> {
                        AliTradeHelper(activity).showDetailPage(goods.goodsId)
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_buy_btn, R.drawable.bg_buy_btn_pressed)
                    }
                }
            }
    }

    private fun initSwipeToRefresh() {
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { loadInitial() }
    }

    private fun loadInitial() {
        mViewModel.getRankingList(rankType.index)
    }
}