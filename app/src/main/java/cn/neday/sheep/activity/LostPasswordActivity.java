//package cn.neday.sheep.activity;
//
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.flyco.animation.BounceEnter.BounceTopEnter;
//import com.flyco.animation.SlideExit.SlideBottomExit;
//import com.flyco.dialog.widget.NormalDialog;
//import com.neday.bomb.CustomApplication;
//import com.neday.bomb.R;
//import com.neday.bomb.StaticConfig;
//import com.neday.bomb.base.BaseActivity;
//import com.neday.bomb.network.RxFactory;
//import com.neday.bomb.util.CommonUtils;
//import com.neday.bomb.util.SharedPreferencesHelper;
//import com.neday.bomb.util.StringUtils;
//import com.neday.bomb.view.ClearEditText;
//import com.neday.bomb.view.loading.CatLoadingView;
//import com.orhanobut.logger.Logger;
//
//import org.json.JSONObject;
//
//import cn.smssdk.EventHandler;
//import cn.smssdk.SMSSDK;
//
///**
// * 重置登录密码页
// *
// * @author nEdAy
// */
//public class LostPasswordActivity extends BaseActivity {
//    private final static String TAG = "LostPasswordActivity";
//    private final static String country = "86";
//    private CatLoadingView catLoadingView;
//    private ClearEditText et_phone;
//    private EditText et_password, et_sms;
//    private ImageView iv_password_see;
//    private TextView tv_sms;
//    private boolean isVoice;
//    private TimeCount timeCount;
//    private final EventHandler eventHandler = new EventHandler() {
//        @Override
//        public void afterEvent(int event, int result, Object data) {
//            if (result == SMSSDK.RESULT_COMPLETE) {
//                //回调完成
//                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                    //走短信验证
//                    runOnUiThread(() -> tv_sms.setClickable(false));
//                } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
//                    //请求发送语音验证码，无返回
//                    runOnUiThread(() -> {
//                        tv_sms.setText(" 致电中...请稍等 ");
//                        tv_sms.setEnabled(false);
//                    });
//                }
//            } else {
//                runOnUiThread(() -> {
//                    timeCount.cancel();
//                    tv_sms.setEnabled(true);
//                    tv_sms.setText(" 异常，请重试 ");
//                });
//                try {
//                    Throwable throwable = (Throwable) data;
//                    throwable.printStackTrace();
//                    JSONObject object = new JSONObject(throwable.getMessage());
//                    String des = object.optString("detail");//错误描述
//                    int status = object.optInt("status");//错误代码
//                    if (status > 0 && !TextUtils.isEmpty(des)) {
//                        CommonUtils.showToast(des);
//                    }
//                } catch (Exception ignored) {
//                }
//            }
//        }
//    };
//    private boolean password_saw = false;
//
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_lost_password;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        setTintManager();
//        initTopBarForLeft("重置登录密码", getString(R.string.tx_back));
//        catLoadingView = new CatLoadingView();
//        et_phone = findViewById(R.id.et_phone);
//        et_password = findViewById(R.id.et_password);
//        et_sms = findViewById(R.id.et_sms);
//        tv_sms = findViewById(R.id.tv_sms);
//        iv_password_see = findViewById(R.id.iv_password_see);
//        // 初始化 MobSMS SDK
//        initSms();
//        // 交互优化，修改密码的用户名提前获取
//        SharedPreferencesHelper sharedPreferencesHelper = CustomApplication.getInstance().getSpHelper();
//        String oldPhone = sharedPreferencesHelper.getUserPhone();
//        if (!TextUtils.isEmpty(oldPhone)) {
//            et_phone.setText(oldPhone);
//            et_password.setFocusable(true);
//            et_password.setFocusableInTouchMode(true);
//            et_password.requestFocus();
//            et_password.requestFocusFromTouch();
//        }
//        tv_sms.setOnClickListener(v -> requestVerificationCode());
//        iv_password_see.setOnClickListener(v -> changePasswordSee());
//        findViewById(R.id.btn_submit).setOnClickListener(v -> resetPassword());
//    }
//
//    /**
//     * 初始化 MobSMS SDK
//     */
//    private void initSms() {
//        SMSSDK.initSDK(getBaseContext(), StaticConfig.MOB_APP_KEY, StaticConfig.MOB_APP_SECRET);
//        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
//    }
//
//    /**
//     * 改变密码隐藏按钮样式和输入框类型
//     */
//    private void changePasswordSee() {
//        if (password_saw) {//false
//            password_saw = false;
//            iv_password_see.setImageResource(R.drawable.ic_see_normal);
//            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        } else {
//            password_saw = true;
//            iv_password_see.setImageResource(R.drawable.ic_see_pressed);
//            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//        }
//        et_password.setSelection(et_password.getText().length());
//    }
//
//    /**
//     * 请求短信验证码
//     */
//    private void requestVerificationCode() {
//        String phone = et_phone.getText().toString().trim().replace(" ", "");
//        if (TextUtils.isEmpty(phone)) {
//            CommonUtils.showToast(R.string.toast_error_phone_null);
//            et_phone.requestFocus();
//            CommonUtils.setShakeAnimation(et_phone);
//            return;
//        }
//        if (!StringUtils.isPhoneNumberValid(phone)) {
//            CommonUtils.showToast(R.string.toast_error_phone_error);
//            et_phone.requestFocus();
//            CommonUtils.setShakeAnimation(et_phone);
//            return;
//        }
//        //先短信验证码，闲置60s后切换语音验证码
//        if (isVoice) {
//            final NormalDialog dialog = new NormalDialog(mContext);
//            dialog.content("确定后将致电您的手机号并语音播报验证码，如不希望被来点打扰请返回。")//
//                    .style(NormalDialog.STYLE_TWO)//
//                    .titleTextSize(23)//
//                    .showAnim(new BounceTopEnter())//
//                    .dismissAnim(new SlideBottomExit())//
//                    .show();
//            dialog.setOnBtnClickL(
//                    dialog::dismiss,
//                    () -> {
//                        dialog.dismiss();
//                        SMSSDK.getVoiceVerifyCode(country, phone);
//                    }
//            );
//        } else {
//            final NormalDialog dialog = new NormalDialog(mContext);
//            dialog.content("我们将发送验证码到该手机号：+" + country + phone)//
//                    .style(NormalDialog.STYLE_TWO)//
//                    .titleTextSize(23)//
//                    .showAnim(new BounceTopEnter())//
//                    .dismissAnim(new SlideBottomExit())//
//                    .show();
//            dialog.setOnBtnClickL(
//                    dialog::dismiss,
//                    () -> {
//                        dialog.dismiss();
//                        timeCount = new TimeCount(30000, 1000);
//                        timeCount.start();
//                        SMSSDK.getVerificationCode(country, phone);
//                    }
//            );
//        }
//    }
//
//    /**
//     * 校验输入合法性并请求重置用户密码
//     */
//    private void resetPassword() {
//        String phone = et_phone.getText().toString().trim().replace(" ", "");
//        String password = et_password.getText().toString().trim();
//        String sms = et_sms.getText().toString().trim();
//        if (TextUtils.isEmpty(phone)) {
//            CommonUtils.showToast(R.string.toast_error_phone_null);
//            et_phone.requestFocus();
//            CommonUtils.setShakeAnimation(et_phone);
//            return;
//        }
//        if (!StringUtils.isPhoneNumberValid(phone)) {
//            CommonUtils.showToast(R.string.toast_error_phone_error);
//            et_phone.requestFocus();
//            CommonUtils.setShakeAnimation(et_phone);
//            return;
//        }
//        if (!StringUtils.isValidPassword(password)) {
//            et_password.requestFocus();
//            CommonUtils.setShakeAnimation(et_password);
//            CommonUtils.showToast(R.string.toast_error_password_error);
//            return;
//        }
//        if (TextUtils.isEmpty(password)) {
//            et_password.requestFocus();
//            CommonUtils.setShakeAnimation(et_password);
//            CommonUtils.showToast(R.string.toast_error_password_null);
//            return;
//        }
//        if (TextUtils.isEmpty(sms)) {
//            et_sms.requestFocus();
//            CommonUtils.setShakeAnimation(et_sms);
//            CommonUtils.showToast(R.string.toast_error_sms_null);
//            return;
//        }
//        if (!CommonUtils.isNetworkAvailable()) {
//            CommonUtils.showToast(R.string.network_tips);
//            return;
//        }
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//                        .resetUserPassword(phone, StringUtils.getMD5(password), sms),
//                () ->
//                        catLoadingView.show(getSupportFragmentManager(), TAG),
//                baseObject -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast("修改成功");
//                    finish();
//                },
//                throwable -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast("修改失败");
//                    Logger.e(throwable.getMessage());
//                });
//    }
//
//    /**
//     * 用户back按键回馈
//     */
//    @Override
//    public void onBackPressed() {
//        final NormalDialog dialog = new NormalDialog(mContext);
//        dialog.content("验证码短信可能略有延迟，确定返回并重新开始？")//
//                .style(NormalDialog.STYLE_TWO)//
//                .titleTextSize(23)//
//                .showAnim(new BounceTopEnter())//
//                .dismissAnim(new SlideBottomExit())//
//                .show();
//        dialog.setOnBtnClickL(
//                dialog::dismiss,
//                () -> {
//                    dialog.superDismiss();
//                    finish();
//                });
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        SMSSDK.registerEventHandler(eventHandler);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        SMSSDK.unregisterEventHandler(eventHandler);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (timeCount != null) {
//            timeCount.cancel();
//        }
//    }
//
//    private final class TimeCount extends CountDownTimer {
//        TimeCount(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            tv_sms.setText(String.format(getString(R.string.countdown_number), millisUntilFinished / 1000));
//        }
//
//        @Override
//        public void onFinish() {
//            tv_sms.setClickable(true);
//            isVoice = true;
//            tv_sms.setText(" 发送语音验证 ");
//        }
//    }
//}
