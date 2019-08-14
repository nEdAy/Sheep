package cn.neday.sheep.activity

import android.view.KeyEvent
import cn.neday.sheep.R
import cn.neday.sheep.fragment.GoodsFragment
import cn.neday.sheep.fragment.IndexFragment
import cn.neday.sheep.fragment.MeFragment
import cn.neday.sheep.fragment.RankingFragment
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.flyco.dialog.widget.ActionSheetDialog
import com.flyco.tablayout.listener.CustomTabEntity
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * 主页
 *
 * @author nEdAy
 */
class MainActivity : BaseActivity() {

    private var mPressedBackTime = 0L

    override val layoutId = R.layout.activity_main

    override fun initView() {
        val tabEntities = ArrayList<CustomTabEntity>()
        val tabEntitiesArray = resources.getStringArray(R.array.tab_entities_array)
        for (index in 0 until tabEntitiesArray.size) {
            tabEntities.add(TabEntity(tabEntitiesArray[index], iconSelectResIDs[index], iconUnSelectResIDs[index]))
        }
        tl_main_tab.setTabData(
            tabEntities,
            this,
            R.id.fl_main_content,
            arrayListOf(IndexFragment(), RankingFragment(), GoodsFragment(), MeFragment())
        )
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
                0 -> Beta.checkUpgrade()
                1 -> ActivityUtils.startActivity(AboutActivity::class.java)
                2 -> ActivityUtils.finishAllActivities()
            }
            dialog.dismiss()
        }
    }

    internal class TabEntity(
        private val title: String,
        private val selectedIcon: Int,
        private val unSelectedIcon: Int
    ) : CustomTabEntity {

        override fun getTabTitle(): String {
            return title
        }

        override fun getTabSelectedIcon(): Int {
            return selectedIcon
        }

        override fun getTabUnselectedIcon(): Int {
            return unSelectedIcon
        }
    }

    companion object {

        private const val SAFE_PRESSED_BACK_TIME = 2000
        private val iconSelectResIDs = intArrayOf(
            R.drawable.tab_index_select,
            R.drawable.tab_item_select,
            R.drawable.tab_library_select,
            R.drawable.tab_me_select
        )
        private val iconUnSelectResIDs = intArrayOf(
            R.drawable.tab_index_unselect,
            R.drawable.tab_item_unselect,
            R.drawable.tab_library_unselect,
            R.drawable.tab_me_unselect
        )
    }
}