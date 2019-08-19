package cn.neday.sheep.fragment

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.neday.base.router.Router
import cn.neday.sheep.R
import cn.neday.sheep.activity.LoginActivity
import cn.neday.sheep.activity.SearchActivity
import cn.neday.sheep.activity.ShakeActivity
import cn.neday.sheep.activity.SignInActivity
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.viewmodel.IndexViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_main_index.*
import kotlinx.android.synthetic.main.include_anything_list.*
import kotlinx.android.synthetic.main.include_main_index_header.view.*
import kotlinx.android.synthetic.main.include_main_index_icon.view.*

/**
 * 首页
 */
class IndexFragment : GoodsListFragment<IndexViewModel>(R.layout.fragment_main_index) {

    private lateinit var headerView: View

    //TODO : 待测试 首页初始显示
    override fun initView() {
        super.initView()
        initSearchHeader()
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

    override fun loadInitial() {
        mViewModel.getBannerList()
        mViewModel.getRankingList(3)
    }

    override fun initAdapter() {
        super.initAdapter()
        addHeaderView(adapter)
        mViewModel.rankGoods.observe(this, Observer {
            adapter.setNewData(it)
            srl_goods.isRefreshing = false
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
                Router.alibabaService.showItemURLPage(activity, it[position].url)
            }
        })
    }

    private fun initHeaderIcon(headerView: View) {
        headerView.ll_sign.setOnClickListener { ActivityUtils.startActivity(SignInActivity::class.java) }
        headerView.ll_shake.setOnClickListener { ActivityUtils.startActivity(ShakeActivity::class.java) }
        headerView.ll_shop.setOnClickListener { ActivityUtils.startActivity(LoginActivity::class.java) }
        headerView.ll_join.setOnClickListener { CommonUtils.joinQQGroup(activity) }
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
