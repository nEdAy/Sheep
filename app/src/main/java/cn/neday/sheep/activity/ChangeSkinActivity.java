//package cn.neday.sheep.activity;
//
//
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.neday.bomb.CustomApplication;
//import com.neday.bomb.R;
//import com.neday.bomb.base.BaseActivity;
//import com.neday.bomb.util.CommonUtils;
//import com.neday.bomb.util.SharedPreferencesHelper;
//
///**
// * 修改背景皮肤页
// *
// * @author nEdAy
// */
//public class ChangeSkinActivity extends BaseActivity {
//    //背景示例图（上）
//    private static final int[] selfcenter_bg_main = {R.drawable.selfcenter_bg_main_0, R.drawable.selfcenter_bg_main_1,
//            R.drawable.selfcenter_bg_main_2, R.drawable.selfcenter_bg_main_3, R.drawable.selfcenter_bg_main_4,};
//    //背景选择图（下）
//    private static final int[] selfcenter_bg_banner = {R.drawable.selfcenter_bg_banner_0, R.drawable.selfcenter_bg_banner_1,
//            R.drawable.selfcenter_bg_banner_2, R.drawable.selfcenter_bg_banner_3, R.drawable.selfcenter_bg_banner_4,};
//    private ImageView skin_image;
//    private SharedPreferencesHelper sharedPreferencesHelper;
//
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_change_skin;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        setTintManager();
//        initTopBarForLeft("自定义皮肤", getString(R.string.tx_back));
//        skin_image = findViewById(R.id.skin_image);
//        LinearLayout mLinearLayout = findViewById(R.id.my_gallery);
//        sharedPreferencesHelper = CustomApplication.getInstance().getSpHelper();
//        int centerBg = sharedPreferencesHelper.getCenterBg();
//        skin_image.setImageResource(selfcenter_bg_main[centerBg]);
//        for (int i = 0; i < 5; i++) {
//            mLinearLayout.addView(getImageView(i));
//        }
//    }
//
//    /**
//     * 构造每个背景图片的画廊显示
//     *
//     * @param number 构造序数
//     * @return 视图
//     */
//    private View getImageView(final int number) {
//        RelativeLayout layout = new RelativeLayout(getApplicationContext());
//        layout.setLayoutParams(new RelativeLayout.LayoutParams(250, 250));
//        layout.setGravity(Gravity.CENTER);
//        ImageView imageView = new ImageView(mContext);
//        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(selfcenter_bg_banner[number]);
//        layout.addView(imageView);
//        layout.setOnClickListener(view -> {
//            skin_image.setImageResource(selfcenter_bg_main[number]);
//            CommonUtils.showToast("已设置此背景皮肤");
//            sharedPreferencesHelper.setCenterBg(number);
//        });
//        return layout;
//    }
//
//}