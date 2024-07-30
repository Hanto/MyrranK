package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.view.ui.WidgetText
import java.util.Locale.US
import kotlin.reflect.KClass

class StatView(

    stat: StatUpgradeable,
    font14: BitmapFont,
    font12: BitmapFont,
    font10: BitmapFont

): Table()
{
    companion object {

        private val PURPLE_L = Color(163/255f, 170/255f, 255/255f, 1f)
        private val PURPLE_H = Color(110/255f, 110/255f, 211/255f, 1f)
    }

    val name = WidgetText(stat.name, font12, WHITE) { it.value }
    val baseBonus = WidgetText(stat.baseBonus, font14, ORANGE ) { it.value.format(2) }
    val totalBonus = WidgetText(stat.totalBonus(), font14, PURPLE_H) { it.value.format(2) }
    val upgrades =  WidgetText(stat.upgrades, font10, PURPLE_L) { "${it.actual.value}-${it.maximum.value}"}
    val upgradeCost = WidgetText(stat.upgradeCost, font10, PURPLE_L) { it.value.format(0) }
    val bonusPerUpgrade = WidgetText(stat.bonusPerUpgrade, font10, PURPLE_L) { it.value.format(1) }

    init {

        add(name).bottom().left().minWidth(90f)
        add(baseBonus).bottom().right().minWidth(35f)
        add(totalBonus).bottom().right().minWidth(35f)
        add(upgrades).bottom().right().minWidth(30f)
        add(upgradeCost).bottom().right().minWidth(20f)
        add(bonusPerUpgrade).bottom().right().minWidth(20f)
    }

    private fun Float.format(decimals: Int) =

        "%.${decimals}f".format(US,this)

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
