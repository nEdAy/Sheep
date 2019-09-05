//package cn.neday.sheep.activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.NumberPicker;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.neday.bomb.CustomApplication;
//import com.neday.bomb.R;
//import com.neday.bomb.base.BaseActivity;
//import com.neday.bomb.util.SharedPreferencesHelper;
//
//import org.json.JSONArray;
//
//import java.util.Arrays;
//
//import static com.neday.bomb.R.id.rl_notification;
//
///**
// * 推送设置页
// *
// * @author nEdAy
// */
//public class PushSettingActivity extends BaseActivity implements View.OnClickListener {
//    private RelativeLayout rl_voice;
//    private RelativeLayout rl_vibrate;
//    private RelativeLayout rl_quiet_period;
//    private RelativeLayout rl_time;
//    private ImageView iv_open_notification, iv_close_notification, iv_open_voice,
//            iv_close_voice, iv_open_vibrate, iv_close_vibrate, iv_open_quiet, iv_close_quiet;
//    private SharedPreferencesHelper sharedPreferencesHelper;
//    private int mHour_0, mMinute_0, mHour_1, mMinute_1;
//    private NumberPicker mHourSpinner_0, mMinuteSpinner_0, mHourSpinner_1, mMinuteSpinner_1;
//    private TextView tv_quiet_period;
//    private final NumberPicker.OnValueChangeListener mOnHourChangedListener = (picker, oldVal, newVal) -> {
//        mHour_0 = mHourSpinner_0.getValue();
//        mMinute_0 = mMinuteSpinner_0.getValue();
//        mHour_1 = mHourSpinner_1.getValue();
//        mMinute_1 = mMinuteSpinner_1.getValue();
//        tv_quiet_period.setText(String.format(getResources().getString(R.string.tx_quiet_period),
//                getDateFormat(mHour_0), getDateFormat(mMinute_0), getDateFormat(mHour_1), getDateFormat(mMinute_1)));
//    };
//
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_setting_push;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        setTintManager();
//        initTopBarForLeft("推送设置", getString(R.string.tx_back));
//        rl_voice = findViewById(R.id.rl_voice);
//        rl_vibrate = findViewById(R.id.rl_vibrate);
//        rl_quiet_period = findViewById(R.id.rl_quiet_period);
//        rl_time = findViewById(R.id.rl_time);
//        iv_open_notification = findViewById(R.id.iv_open_notification);
//        iv_close_notification = findViewById(R.id.iv_close_notification);
//        iv_open_voice = findViewById(R.id.iv_open_voice);
//        iv_close_voice = findViewById(R.id.iv_close_voice);
//        iv_open_vibrate = findViewById(R.id.iv_open_vibrate);
//        iv_close_vibrate = findViewById(R.id.iv_close_vibrate);
//        iv_open_quiet = findViewById(R.id.iv_open_province_flow);
//        iv_close_quiet = findViewById(R.id.iv_close_province_flow);
//        mHourSpinner_0 = findViewById(R.id.mHourSpinner_0);
//        mMinuteSpinner_0 = findViewById(R.id.mMinuteSpinner_0);
//        mHourSpinner_1 = findViewById(R.id.mHourSpinner_1);
//        mMinuteSpinner_1 = findViewById(R.id.mMinuteSpinner_1);
//        tv_quiet_period = findViewById(R.id.tv_quiet_period);
//        rl_voice.setOnClickListener(this);
//        rl_vibrate.setOnClickListener(this);
//        rl_quiet_period.setOnClickListener(this);
//        findViewById(R.id.rl_notification).setOnClickListener(this);
//        findViewById(R.id.rl_province_flow).setOnClickListener(this);
//        findViewById(R.id.tv_btn_submit).setOnClickListener(this);
//        sharedPreferencesHelper = CustomApplication.getInstance().getSpHelper();
//        int[] resArray = new int[4];
//        Arrays.fill(resArray, 0);
//        try {
//            JSONArray jsonArray = new JSONArray(sharedPreferencesHelper.getQuitePeriod());
//            for (int i = 0; i < jsonArray.length(); i++) {
//                resArray[i] = jsonArray.getInt(i);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mHour_0 = resArray[0];
//        mMinute_0 = resArray[1];
//        mHour_1 = resArray[2];
//        mMinute_1 = resArray[3];
//        tv_quiet_period.setText(String.format(getResources().getString(R.string.tx_quiet_period), getDateFormat(mHour_0),
//                getDateFormat(mMinute_0), getDateFormat(mHour_1), getDateFormat(mMinute_1)));
//        mHourSpinner_0.setMaxValue(23);
//        mHourSpinner_0.setMinValue(0);
//        mHourSpinner_0.setValue(mHour_0);
//        mHourSpinner_0.setOnValueChangedListener(mOnHourChangedListener);
//        mMinuteSpinner_0.setMaxValue(59);
//        mMinuteSpinner_0.setMinValue(0);
//        mMinuteSpinner_0.setValue(mMinute_0);
//        mMinuteSpinner_0.setOnValueChangedListener(mOnHourChangedListener);
//        mHourSpinner_1.setMaxValue(23);
//        mHourSpinner_1.setMinValue(0);
//        mHourSpinner_1.setValue(mHour_1);
//        mHourSpinner_1.setOnValueChangedListener(mOnHourChangedListener);
//        mMinuteSpinner_1.setMaxValue(59);
//        mMinuteSpinner_1.setMinValue(0);
//        mMinuteSpinner_1.setValue(mMinute_1);
//        mMinuteSpinner_1.setOnValueChangedListener(mOnHourChangedListener);
//        // 初始化各個按鈕顯示
//        if (sharedPreferencesHelper.isAllowPushNotify()) {
//            iv_open_notification.setVisibility(View.VISIBLE);
//            iv_close_notification.setVisibility(View.INVISIBLE);
//        } else {
//            iv_open_notification.setVisibility(View.INVISIBLE);
//            iv_close_notification.setVisibility(View.VISIBLE);
//        }
//        if (sharedPreferencesHelper.isAllowVoice()) {
//            iv_open_voice.setVisibility(View.VISIBLE);
//            iv_close_voice.setVisibility(View.INVISIBLE);
//        } else {
//            iv_open_voice.setVisibility(View.INVISIBLE);
//            iv_close_voice.setVisibility(View.VISIBLE);
//        }
//        if (sharedPreferencesHelper.isAllowVibrate()) {
//            iv_open_vibrate.setVisibility(View.VISIBLE);
//            iv_close_vibrate.setVisibility(View.INVISIBLE);
//        } else {
//            iv_open_vibrate.setVisibility(View.INVISIBLE);
//            iv_close_vibrate.setVisibility(View.VISIBLE);
//        }
//        if (sharedPreferencesHelper.isAllowQuiet()) {
//            iv_open_quiet.setVisibility(View.VISIBLE);
//            iv_close_quiet.setVisibility(View.INVISIBLE);
//        } else {
//            iv_open_quiet.setVisibility(View.INVISIBLE);
//            iv_close_quiet.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case rl_notification:
//                if (iv_open_notification.getVisibility() == View.VISIBLE) {
//                    iv_open_notification.setVisibility(View.INVISIBLE);
//                    iv_close_notification.setVisibility(View.VISIBLE);
//                    sharedPreferencesHelper.setPushNotifyEnable(false);
////                    PushManager.getInstance().turnOffPush(CustomApplication.getInstance());//反注册推送
//                    rl_vibrate.setVisibility(View.GONE);
//                    rl_voice.setVisibility(View.GONE);
//                } else {
//                    iv_open_notification.setVisibility(View.VISIBLE);
//                    iv_close_notification.setVisibility(View.INVISIBLE);
//                    sharedPreferencesHelper.setPushNotifyEnable(true);
////                    PushManager.getInstance().turnOnPush(CustomApplication.getInstance());//注册推送
//                    rl_vibrate.setVisibility(View.VISIBLE);
//                    rl_voice.setVisibility(View.VISIBLE);
//                }
//                break;
//            case R.id.rl_voice:
//                if (iv_open_voice.getVisibility() == View.VISIBLE) {
//                    iv_open_voice.setVisibility(View.INVISIBLE);
//                    iv_close_voice.setVisibility(View.VISIBLE);
//                    sharedPreferencesHelper.setAllowVoiceEnable(false);
//                } else {
//                    iv_open_voice.setVisibility(View.VISIBLE);
//                    iv_close_voice.setVisibility(View.INVISIBLE);
//                    sharedPreferencesHelper.setAllowVoiceEnable(true);
//                }
//                break;
//            case R.id.rl_vibrate:
//                if (iv_open_vibrate.getVisibility() == View.VISIBLE) {
//                    iv_open_vibrate.setVisibility(View.INVISIBLE);
//                    iv_close_vibrate.setVisibility(View.VISIBLE);
//                    sharedPreferencesHelper.setAllowVibrateEnable(false);
//                } else {
//                    iv_open_vibrate.setVisibility(View.VISIBLE);
//                    iv_close_vibrate.setVisibility(View.INVISIBLE);
//                    sharedPreferencesHelper.setAllowVibrateEnable(true);
//                }
//                break;
//            case R.id.rl_province_flow:
//                if (iv_open_quiet.getVisibility() == View.VISIBLE) {
//                    iv_open_quiet.setVisibility(View.INVISIBLE);
//                    iv_close_quiet.setVisibility(View.VISIBLE);
//                    rl_quiet_period.setVisibility(View.INVISIBLE);
//                    rl_time.setVisibility(View.INVISIBLE);
//                    sharedPreferencesHelper.setAllowQuietEnable(false);
//                } else {
//                    iv_open_quiet.setVisibility(View.VISIBLE);
//                    iv_close_quiet.setVisibility(View.INVISIBLE);
//                    rl_quiet_period.setVisibility(View.VISIBLE);
//                    sharedPreferencesHelper.setAllowQuietEnable(true);
//                }
//                break;
//            case R.id.rl_quiet_period:
//                rl_time.setVisibility(View.VISIBLE);
////                PushManager.getInstance().setSilentTime(CustomApplication.getInstance(), mHour_0, Math.abs(mHour_1 - mHour_0));
//                break;
//            case R.id.tv_btn_submit:
//                JSONArray jsonArray = new JSONArray();
//                jsonArray.put(mHour_0);
//                jsonArray.put(mMinute_0);
//                jsonArray.put(mHour_1);
//                jsonArray.put(mMinute_1);
//                sharedPreferencesHelper.setQuitePeriod(jsonArray.toString());
//                rl_time.setVisibility(View.GONE);
////                PushManager.getInstance().setSilentTime(CustomApplication.getInstance(), mHour_0, Math.abs(mHour_1 - mHour_0));
//                break;
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 格式化时间的时 如9变为'09'
//     *
//     * @param time 時間
//     * @return 时间字符串
//     */
//    private String getDateFormat(int time) {
//        String date = time + "";
//        if (time < 10) {
//            date = "0" + date;
//        }
//        return date;
//    }
//}
