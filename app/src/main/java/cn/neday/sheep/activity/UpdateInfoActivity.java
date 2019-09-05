//package cn.neday.sheep.activity;
//
//import android.os.Build;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.hwangjr.rxbus.RxBus;
//import com.neday.bomb.CustomApplication;
//import com.neday.bomb.R;
//import com.neday.bomb.StaticConfig;
//import com.neday.bomb.base.BaseOnlineActivity;
//import com.neday.bomb.entity.User;
//import com.neday.bomb.network.RxFactory;
//import com.neday.bomb.util.CommonUtils;
//import com.neday.bomb.util.IMMLeaks;
//import com.neday.bomb.view.loading.CatLoadingView;
//import com.orhanobut.logger.Logger;
//
///**
// * 设置昵称页
// *
// * @author nEdAy
// */
//public class UpdateInfoActivity extends BaseOnlineActivity {
//    private final static String TAG = "UpdateInfoActivity";
//    private CatLoadingView catLoadingView;
//    private TextView tv_update;
//    private final TextWatcher mNicknameWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            int length = s.length();
//            tv_update.setEnabled(length > 0);
//        }
//    };
//    private EditText et_nickname;
//
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_update_info;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        setTintManager();
//        RxBus.get().register(this);
//        initTopBarForLeft("修改昵称", getString(R.string.tx_back));
//        catLoadingView = new CatLoadingView();
//        tv_update = findViewById(R.id.btn_submit);
//        et_nickname = findViewById(R.id.et_nickname);
//        et_nickname.addTextChangedListener(mNicknameWatcher);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            IMMLeaks.fixFocusedViewLeak(CustomApplication.getInstance());
//        }
//    }
//
//    @Override
//    public void onResumeAfter() {
//        tv_update.setOnClickListener(v -> {
//            String nickname = et_nickname.getText().toString();
//            if (nickname.isEmpty() && nickname.equals("")) {
//                CommonUtils.showToast("请填写昵称!");
//                return;
//            }
//            if (!CommonUtils.isNetworkAvailable()) {
//                CommonUtils.showToast(R.string.network_tips);
//                return;
//            }
//            updateInfo(nickname);
//        });
//    }
//
//    /**
//     * 修改资料 updateInfo
//     */
//    private void updateInfo(String nickname) {
//        User user = new User();
//        toSubscribe(RxFactory.getUserServiceInstance(currentUser.getSessionToken())
//                        .updateUser(currentUser.getObjectId(), user),
//                () -> {
//                    user.setNickname(nickname);
//                    catLoadingView.show(getSupportFragmentManager(), TAG);
//                },
//                baseObject -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    //发送事件，通知账户页面修改昵称成功，并返回其值
//                    RxBus.get().post(StaticConfig.ACTION_UPDATE_NICKNAME_SUCCESS_FINISH, user.getNickname());
//                    finish();
//                },
//                throwable -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast("更新失败");
//                    Logger.e(throwable.getMessage());
//                });
//    }
//
//    @Override
//    protected void onDestroy() {
//        RxBus.get().unregister(this);
//        super.onDestroy();
//    }
//
//}
