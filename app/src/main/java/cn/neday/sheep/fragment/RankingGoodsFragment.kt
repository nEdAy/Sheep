package cn.neday.sheep.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.neday.sheep.R
import cn.neday.sheep.enum.RankType
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main_ranking_goods.*

class RankingGoodsFragment : BaseFragment(R.layout.fragment_main_ranking_goods) {

    override fun initView() {
        initViewPager()
    }

    private fun initViewPager() {
        val fragmentTitleList = resources.getStringArray(R.array.ranking_type_array)
        vp2_ranking.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return arrayListOf(
                    RankingListFragment(RankType.SHI_SHI_XIAO_XIANG_BANG),
                    RankingListFragment(RankType.QUAN_TIAN_XIAO_LIANG_BANG),
                    RankingListFragment(RankType.RE_TUI_BANG)
                )[position]
            }

            override fun getItemCount(): Int {
                return fragmentTitleList.size
            }
        }
        TabLayoutMediator(tl_ranking, vp2_ranking) { tab, position ->
            tab.text = fragmentTitleList[position]
        }.attach()
        vp2_ranking.currentItem = 1
    }
}