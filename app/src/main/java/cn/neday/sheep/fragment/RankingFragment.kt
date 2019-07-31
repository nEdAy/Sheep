package cn.neday.sheep.fragment

import androidx.fragment.app.Fragment
import cn.neday.sheep.R
import cn.neday.sheep.enum.RankType
import kotlinx.android.synthetic.main.fragment_main_ranking.*

class RankingFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_main_ranking

    override fun initView() {
        initViewPager()
    }

    private fun initViewPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(RankingListFragment(RankType.SHI_SHI_XIAO_XIANG_BANG))
        fragments.add(RankingListFragment(RankType.QUAN_TIAN_XIAO_LIANG_BANG))
        fragments.add(RankingListFragment(RankType.RE_TUI_BANG))
        stl_ranking.setViewPager(vp_ranking, resources.getStringArray(R.array.ranking_type_array), activity, fragments)
        vp_ranking.currentItem = 1
    }
}