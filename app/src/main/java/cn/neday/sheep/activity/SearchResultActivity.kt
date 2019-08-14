package cn.neday.sheep.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.model.CommonGoods
import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.viewmodel.SearchResultViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.include_anything_list.*

/**
 * 搜索结果列表页
 *
 * @author nEdAy
 */
class SearchResultActivity : BaseVMActivity<SearchResultViewModel>() {

    override val layoutId = R.layout.activity_search_result

    override fun initView() {
        val keyWord = intent.extras?.get(EXTRA) as String?
        keyWord?.let {
            initSearchBar(keyWord)
            initAdapter(keyWord)
            initSwipeToRefresh(keyWord)
            loadInitial(keyWord)
        } ?: ActivityUtils.finishActivity(this)
    }

    private fun initSearchBar(keyWord: String) {
        titleBar_search_result.centerSearchEditText.setText(keyWord)
        titleBar_search_result.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                mViewModel.getDtkSearchGoods(titleBar_search_result.searchKey)
            } else if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            }
        }
    }

    private fun initAdapter(keyWord: String) {
        val adapter = GoodsListAdapter()
        adapter.bindToRecyclerView(rv_goods)
        addEmptyView(adapter, keyWord)
        addClickListener(adapter)
        adapter.setPreLoadNumber(PREFETCH_DISTANCE)
        adapter.setOnLoadMoreListener({
            mViewModel.getDtkSearchGoods(keyWord, mViewModel.mCurrentPageId)
        }, rv_goods)
        mViewModel.pageGoods.observe(this, Observer<Pages<CommonGoods>> {
            if (adapter.itemCount >= it.totalNum ?: 0) {
                adapter.loadMoreEnd()
            } else {
                setAdapterData(adapter, it)
                mViewModel.mCurrentPageId = it.pageId ?: SearchResultViewModel.LOAD_INITIAL_PAGE_ID
                adapter.loadMoreComplete()
            }
        })
        mViewModel.errMsg.observe(this, Observer {
            srl_goods.isRefreshing = false
            adapter.loadMoreFail()
        })
    }

    private fun addEmptyView(adapter: GoodsListAdapter, keyWord: String) {
        adapter.setEmptyView(R.layout.include_no_data, rv_goods.parent.parent as ViewGroup)
        adapter.emptyView.setOnClickListener { loadInitial(keyWord) }
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
            AliTradeHelper(this).showAddCartPage(goods.goodsId)
            true
        }
        listAdapter.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val goods = adapter.getItem(position) as Goods
                when (view.id) {
                    R.id.ll_get -> {
                        AliTradeHelper(this).showItemURLPage(goods.couponLink)
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_get_btn, R.drawable.bg_get_btn_pressed)
                    }
                    R.id.tx_buy_url -> {
                        AliTradeHelper(this).showDetailPage(goods.goodsId)
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_buy_btn, R.drawable.bg_buy_btn_pressed)
                    }
                }
            }
    }

    private fun setAdapterData(adapter: GoodsListAdapter, data: Pages<CommonGoods>) {
        if (mViewModel.mCurrentPageId == SearchResultViewModel.LOAD_INITIAL_PAGE_ID) {
            srl_goods.isRefreshing = false
            adapter.setNewData(data.list)
            adapter.disableLoadMoreIfNotFullPage()
        } else {
            data.list?.let { adapter.addData(it) }
        }
    }

    private fun initSwipeToRefresh(keyWord: String) {
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { loadInitial(keyWord) }
    }

    private fun loadInitial(keyWord: String) {
        mViewModel.getDtkSearchGoods(keyWord)
    }

    companion object {

        const val EXTRA = "keyWord"
        private const val PREFETCH_DISTANCE = 20
    }
}