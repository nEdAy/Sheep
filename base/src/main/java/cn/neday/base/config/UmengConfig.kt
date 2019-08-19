package cn.neday.base.config

import cn.neday.base.BuildConfig
import com.blankj.utilcode.util.Utils
import com.umeng.commonsdk.UMConfigure


object UmengConfig {

    private const val UMENG_APP_KEY = "5d087b810cafb25c1d000e9f"
    private const val UMENG_PUSH_MESSAGE_SECRET = "5df990afe466c1bd066b06d3a2fd225d"

    fun init() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(
            Utils.getApp(),
            UMENG_APP_KEY,
            "Channel", // TODO: chanel
            UMConfigure.DEVICE_TYPE_PHONE,
            UMENG_PUSH_MESSAGE_SECRET
        )
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(!BuildConfig.DEBUG)
    }
}