package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.neday.sheep.viewmodel.BaseViewModel
import com.blankj.utilcode.util.LogUtils

/**
 * Fragment基类 + ViewModel
 *
 * @author nEdAy
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {

    protected lateinit var mViewModel: VM

    override fun prepareInitView() {
        super.prepareInitView()
        initProviderViewModel()
    }

    private fun initProviderViewModel() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
        mViewModel.errMsg.observe(this, Observer {
            LogUtils.e(it)
        })
    }

    open fun providerVMClass(): Class<VM>? = null
}
