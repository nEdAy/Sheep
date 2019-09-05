//package cn.neday.sheep.activity;
//
//import android.os.Build;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.neday.bomb.CustomApplication;
//import com.neday.bomb.R;
//import com.neday.bomb.base.BaseOnlineActivity;
//import com.neday.bomb.entity.User;
//import com.neday.bomb.network.RxFactory;
//import com.neday.bomb.util.CommonUtils;
//import com.neday.bomb.util.IMMLeaks;
//import com.neday.bomb.util.StringUtils;
//import com.neday.bomb.view.loading.CatLoadingView;
//import com.orhanobut.logger.Logger;
//
//import static com.neday.bomb.R.id.btn_submit;
//
///**
// * 修改登陆密码页
// *
// * @author nEdAy
// */
//public class UpdateNewPasswordActivity extends BaseOnlineActivity {
//    private final static String TAG = "UpdateNewPasswordActivity";
//    private CatLoadingView catLoadingView;
//    private EditText et_old_password, et_new_password, et_verify_password;
//    private TextView tv_already;
//
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_update_new_password;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        setTintManager();
//        initTopBarForBoth("修改登录密碼", getString(R.string.tx_back), "忘记", () ->
//                getOperation().startActivity(LostPasswordActivity.class));
//        catLoadingView = new CatLoadingView();
//        tv_already = findViewById(R.id.tv_already);
//        et_old_password = findViewById(R.id.et_old_password);
//        et_new_password = findViewById(R.id.et_new_password);
//        et_verify_password = findViewById(R.id.et_verify_password);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            IMMLeaks.fixFocusedViewLeak(CustomApplication.getInstance());
//        }
//    }
//
//    @Override
//    public void onResumeAfter() {
//        tv_already.setText(StringUtils.hidePhoneNumber(currentUser.getUsername()));
//        findViewById(btn_submit).setOnClickListener(v -> updateNewPassword());
//    }
//
//    /**
//     * 校验输入合法性和网络状态
//     */
//    private void updateNewPassword() {
//        String old_password = et_old_password.getText().toString();
//        String new_password = et_new_password.getText().toString();
//        String verify_password = et_verify_password.getText().toString();
//        if (TextUtils.isEmpty(old_password)) {
//            et_old_password.requestFocus();
//            CommonUtils.setShakeAnimation(et_old_password);
//            CommonUtils.showToast(R.string.toast_error_password_null);
//            return;
//        }
//        if (!StringUtils.isValidPassword(old_password)) {
//            et_old_password.requestFocus();
//            CommonUtils.setShakeAnimation(et_old_password);
//            CommonUtils.showToast(R.string.toast_error_password_error);
//            return;
//        }
//        if (TextUtils.isEmpty(new_password)) {
//            et_new_password.requestFocus();
//            CommonUtils.setShakeAnimation(et_new_password);
//            CommonUtils.showToast(R.string.toast_error_password_null);
//            return;
//        }
//        if (!StringUtils.isValidPassword(new_password)) {
//            et_new_password.requestFocus();
//            CommonUtils.setShakeAnimation(et_new_password);
//            CommonUtils.showToast(R.string.toast_error_password_error);
//            return;
//        }
//        if (TextUtils.isEmpty(verify_password)) {
//            et_verify_password.requestFocus();
//            CommonUtils.setShakeAnimation(et_verify_password);
//            CommonUtils.showToast(R.string.toast_error_password_null);
//            return;
//        }
//        if (!verify_password.equals(new_password)) {
//            et_verify_password.requestFocus();
//            CommonUtils.setShakeAnimation(et_verify_password);
//            CommonUtils.showToast(R.string.toast_error_password_verify_error);
//            return;
//        }
//        if (!CommonUtils.isNetworkAvailable()) {
//            CommonUtils.showToast(R.string.network_tips);
//            return;
//        }
//        updateInfo(old_password, new_password);
//    }
//
//
//    /**
//     * 修改密码
//     */
//    private void updateInfo(String old_password, String new_password) {
//        User user = new User();
//        toSubscribe(RxFactory.getUserServiceInstance(currentUser.getSessionToken())
//                        .updateUserPassword(currentUser.getObjectId(), StringUtils.getMD5(old_password), StringUtils.getMD5(new_password)),
//                () -> {
//                    user.setNickname(old_password);
//                    catLoadingView.show(getSupportFragmentManager(), TAG);
//                },
//                baseObject -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    finish();
//                },
//                throwable -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast("修改失败");
//                    Logger.e(throwable.getMessage());
//                });
//    }
//
//}
