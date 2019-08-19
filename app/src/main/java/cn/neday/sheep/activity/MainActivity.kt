package cn.neday.sheep.activity

import android.view.KeyEvent
import cn.neday.base.activity.BaseVMActivity
import cn.neday.base.config.BuglyConfig
import cn.neday.sheep.R
import cn.neday.sheep.fragment.IndexFragment
import cn.neday.sheep.fragment.MeFragment
import cn.neday.sheep.fragment.NineGoodsFragment
import cn.neday.sheep.fragment.RankingGoodsFragment
import cn.neday.sheep.viewmodel.MainViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.flyco.dialog.widget.ActionSheetDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页
 *
 * @author nEdAy
 */
class MainActivity : BaseVMActivity<MainViewModel>(R.layout.activity_main) {

    private var mPressedBackTime = 0L

    override fun initView() {
        nav_main.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        nav_main.selectedItemId = R.id.navigation_index
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        loadFragment(item.itemId)
        true
    }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        val fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.navigation_index -> IndexFragment()
            R.id.navigation_ranking -> RankingGoodsFragment()
            R.id.navigation_nine -> NineGoodsFragment()
            R.id.navigation_me -> MeFragment()
            else -> null
        }
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            if (mViewModel.mLastActiveFragmentTag != null) {
                val lastFragment = supportFragmentManager.findFragmentByTag(mViewModel.mLastActiveFragmentTag)
                if (lastFragment != null)
                    transaction.hide(lastFragment)
            }
            if (!fragment.isAdded) {
                transaction.add(R.id.fl_main, fragment, tag)
            } else {
                transaction.show(fragment)
            }
            transaction.commit()
            mViewModel.mLastActiveFragmentTag = tag
        }
    }

    /**
     * 截获Back键动作 连续触发两次Back键则退出
     */
    override fun onBackPressed() {
        if (mPressedBackTime + SAFE_PRESSED_BACK_TIME > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            ToastUtils.showShort(getString(R.string.tx_pressed_back_hint))
        }
        mPressedBackTime = System.currentTimeMillis()
    }

    /**
     * 截获Menu键动作 弹出Menu菜单
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            showActionSheet()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showActionSheet() {
        val menuItemArray = resources.getStringArray(R.array.menu_items_array)
        val dialog = ActionSheetDialog(this, menuItemArray, null)
        dialog.isTitleShow(false).layoutAnimation(null).show()
        dialog.setOnOperItemClickL { _, _, position, _ ->
            when (position) {
                0 -> BuglyConfig.checkUpgrade()
                1 -> ActivityUtils.startActivity(AboutActivity::class.java)
                2 -> ActivityUtils.finishAllActivities()
            }
            dialog.dismiss()
        }
    }

    companion object {

        private const val SAFE_PRESSED_BACK_TIME = 2000
    }
}