package cn.neday.sheep.adapter

import androidx.recyclerview.widget.RecyclerView
import cn.neday.sheep.R
import cn.neday.sheep.model.CreditHistory
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 * Adapter for the [RecyclerView]
 *
 * @author nEdAy
 */
class CreditHistoryListAdapter :
    BaseQuickAdapter<CreditHistory, BaseViewHolder>(R.layout.list_item_credit_history, null) {

    override fun convert(helper: BaseViewHolder, creditHistory: CreditHistory) {
        val value = creditHistory.change ?: 0
        val isNegative = value shr 31 == -1 // 得到符号位，0 为正数，-1 为负数
        helper.setText(R.id.tv_creditsHistory_type, creditHistory.message)
            .setText(R.id.tv_creditsHistory_time, creditHistory.createAt)
            .setText(
                R.id.tv_creditsHistory_change, if (isNegative) {
                    value.toString()
                } else {
                    "+$value"
                }
            )
            .setTextColor(
                R.id.tv_creditsHistory_change,
                if (isNegative) {
                    R.color.red
                } else {
                    R.color.green
                }
            )
    }
}