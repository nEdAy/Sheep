package cn.neday.sheep.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alibaba.baichuan.android.trade.AlibcTradeSDK

/**
 * Fragment基类
 *
 * @author nEdAy
 */
abstract class BaseFragment : Fragment() {

    private var mRootView: View? = null

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareInitView()
        initView()
    }

    open fun prepareInitView() {
        // do nothing
    }

    abstract fun initView()

    override fun onDestroyView() {
        super.onDestroyView()
        AlibcTradeSDK.destory()
        mRootView = null
    }
}
