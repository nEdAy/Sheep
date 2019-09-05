//package cn.neday.sheep.activity;
//
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//
//import com.neday.bomb.R;
//import com.neday.bomb.base.BaseActivity;
//import com.neday.bomb.util.CommonUtils;
//
///**
// * Vip说明页
// *
// * @author nEdAy
// */
//public class VipHelpActivity extends BaseActivity {
//    private static final String[] vipList = {"0-99", "100-199", "200-499", "500-999",
//            "1000-1999", "2000-4999", "5000-14999", "15000-49999", "50000-99999", "100000-199999", "200000-暂缺"};
//    private static final int[] vipImageList = {R.drawable.level_0, R.drawable.level_1,
//            R.drawable.level_2, R.drawable.level_3, R.drawable.level_4, R.drawable.level_5, R.drawable.level_6,
//            R.drawable.level_7, R.drawable.level_8, R.drawable.level_9, R.drawable.level_10};
//
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_vip_help;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        setTintManager();
//        initTopBarForLeft("等级说明", getString(R.string.tx_back));
//        initVipHelpTable();
//    }
//
//    /**
//     * 构造等级和积分对应表格View
//     */
//    private void initVipHelpTable() {
//        // 新建TableLayout的实例
//        TableLayout tableLayout = findViewById(R.id.tableLayout);
//        // 全部列自动填充空白处
//        tableLayout.setStretchAllColumns(true);
//        // 生成10行，8列的表格
//        for (int row = 0; row < 11; row++) {
//            TableRow tableRow = new TableRow(this);
//            tableRow.setGravity(Gravity.CENTER_VERTICAL);
//            int px = CommonUtils.dip2px(mContext, 20F);
//            tableRow.setPadding(px, px, px, px / 2);
//            // tv用于显示
//            ImageView imageView = new ImageView(mContext);
//            imageView.setImageResource(vipImageList[row]);
//            tableRow.addView(imageView);
//            TextView textView = new TextView(mContext);
//            textView.setText(vipList[row]);
//            textView.setTextColor(getBaseContext().getResources().getColor(R.color.red_deep));
//            tableRow.addView(textView);
//            // 新建的TableRow添加到TableLayout
//            tableLayout.addView(tableRow, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            View line = new View(mContext);
//            line.setBackgroundColor(getBaseContext().getResources().getColor(
//                    R.color.line_color));
//            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(mContext, 1));
//            line.setLayoutParams(l);
//            tableLayout.addView(line);
//        }
//    }
//
//}
