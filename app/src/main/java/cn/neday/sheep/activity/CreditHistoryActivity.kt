package cn.neday.sheep.activity

import cn.neday.sheep.R
import cn.neday.sheep.viewmodel.CreditHistoryViewModel

class CreditHistoryActivity : BaseVMActivity<CreditHistoryViewModel>() {

    override val layoutId = R.layout.activity_search

    override fun initView() {
        mViewModel.getHotWords()
    }
}
