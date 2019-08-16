package cn.neday.sheep.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.neday.sheep.R
import cn.neday.sheep.enum.NineType
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main_nine_goods.*

/**
 * 9.9精选
 * -1-精选，1 -居家百货，2 -美食，3 -服饰，4 -配饰，5 -美妆，6 -内衣，7 -母婴，8 -箱包，9 -数码配件，10 -文娱车品
 */
class NineGoodsFragment : BaseFragment(R.layout.fragment_main_nine_goods) {

    override fun initView() {
        initViewPager()
    }

    private fun initViewPager() {
        val fragmentTitleList = resources.getStringArray(R.array.nine_type_array)
        vp2_nine.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return arrayListOf(
                    NineListFragment(NineType.JING_XUAN),
                    NineListFragment(NineType.JU_JUA_BAI_HUO),
                    NineListFragment(NineType.MEI_SHI),
                    NineListFragment(NineType.FU_SHI),
                    NineListFragment(NineType.PEI_SHI),
                    NineListFragment(NineType.MEI_ZHUANG),
                    NineListFragment(NineType.NEI_YI),
                    NineListFragment(NineType.MU_YING),
                    NineListFragment(NineType.XIANG_BAO),
                    NineListFragment(NineType.SHU_MA_PEI_JIAN),
                    NineListFragment(NineType.WEN_YU_CHE_PIN)
                )[position]
            }

            override fun getItemCount(): Int {
                return fragmentTitleList.size
            }
        }
        TabLayoutMediator(tl_nine, vp2_nine) { tab, position ->
            tab.text = fragmentTitleList[position]
        }.attach()
        vp2_nine.offscreenPageLimit = 1
    }
}