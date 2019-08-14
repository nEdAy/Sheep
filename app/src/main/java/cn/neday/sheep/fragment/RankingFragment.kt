package cn.neday.sheep.fragment

import cn.neday.sheep.R
import cn.neday.sheep.enum.RankType
import kotlinx.android.synthetic.main.fragment_main_ranking.*

class RankingFragment : BaseFragment(R.layout.fragment_main_ranking) {

    override fun initView() {
        initViewPager()
    }

    private fun initViewPager() {
        stl_ranking.setViewPager(
            vp_ranking,
            resources.getStringArray(R.array.ranking_type_array),
            activity,
            arrayListOf(
                RankingListFragment(RankType.SHI_SHI_XIAO_XIANG_BANG),
                RankingListFragment(RankType.QUAN_TIAN_XIAO_LIANG_BANG),
                RankingListFragment(RankType.RE_TUI_BANG)
            )
        )
        vp_ranking.currentItem = 1
    }
}