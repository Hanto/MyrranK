package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.view.ui.WidgetText
import java.util.Locale.US

class StatView(

    val stat: StatUpgradeable,
    assets: SkillAssets,
    val controller: (StatId, NumUpgrades) -> Unit,

): Table()
{
    companion object {

        private val PURPLE_L = Color(163/255f, 170/255f, 255/255f, 1f)
        private val PURPLE_H = Color(110/255f, 110/255f, 211/255f, 1f)
    }

    private val name = WidgetText(stat.name, assets.font12, WHITE) { it.value }
    private val baseBonus = WidgetText(stat.baseBonus, assets.font14, ORANGE ) { it.value.format(1) }
    private val totalBonus = WidgetText(stat.totalBonus(), assets.font14, PURPLE_H) { it.value.format(1) }
    private val upgrades =  WidgetText(stat.upgrades, assets.font10, PURPLE_L) { "${it.actual.value}-${it.maximum.value}"}
    private val upgradeCost = WidgetText(stat.upgradeCost, assets.font10, PURPLE_L) { it.value.format(0) }
    private val bonusPerUpgrade = WidgetText(stat.bonusPerUpgrade, assets.font10, PURPLE_L) { it.value.format(1) }

    init {

        top().left()

        add(name).bottom().left().minWidth(90f)
        add(baseBonus).bottom().right().minWidth(35f)
        add(totalBonus).bottom().right().minWidth(35f)
        add(upgrades).bottom().right().minWidth(30f)
        add(upgradeCost).bottom().right().minWidth(20f)
        add(bonusPerUpgrade).bottom().right().minWidth(20f)
            .minHeight(12f).row()
    }

    fun update() {

        upgrades.setText(stat.upgrades)
        totalBonus.setText(stat.totalBonus())
    }

    private fun Float.format(decimals: Int) =

        "%.${decimals}f".format(US,this)
}
