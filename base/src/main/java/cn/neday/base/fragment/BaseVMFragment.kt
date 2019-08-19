package cn.neday.base.fragment

import androidx.lifecycle.Observer
import cn.neday.base.viewmodel.BaseViewModel
import com.blankj.utilcode.util.LogUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.ParameterizedType

/**
 * Fragment基类 + ViewModel
 *
 * @author nEdAy
 */
abstract class BaseVMFragment<VM : BaseViewModel>(layoutId: Int) : BaseFragment(layoutId) {

    @Suppress("UNCHECKED_CAST")
    protected val mViewModel: VM by viewModel(((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>).kotlin)

    override fun prepareInitView() {
        super.prepareInitView()
        lifecycle.addObserver(mViewModel)
        mViewModel.errMsg.observe(this, Observer {
            LogUtils.e(it)
        })
    }
}
