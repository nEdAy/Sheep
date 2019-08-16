package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.enum.RankType
import cn.neday.sheep.viewmodel.RankingListViewModel

/**
 * 各大榜单
 * 1.实时销量榜
 * 2.全天销量榜
 * 3.热推榜
 */
class RankingListFragment(private val rankType: RankType) :
    GoodsListFragment<RankingListViewModel>(R.layout.fragment_goods_list) {

    override fun loadInitial() {
        mViewModel.getRankingList(rankType.index)
    }

    override fun initAdapter() {
        super.initAdapter()
        mViewModel.rankGoods.observe(this, Observer {
            adapter.setNewData(it)
        })
    }
}