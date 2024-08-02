package com.myrran.view.ui.skill.stats

import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.StatController
import java.util.Locale.US

class StatView(

    val stat: StatUpgradeable,
    assets: SkillAssets,
    val controller: StatController,

): Table()
{
    companion object {

        private val PURPLE_L = Color(163/255f, 170/255f, 255/255f, 1f)
        private val PURPLE_H = Color(110/255f, 110/255f, 211/255f, 1f)
    }

    private val name = TextView(stat.name, assets.font12, WHITE, 1f) { it.value }
    private val baseBonus = TextView(stat.baseBonus, assets.font14, PURPLE_H, 1f) { it.value.format(1) }
    private val totalBonus = TextView(stat.totalBonus(), assets.font14, ORANGE, 1f) { it.value.format(1) }
    private val upgrades =  TextView(stat.upgrades, assets.font10, PURPLE_L, 1f) { "${it.actual.value}-${it.maximum.value}"}
    private val upgradeCost = TextView(stat.upgradeCost, assets.font10, PURPLE_L, 1f) { it.value.format(0) }
    private val bonusPerUpgrade = TextView(stat.bonusPerUpgrade, assets.font10, PURPLE_L, 1f) { it.value.format(1) }
    private val upgradeBar = UpgradeView(stat, assets, controller)

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left().padTop(-3f).padBottom(-3f)

        add(name.align(Align.left)).minWidth(90f)

        add(upgradeCost.align(Align.center or Align.bottom)).minWidth(22f)
        add(bonusPerUpgrade.align(Align.center or Align.bottom)).minWidth(22f)
        add(upgrades.align(Align.center)).minWidth(40f)

        add(baseBonus.align(Align.right)).minWidth(35f)
        add(upgradeBar).center().padLeft(2f).padTop(2f).minWidth(80f)
        add(totalBonus.align(Align.right)).minWidth(35f)

        row()

        baseBonus.addListener(createListener(amountToUpgrade = -1))
        totalBonus.addListener(createListener(amountToUpgrade = +1))
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        upgrades.setText(stat.upgrades)
        totalBonus.setText(stat.totalBonus())
        upgradeBar.update()
    }

    // LISTENER:
    //--------------------------------------------------------------------------------------------------------

    private fun createListener(amountToUpgrade: Int) = object : ClickListener() {

        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {

            val numUpgrades = if (button == Buttons.RIGHT) amountToUpgrade*2 else amountToUpgrade
            controller.upgrade.invoke(stat.id, NumUpgrades(numUpgrades))
            return true
        }
    }

    private fun Float.format(decimals: Int) =

        "%.${decimals}f".format(US,this)
}
