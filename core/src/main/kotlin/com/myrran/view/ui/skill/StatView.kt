package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.view.ui.WidgetText
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.StatController
import java.util.Locale.US

class StatView(

    val stat: StatUpgradeable,
    assets: SkillAssets,
    controller: StatController,

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
    private val upgradeBar = UpgradeView(stat, assets, controller)

    init {

        baseBonus.setAlignment(Align.right)
        totalBonus.setAlignment(Align.right)
        upgrades.setAlignment(Align.right)
        upgradeCost.setAlignment(Align.right or Align.bottom)
        bonusPerUpgrade.setAlignment(Align.right or Align.bottom)

        top().left().padTop(-4f).padBottom(-4f)

        add(name).minWidth(90f)
        add(baseBonus).minWidth(35f)
        add(upgradeBar).center().padTop(2f)
        add(totalBonus).minWidth(35f)
        add(upgrades).minWidth(30f)
        add(upgradeCost).minWidth(20f)
        add(bonusPerUpgrade).minWidth(20f).row()
    }

    fun update() {

        upgrades.setText(stat.upgrades)
        totalBonus.setText(stat.totalBonus())
        upgradeBar.update()
    }

    private fun Float.format(decimals: Int) =

        "%.${decimals}f".format(US,this)
}
