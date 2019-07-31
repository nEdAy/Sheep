package cn.neday.sheep.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.adapter.KeyWordsListAdapter
import cn.neday.sheep.viewmodel.SearchViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fadai.particlesmasher.ParticleSmasher
import com.fadai.particlesmasher.SmashAnimator
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search.*

/**
 * 搜索页（历史搜索，热门搜索）
 *
 * @author nEdAy
 */
class SearchActivity : BaseVMActivity<SearchViewModel>() {

    override val layoutId = R.layout.activity_search

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java

    private lateinit var mParticleSmasher: ParticleSmasher

    override fun initView() {
        initSearch()
        initHistoryWords()
        initHotWords()
        mViewModel.getHotWords()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getHistoryWords()
    }

    private fun initSearch() {
        titleBar_search.centerSearchEditText.hint = getString(R.string.tx_search_hint)
        titleBar_search.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                val bundle = Bundle()
                bundle.putString(SearchResultActivity.EXTRA, titleBar_search.searchKey)
                ActivityUtils.startActivity(bundle, SearchResultActivity::class.java)
            } else if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            }
        }
    }

    private fun initHistoryWords() {
        mParticleSmasher = ParticleSmasher(this)
        val historyWordsListAdapter = KeyWordsListAdapter()
        addOnItemClickListener(historyWordsListAdapter)
        historyWordsListAdapter.onItemLongClickListener =
            BaseQuickAdapter.OnItemLongClickListener { adapter, view, position ->
                mParticleSmasher.with(view)?.start()
                mViewModel.removeHistoryWords(adapter.getItem(position).toString())
                true
            }
        rv_search_history_words.adapter = historyWordsListAdapter
        rv_search_history_words.layoutManager = getFlexboxLayoutManager()

        mViewModel.historyWords.observe(this, Observer {
            historyWordsListAdapter.setNewData(it?.reversed())
        })
        tv_search_history_clean.setOnClickListener {
            mParticleSmasher.with(rv_search_history_words)
                .addAnimatorListener(object : SmashAnimator.OnAnimatorListener() {
                    override fun onAnimatorStart() {
                        super.onAnimatorStart()
                        mViewModel.cleanHistoryWords()
                    }

                    override fun onAnimatorEnd() {
                        super.onAnimatorEnd()
                        mParticleSmasher.reShowView(rv_search_history_words)
                    }
                }).start()
        }
    }

    private fun initHotWords() {
        val hotWordsListAdapter = KeyWordsListAdapter()
        addOnItemClickListener(hotWordsListAdapter)
        rv_search_hot_words.adapter = hotWordsListAdapter
        rv_search_hot_words.layoutManager = getFlexboxLayoutManager()
        mViewModel.hotWords.observe(this, Observer {
            hotWordsListAdapter.setNewData(it?.hotWords?.reversed())
        })
    }

    private fun addOnItemClickListener(keyWordsListAdapter: KeyWordsListAdapter) {
        keyWordsListAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bundle = Bundle()
            bundle.putString(SearchResultActivity.EXTRA, adapter.getItem(position).toString())
            ActivityUtils.startActivity(bundle, SearchResultActivity::class.java)
        }
    }

    private fun getFlexboxLayoutManager(): FlexboxLayoutManager {
        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        return flexboxLayoutManager
    }

    override fun onDestroy() {
        super.onDestroy()
        mParticleSmasher.clear()
    }
}