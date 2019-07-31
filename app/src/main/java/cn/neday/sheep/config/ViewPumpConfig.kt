package cn.neday.sheep.config

import cn.neday.sheep.R
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


object ViewPumpConfig {

    private const val FONT_PATH_ALIBABA_PU_HUI_TI_REGULAR = "font/alibaba_pu_hui_ti_regular"

    fun init() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(FONT_PATH_ALIBABA_PU_HUI_TI_REGULAR)
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }
}