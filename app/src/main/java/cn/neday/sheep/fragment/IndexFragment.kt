package cn.neday.sheep.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.activity.*
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.model.Goods
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.viewmodel.IndexViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_main_index.*
import kotlinx.android.synthetic.main.include_anything_list.*
import kotlinx.android.synthetic.main.include_main_index_header.view.*
import kotlinx.android.synthetic.main.include_main_index_icon.view.*


/**
 * 首页
 */
class IndexFragment : BaseVMFragment<IndexViewModel>(R.layout.fragment_main_index) {

    private lateinit var headerView: View

    override fun initView() {
        //TODO : 待测试 首页初始显示
        initSearchHeader()
        initAdapter()
        initSwipeToRefresh()
        loadInitial()
    }

    private fun initSearchHeader() {
        titleBar_index.centerSearchEditText.hint = getString(R.string.tx_search_hint)
        titleBar_index.centerSearchEditText.setOnClickListener { ActivityUtils.startActivity(SearchActivity::class.java) }
        titleBar_index.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_SEARCH || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                ActivityUtils.startActivity(SearchActivity::class.java)
            }
        }
    }

    private fun initAdapter() {
        val adapter = GoodsListAdapter()
        adapter.bindToRecyclerView(rv_goods)
        addHeaderView(adapter)
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

    private fun addHeaderView(adapter: GoodsListAdapter) {
        headerView = layoutInflater.inflate(R.layout.include_main_index_header, rv_goods.parent as ViewGroup, false)
        initHeaderBanner(headerView)
        initHeaderIcon(headerView)
        adapter.addHeaderView(headerView)
    }

    private fun initHeaderBanner(headerView: View) {
        mViewModel.banners.observe(this, Observer {
            headerView.banner.setSource(it).startScroll()
            headerView.banner.setOnItemClickL { position ->
                AliTradeHelper(activity).showItemURLPage(it[position].url)
            }
        })
    }

    private fun initHeaderIcon(headerView: View) {
        headerView.ll_sign.setOnClickListener { ActivityUtils.startActivity(SignInActivity::class.java) }
        headerView.ll_shake.setOnClickListener { ActivityUtils.startActivity(ShakeActivity::class.java) }
        headerView.ll_shop.setOnClickListener { ActivityUtils.startActivity(LoginActivity::class.java) }
        headerView.ll_join.setOnClickListener { CommonUtils.joinQQGroup(activity) }
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
        mViewModel.getBannerList()
        mViewModel.getRankingList(3)
    }

    override fun onResume() {
        super.onResume()
        headerView.banner.goOnScroll()
    }

    override fun onPause() {
        super.onPause()
        headerView.banner.pauseScroll()
    }
}
