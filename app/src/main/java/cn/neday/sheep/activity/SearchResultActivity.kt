package cn.neday.sheep.activity

import androidx.fragment.app.commit
import cn.neday.base.activity.BaseVMActivity
import cn.neday.sheep.R
import cn.neday.sheep.fragment.SearchResultFragment
import cn.neday.sheep.viewmodel.SearchResultViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search_result.*

/**
 * 搜索结果列表页
 *
 * @author nEdAy
 */
class SearchResultActivity : BaseVMActivity<SearchResultViewModel>(R.layout.activity_search_result) {

    override fun initView() {
        val keyWord = intent.extras?.get(EXTRA) as String?
        keyWord?.let {
            initSearchBar(keyWord)
            initFragment(keyWord)
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

    private fun initFragment(keyWord: String) {
        supportFragmentManager.commit(allowStateLoss = true) {
            replace(R.id.fragment, SearchResultFragment(keyWord))
        }
    }

    companion object {

        const val EXTRA = "keyWord"
    }
}