package com.example.awesomefish.shared

import android.content.Context
import android.graphics.Typeface

object FontManager {

    fun getTypeForFont(context: Context, font: Font): Typeface {
        return Typeface.createFromAsset(context.assets, font.path)
    }

    enum class Font(val path: String) {
        SPACE_QUEST_XJ4O("fonts/SpaceQuest-Xj4o.ttf"),
        SPACE_QUEST_XJ40_ITALLIC("fonts/SpaceQuestItalic-60Rx.ttf"),
        SPACE_QUEST_Y0Y3("fonts/SpaceQuest-yOY3.ttf"),
        GLADIATOR_SPORT("fonts/GladiatorSport-O82O.ttf"),
        GLADIATOR_SPORT_BOLD("fonts/GladiatorSportBold-ZP4q.ttf"),
        GLADIATOR_SPORT_BOLD_ITALLICS("fonts/GladiatorSportBoldItalic-3BWX.ttf"),
        GLADIATOR_SPORT_ITALLIC("fonts/GladiatorSportItalic-xgKj.ttf"),
        SQUIRK("fonts/Squirk-RMvV.ttf")
    }

    object FontSize {
        val VERY_SMALL = FontDesnsity.SMALL().toFloat()
        val SMALL = FontDesnsity.SMALL().toFloat()
        val MEDIUM = FontDesnsity.MEDIUM().toFloat()
        val LARGE = FontDesnsity.LARGE().toFloat()
        val VERY_LARGE = FontDesnsity.VERY_LARGE().toFloat()
        val BIG = FontDesnsity.BIG().toFloat()

    }

    object FontBaseSize {
        const val SMALL = 12
        const val MEDIUM = 17
        const val BIG = 22
        const val LARGE = 33
        const val VERY_LARGE = 40
    }

    object FontMultipyer {
        const val SMALL = 1
        const val MEDIUM = 1.5
        const val LARGE = 3
        const val VERY_LARGE = 4.5
    }

    object FontDesnsity {
        fun SMALL(): Int = when (getDeviceWidth(ViewManager.width)) {
            ScreenSize.Small -> (FontBaseSize.SMALL * FontMultipyer.SMALL)
            ScreenSize.Medium -> (FontBaseSize.SMALL * FontMultipyer.MEDIUM).toInt()
            ScreenSize.Large -> (FontBaseSize.SMALL * FontMultipyer.LARGE)
            ScreenSize.VeryLarge -> (FontBaseSize.SMALL * FontMultipyer.VERY_LARGE).toInt()
            else -> 0
        }

        fun MEDIUM(): Int = when (getDeviceWidth(ViewManager.width)) {
            ScreenSize.Small -> (FontBaseSize.MEDIUM * FontMultipyer.SMALL).toInt()
            ScreenSize.Medium -> (FontBaseSize.MEDIUM * FontMultipyer.MEDIUM).toInt()
            ScreenSize.Large -> (FontBaseSize.MEDIUM * FontMultipyer.LARGE).toInt()
            ScreenSize.VeryLarge -> (FontBaseSize.MEDIUM * FontMultipyer.VERY_LARGE).toInt()
            else -> 0
        }

        fun LARGE(): Int = when (getDeviceWidth(ViewManager.width)) {
            ScreenSize.Small -> (FontBaseSize.VERY_LARGE * FontMultipyer.SMALL).toInt()
            ScreenSize.Medium -> (FontBaseSize.VERY_LARGE * FontMultipyer.MEDIUM).toInt()
            ScreenSize.Large -> (FontBaseSize.VERY_LARGE * FontMultipyer.LARGE).toInt()
            ScreenSize.VeryLarge -> (FontBaseSize.VERY_LARGE * FontMultipyer.VERY_LARGE).toInt()
            else -> 0
        }

        fun BIG(): Int = when (getDeviceWidth(ViewManager.width)) {
            ScreenSize.Small -> (FontBaseSize.BIG * FontMultipyer.SMALL)
            ScreenSize.Medium -> (FontBaseSize.BIG * FontMultipyer.MEDIUM).toInt()
            ScreenSize.Large -> (FontBaseSize.BIG * FontMultipyer.LARGE)
            ScreenSize.VeryLarge -> (FontBaseSize.BIG * FontMultipyer.VERY_LARGE).toInt()
            else -> 0
        }

        fun VERY_LARGE(): Int = when (getDeviceWidth(ViewManager.width)) {
            ScreenSize.Small -> (FontBaseSize.LARGE * FontMultipyer.SMALL)
            ScreenSize.Medium -> (FontBaseSize.LARGE * FontMultipyer.MEDIUM).toInt()
            ScreenSize.Large -> (FontBaseSize.LARGE * FontMultipyer.LARGE)
            ScreenSize.VeryLarge -> (FontBaseSize.LARGE * FontMultipyer.VERY_LARGE).toInt()
            else -> 0
        }
    }
}
