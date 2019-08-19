package cn.neday.sheep.fragment

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.neday.base.fragment.BaseVMFragment
import cn.neday.base.router.Router
import cn.neday.base.viewmodel.BaseViewModel
import cn.neday.sheep.R
import cn.neday.sheep.activity.GoodsDetailsActivity
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.model.Goods
import cn.neday.sheep.util.CommonUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.include_anything_list.*

abstract class GoodsListFragment<VM : BaseViewModel>(layoutId: Int) : BaseVMFragment<VM>(layoutId) {

    val adapter = GoodsListAdapter()

    override fun initView() {
        initAdapter()
        initSwipeToRefresh()
        loadInitial()
    }

    open fun initAdapter() {
        adapter.bindToRecyclerView(rv_goods)
        addEmptyView(adapter)
        addClickListener(adapter)
        mViewModel.errMsg.observe(this, Observer {
            ToastUtils.showShort(it)
            adapter.loadMoreFail()
        })
        mViewModel.onComplete.observe(this, Observer {
            srl_goods.isRefreshing = false
        })
    }

    private fun addEmptyView(adapter: GoodsListAdapter) {
        adapter.setEmptyView(R.layout.include_no_data, rv_goods.parent as ViewGroup)
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
            Router.alibabaService.showAddCartPage(activity, goods.goodsId)
            true
        }
        listAdapter.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val goods = adapter.getItem(position) as Goods
                when (view.id) {
                    R.id.ll_get -> {
                        Router.alibabaService.showItemURLPage(activity, goods.couponLink)
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_get_btn, R.drawable.bg_get_btn_pressed)
                    }
                    R.id.tx_buy_url -> {
                        Router.alibabaService.showDetailPage(activity, goods.goodsId)
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_buy_btn, R.drawable.bg_buy_btn_pressed)
                    }
                }
            }
    }

    private fun initSwipeToRefresh() {
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { loadInitial() }
    }

    abstract fun loadInitial()
}