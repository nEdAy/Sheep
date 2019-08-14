package cn.neday.sheep.fragment

import cn.neday.sheep.R
import cn.neday.sheep.enum.NineType
import kotlinx.android.synthetic.main.fragment_main_goods.*

/**
 * 9.9精选
 * -1-精选，1 -居家百货，2 -美食，3 -服饰，4 -配饰，5 -美妆，6 -内衣，7 -母婴，8 -箱包，9 -数码配件，10 -文娱车品
 */
class GoodsFragment : BaseFragment(R.layout.fragment_main_goods) {

    override fun initView() {
        initViewPager()
    }

    private fun initViewPager() {
        stl_goods.setViewPager(
            vp_goods,
            resources.getStringArray(R.array.goods_type_array),
            activity,
            arrayListOf(
                GoodsListFragment(NineType.JING_XUAN),
                GoodsListFragment(NineType.JU_JUA_BAI_HUO),
                GoodsListFragment(NineType.MEI_SHI),
                GoodsListFragment(NineType.FU_SHI),
                GoodsListFragment(NineType.PEI_SHI),
                GoodsListFragment(NineType.MEI_ZHUANG),
                GoodsListFragment(NineType.NEI_YI),
                GoodsListFragment(NineType.MU_YING),
                GoodsListFragment(NineType.XIANG_BAO),
                GoodsListFragment(NineType.SHU_MA_PEI_JIAN),
                GoodsListFragment(NineType.WEN_YU_CHE_PIN)
            )
        )
    }
}