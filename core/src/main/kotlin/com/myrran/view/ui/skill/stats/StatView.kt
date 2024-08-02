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
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.domain.utils.format
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.BASE_BONUS_SIZE
import com.myrran.view.ui.skill.BONUS_PER_UPGRADE_SIZE
import com.myrran.view.ui.skill.NAME_SIZE
import com.myrran.view.ui.skill.TOTAL_BONUS_SIZE
import com.myrran.view.ui.skill.UPGRADECOST_SIZE
import com.myrran.view.ui.skill.UPGRADES_SIZE
import com.myrran.view.ui.skill.UPGRADE_BAR_SIZE
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.StatController

class StatView(

    val stat: Stat,
    private val assets: SkillAssets,
    private val controller: StatController,

): Table()
{
    companion object {

        private val PURPLE_L = Color(163/255f, 170/255f, 255/255f, 1f)
        private val PURPLE_H = Color(110/255f, 110/255f, 211/255f, 1f)
    }

    private val name = TextView(stat.name.value, assets.font12, WHITE, 1f)
    private val baseBonus = if (stat is StatUpgradeable)
        TextView(stat.baseBonus, assets.font14, PURPLE_H, 1f) { it.value.format(1) } else null
    private val totalBonus = TextView(stat.totalBonus(), assets.font14, ORANGE, 1f) { it.value.format(1) }
    private val upgrades = if (stat is StatUpgradeable)
        TextView(stat.upgrades, assets.font10, PURPLE_L, 1f) { "${it.actual.value}-${it.maximum.value}"} else null
    private val upgradeCost = if (stat is StatUpgradeable)
        TextView(stat.upgradeCost, assets.font10, PURPLE_L, 1f) { it.value.format(0) } else null
    private val bonusPerUpgrade = if (stat is StatUpgradeable)
        TextView(stat.bonusPerUpgrade, assets.font10, PURPLE_L, 1f) { it.value.format(1) } else null
    private val upgradeBar = if (stat is StatUpgradeable)
        UpgradeView(stat, assets, controller)  else null

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left().padTop(-3f).padBottom(-3f)
        reBuildTable()
    }

    private fun reBuildTable() {

        clear()
        add(name.align(Align.left)).minWidth(NAME_SIZE)

        add(upgradeCost?.align(Align.center or Align.bottom)).minWidth(BONUS_PER_UPGRADE_SIZE)
        add(bonusPerUpgrade?.align(Align.center or Align.bottom)).minWidth(UPGRADECOST_SIZE)
        add(upgrades?.align(Align.center)).minWidth(UPGRADES_SIZE)

        add(baseBonus?.align(Align.right)).minWidth(BASE_BONUS_SIZE)
        add(upgradeBar).center().padLeft(2f).padTop(2f).minWidth(UPGRADE_BAR_SIZE)
        add(totalBonus.align(Align.right)).minWidth(TOTAL_BONUS_SIZE)

        row()

        baseBonus?.addListener(createListener(amountToUpgrade = -1))
        totalBonus.addListener(createListener(amountToUpgrade = +1))
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        if (stat is StatUpgradeable)
            upgrades?.setText(stat.upgrades)

        totalBonus.setText(stat.totalBonus())
        upgradeBar?.update()
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
}
