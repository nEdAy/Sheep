package cn.neday.sheep.activity

import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.neday.base.activity.BaseVMActivity
import cn.neday.base.config.MMKVConfig
import cn.neday.base.model.Pages
import cn.neday.base.router.Router
import cn.neday.sheep.KZ_JF
import cn.neday.sheep.LOAD_INITIAL_PAGE_ID
import cn.neday.sheep.PREFETCH_DISTANCE
import cn.neday.sheep.R
import cn.neday.sheep.adapter.CreditHistoryListAdapter
import cn.neday.sheep.model.CreditHistory
import cn.neday.sheep.viewmodel.CreditHistoryViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_credit_history.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.include_anything_list.*

/**
 * 口袋币历史页
 *
 * @author nEdAy
 */
class CreditHistoryActivity :
    BaseVMActivity<CreditHistoryViewModel>(R.layout.activity_credit_history) {

    val adapter = CreditHistoryListAdapter()

    override fun initView() {
        initTitleBar()
        initAdapter()
        initSwipeToRefresh()
        loadInitial()
    }

    private fun initTitleBar() {
        titleBar_sign_in.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            } else if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                Router.alibabaService.showItemURLPage(this, KZ_JF)
            }
        }
    }

    private fun initAdapter() {
        adapter.bindToRecyclerView(rv_credit_history)
        addEmptyView(adapter)
        mViewModel.errMsg.observe(this, Observer {
            ToastUtils.showShort(it)
            adapter.loadMoreFail()
        })
        mViewModel.onComplete.observe(this, Observer {
            srl_credit_history.isRefreshing = false
        })
        adapter.setPreLoadNumber(PREFETCH_DISTANCE)
        adapter.setOnLoadMoreListener({
            //            mViewModel.getCreditHistoryListByUserId()
        }, rv_credit_history)
        mViewModel.creditHistories.observe(this, Observer<Pages<CreditHistory>> {
            if (adapter.itemCount >= it.totalNum ?: 0) {
                adapter.loadMoreEnd()
            } else {
                setAdapterData(adapter, it)
                mViewModel.mCurrentPageId = it.pageId ?: LOAD_INITIAL_PAGE_ID
                adapter.loadMoreComplete()
            }
        })
    }

    private fun addEmptyView(adapter: CreditHistoryListAdapter) {
        adapter.setEmptyView(R.layout.include_no_data, rv_credit_history.parent as ViewGroup)
        adapter.emptyView.setOnClickListener { loadInitial() }
    }

    private fun setAdapterData(adapter: CreditHistoryListAdapter, data: Pages<CreditHistory>) {
        if (mViewModel.mCurrentPageId == LOAD_INITIAL_PAGE_ID) {
            srl_goods.isRefreshing = false
            adapter.setNewData(data.list)
            adapter.disableLoadMoreIfNotFullPage()
        } else {
            data.list?.let { adapter.addData(it) }
        }
    }

    private fun initSwipeToRefresh() {
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { loadInitial() }
    }

    private fun loadInitial() {
        mViewModel.getCreditHistoryListByUserId(kv.decodeInt(MMKVConfig.ID))
        mViewModel.getCreditByUserId()
    }
}
