package cn.neday.sheep.view.rise_number

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

import java.text.DecimalFormat

/**
 * 自定义RiseNumberTextView继承TextView，并实现接口RiseNumberBase
 *
 * @author nEdAy
 */
class RiseNumberTextView : AppCompatTextView, IRiseNumber {
    private var mPlayingState = STOPPED
    private var number: Float = 0f
    private var fromNumber: Float = 0f
    /**
     * 动画播放时长
     */
    private var mDuration: Long = 1500
    /**
     * 1.int 2.float
     */
    private var mNumberType = 2
    private var mDecimalFormat: DecimalFormat? = null
    private var mEndListener: EndListener? = null
    /**
     * 判断动画是否正在播放
     */
    private val isRunning: Boolean
        get() = mPlayingState == RUNNING

    /**
     * 构造方法
     */
    constructor(context: Context) : super(context)

    /**
     * 使用xml布局文件默认的被调用的构造方法
     */
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle)

    /**
     * 跑小数动画
     */
    private fun runFloat() {
        val valueAnimator = ValueAnimator.ofFloat(fromNumber, number)
        valueAnimator.duration = mDuration
        valueAnimator.addUpdateListener { valueAnimator1 ->
            text =
                mDecimalFormat?.format(java.lang.Float.parseFloat(valueAnimator1.animatedValue.toString()).toDouble())
            if (valueAnimator1.animatedFraction >= 1) {
                mPlayingState = STOPPED
                mEndListener?.onEndFinish()
            }
        }
        valueAnimator.start()
    }

    /**
     * 跑整数动画
     */
    private fun runInt() {
        val valueAnimator = ValueAnimator.ofInt(fromNumber.toInt(), number.toInt())
        valueAnimator.duration = mDuration
        valueAnimator.addUpdateListener { valueAnimator1 ->
            // 设置瞬时的数据值到界面上
            text = valueAnimator1.animatedValue.toString()
            if (valueAnimator1.animatedFraction >= 1) {
                // 设置状态为停止
                mPlayingState = STOPPED
                mEndListener?.onEndFinish()
            }
        }
        valueAnimator.start()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mDecimalFormat = DecimalFormat("##0.00")
    }

    /**
     * 开始播放动画
     */
    override fun start() {
        if (!isRunning) {
            mPlayingState = RUNNING
            if (mNumberType == 1)
                runInt()
            else
                runFloat()
        }
    }

    /**
     * 设置一个小数进来
     */
    override fun withNumber(number: Float) {
        this.number = number
        mNumberType = 2
        fromNumber = if (number > 1000) {
            number - Math.pow(10.0, (sizeOfInt(number.toInt()) - 1).toDouble()).toFloat()
        } else {
            number / 2
        }
    }

    /**
     * 设置一个整数进来
     */
    override fun withNumber(number: Int) {
        this.number = number.toFloat()
        mNumberType = 1
        fromNumber = if (number > 1000) {
            number - Math.pow(10.0, (sizeOfInt(number) - 2).toDouble()).toFloat()
        } else {
            (number / 2).toFloat()
        }
    }

    private fun sizeOfInt(x: Int): Int {
        var i = 0
        while (true) {
            if (x <= sizeTable[i])
                return i + 1
            i++
        }
    }

    /**
     * 设置动画播放时间
     */
    override fun setDuration(duration: Long) {
        mDuration = duration
    }

    /**
     * 设置动画结束监听器
     */
    override fun setOnEndListener(callback: EndListener) {
        mEndListener = callback
    }

    /**
     * 定义动画结束接口
     */
    interface EndListener {
        /**
         * 当动画播放结束时的回调方法
         */
        fun onEndFinish()
    }

    companion object {
        private val sizeTable =
            intArrayOf(9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE)
        private const val STOPPED = 0
        private const val RUNNING = 1
    }
}
