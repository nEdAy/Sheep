package cn.neday.sheep.activity

import cn.neday.base.activity.BaseActivity
import cn.neday.base.config.MMKVConfig.IS_FIRST_START_APP
import cn.neday.base.config.MMKVConfig.kv
import cn.neday.sheep.R
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_guide.*

/**
 * 导航页
 *
 * @author nEdAy
 */
class GuideActivity : BaseActivity(R.layout.activity_guide) {

    override fun initView() {
        guide_banner.setIndicatorWidth(6f)
            .setIndicatorHeight(6f)
            .setIndicatorGap(12f)
            .setIndicatorCornerRadius(3.5f)
            .setBarColor(R.color.gray)
            .barPadding(0f, 10f, 0f, 10f)
            .setSource(arrayListOf(R.drawable.guide_img_1, R.drawable.guide_img_2, R.drawable.guide_img_3))
            .startScroll()
        guide_banner.setOnGuideJumpClick {
            kv.encode(IS_FIRST_START_APP, false)
            ActivityUtils.startActivity(MainActivity::class.java)
            ActivityUtils.finishActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()
        guide_banner.goOnScroll()
    }

    override fun onPause() {
        super.onPause()
        guide_banner.pauseScroll()
    }
}
