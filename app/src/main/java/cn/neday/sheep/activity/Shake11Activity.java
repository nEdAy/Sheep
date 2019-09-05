//package cn.neday.sheep.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.TextView;
//import cn.neday.sheep.R;
//import cn.neday.alibaba.AliTradeHelper;
//import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
//import io.reactivex.Observable;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 天天摇页
// *
// * @author nEdAy
// */
//public class ShakeActivity extends BaseActivity {
//    private final static String TAG = "ShakeActivity";
//    private SensorManager sensorManager;
//    private ImageView iv_shake, iv_cancel_voice;
//    private TextView tv_shake_times;
//    private boolean isShakeVoice;
//    private Integer shakeTimes;
//    private String currentUserId;
//    private boolean isRunNow, isNotFirst;
//    /**
//     * 重力感应监听
//     */
//    private final SensorEventListener sensorEventListener = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//            // 传感器信息改变时执行该方法
//            float[] values = event.values;
//            float x = values[0]; // x轴方向的重力加速度，向右为正
//            float y = values[1]; // y轴方向的重力加速度，向前为正
//            float z = values[2]; // z轴方向的重力加速度，向上为正
//            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
//            int value = 12;// 三星 i9250怎么晃都不会超过20
//            if ((Math.abs(x) > value || Math.abs(y) > value || Math
//                    .abs(z) > value)) {
//                beginShake();
//            }
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    };
//

//        iv_shake = findViewById(R.id.iv_shake);
//        iv_cancel_voice = findViewById(R.id.iv_cancel_voice);
//        tv_shake_times = findViewById(R.id.tv_shake_times);
//        mApplication = CustomApplication.getInstance();
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        catLoadingView = new CatLoadingView();
//        findViewById(R.id.tv_shake_history).setOnClickListener(v -> {
//            if (currentUser != null) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, CreditsHistoryActivity.class);
//                intent.putExtra("userId", currentUser.getObjectId());
//                startActivity(intent);
//            }
//        });
//        SharedPreferencesHelper sharedPreferencesHelper = mApplication.getSpHelper();
//        isShakeVoice = sharedPreferencesHelper.isAllowShakeVoice();
//        iv_cancel_voice.setImageResource(isShakeVoice ? R.drawable.ic_voice_open : R.drawable.ic_voice_close);
//        findViewById(R.id.rl_shake_voice).setOnClickListener(v -> {
//            if (isShakeVoice) {
//                iv_cancel_voice.setImageResource(R.drawable.ic_voice_close);
//            } else {
//                iv_cancel_voice.setImageResource(R.drawable.ic_voice_open);
//            }
//            isShakeVoice = !isShakeVoice;
//            sharedPreferencesHelper.setAllowShakeVoiceEnable(isShakeVoice);
//        });
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (sensorManager != null) {// 注册监听器
//            try {
//                //由于有的手机硬件并不支持加速度感应功能，主要有两种表现形式：
//                // 1、SensorManager.getDefaultSensor(int type)当传入参数为Sensor.TYPE_ACCELEROMETER时返回为空；
//                // 2、SensorEventListener监听器中的接收重力感应值的onSensorChanged()每次传入的值均为0；
//                // 因此不能实现程序想要实现的加速度感应功能。
//                // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
//                sensorManager.registerListener(sensorEventListener,
//                        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                        SensorManager.SENSOR_DELAY_NORMAL);
//            } catch (Exception ignored) {
//            }
//        }
//    }
//
//    @Override
//    public void onResumeAfter() {
//        if (!isNotFirst) {
//            isNotFirst = true;
//            currentUserId = currentUser.getObjectId();
//            getUserShakeTimes(currentUserId);
//        }
//        iv_shake.setOnClickListener(v -> beginShake());
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        cleanShakeAnim();
//    }
//
//    /**
//     * 准备摇一摇
//     */
//    private void beginShake() {
//        if (isRunNow) {
//            return;
//        }
//        isRunNow = true;
//        if (isShakeVoice) {
//            MediaPlayer mMediaPlayer = mApplication.getMediaPlayer();
//            mMediaPlayer.start();
//        }
//        CommonUtils.Vibrate(this, 300);
//        iv_shake.setImageResource(R.drawable.shake_image);
//        iv_shake.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
//        Observable.timer(2, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(aLong -> {
//                    if (shakeTimes == null || shakeTimes.equals(0)) {
//                        getUserShakeTimes(currentUserId);
//                    } else {
//                        startShake();
//                    }
//                });
//    }
//
//    /**
//     * 开始摇一摇
//     */
//    private void startShake() {
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//                        .shake(currentUserId),
//                () ->
//                        catLoadingView.show(getSupportFragmentManager(), TAG),
//                change -> {
//                    cleanShakeAnim();
//                    catLoadingView.dismissAllowingStateLoss();
//                    if (change == -1) { //标识 -1 为摇一摇次数用尽
//                        CommonUtils.showToast("当日摇一摇机会已经用完咯");
//                        shakeTimes = 0;
//                    } else {
//                        shakeTimes = shakeTimes - 1;
//                        Intent intent = new Intent();
//                        intent.setClass(mContext, ShakeResultShowActivity.class);
//                        intent.putExtra("change", change);
//                        startActivity(intent);
//                    }
//                    tv_shake_times.setText(String.format(getResources().getString(R.string.shake_times), shakeTimes));
//                    isRunNow = false;
//                },
//                throwable -> {
//                    cleanShakeAnim();
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast("天天摇失败");
//                    isRunNow = false;
//                    Logger.e(throwable.getMessage());
//                });
//    }
//
//    /**
//     * 获取用户摇一摇次数
//     *
//     * @param currentUserId 当前用户Id
//     */
//    private void getUserShakeTimes(String currentUserId) {
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//                        .getUser(currentUserId, "_User[shake_times]")
//                        .map(User::getShake_times),
//                () ->
//                        catLoadingView.show(getSupportFragmentManager(), TAG),
//                shakeTimes -> {
//                    isRunNow = false;
//                    catLoadingView.dismissAllowingStateLoss();
//                    tv_shake_times.setText(String.format(getResources().getString(R.string.shake_times), shakeTimes));
//                    this.shakeTimes = shakeTimes;
//                    if (shakeTimes <= 0) {
//                        CommonUtils.showToast("当日摇一摇机会已经用完咯");
//                        cleanShakeAnim();
//                    }
//                },
//                throwable -> {
//                    isRunNow = false;
//                    catLoadingView.dismissAllowingStateLoss();
//                    Logger.e(throwable.getMessage());
//                    cleanShakeAnim();
//                    CommonUtils.showToast("获取用户口袋币失败");
//                });
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (sensorManager != null) {// 取消监听器
//            sensorManager.unregisterListener(sensorEventListener);
//        }
//    }
//
//    /**
//     * 清除摇一摇动画
//     */
//    private void cleanShakeAnim() {
//        iv_shake.setImageResource(R.drawable.shake_original_image);
//        iv_shake.clearAnimation();
//    }
//       if (change > 0) {
//               iv_shake.setImageResource(R.drawable.shake_luck);
//               tv_shake.setText("中奖");
//               tv_title.setText("中奖详情");
//               tv_value.setText("恭喜摇到" + change + "个口袋币，完胜" + (change + 4) + "%的口袋用户！再接再厉哦！");
//               } else {
//               iv_shake.setImageResource(R.drawable.shake_unluck);
//               tv_shake.setText("换个姿势，再来一次");
//               tv_title.setText("没有中奖");
//               tv_value.setText("不增不减，再来一次");
//               }
//}
