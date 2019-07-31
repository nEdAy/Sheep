package cn.neday.sheep.fragment

import androidx.fragment.app.Fragment
import cn.neday.sheep.R
import cn.neday.sheep.enum.NineType
import kotlinx.android.synthetic.main.fragment_main_goods.*

/**
 * 9.9精选
 * -1-精选，1 -居家百货，2 -美食，3 -服饰，4 -配饰，5 -美妆，6 -内衣，7 -母婴，8 -箱包，9 -数码配件，10 -文娱车品
 */
class GoodsFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_main_goods

    override fun initView() {
        initViewPager()
    }

    private fun initViewPager() {
        val mFragments = ArrayList<Fragment>()
        mFragments.add(GoodsListFragment(NineType.JING_XUAN))
        mFragments.add(GoodsListFragment(NineType.JU_JUA_BAI_HUO))
        mFragments.add(GoodsListFragment(NineType.MEI_SHI))
        mFragments.add(GoodsListFragment(NineType.FU_SHI))
        mFragments.add(GoodsListFragment(NineType.PEI_SHI))
        mFragments.add(GoodsListFragment(NineType.MEI_ZHUANG))
        mFragments.add(GoodsListFragment(NineType.NEI_YI))
        mFragments.add(GoodsListFragment(NineType.MU_YING))
        mFragments.add(GoodsListFragment(NineType.XIANG_BAO))
        mFragments.add(GoodsListFragment(NineType.SHU_MA_PEI_JIAN))
        mFragments.add(GoodsListFragment(NineType.WEN_YU_CHE_PIN))
        stl_goods.setViewPager(
            vp_goods,
            resources.getStringArray(R.array.goods_type_array),
            activity,
            mFragments
        )
    }
}