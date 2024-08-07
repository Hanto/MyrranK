package com.myrran.infraestructure.view.ui.skills.created.stat

import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.myrran.controller.StatController
import com.myrran.domain.misc.format
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatUpgradeable
import com.myrran.infraestructure.assets.BASE_BONUS_SIZE
import com.myrran.infraestructure.assets.BONUS_PER_UPGRADE_SIZE
import com.myrran.infraestructure.assets.NAME_SIZE
import com.myrran.infraestructure.assets.PURPLE_DARK
import com.myrran.infraestructure.assets.PURPLE_LIGHT
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.assets.TOTAL_BONUS_SIZE
import com.myrran.infraestructure.assets.UPGRADECOST_SIZE
import com.myrran.infraestructure.assets.UPGRADES_SIZE
import com.myrran.infraestructure.assets.UPGRADE_BAR_SIZE
import com.myrran.infraestructure.view.ui.misc.TextView

class StatView(

    val model: Stat,
    private val assets: SkillViewAssets,
    private val controller: StatController,

): Table()
{
    private val name = TextView(model.name.value, assets.font12, WHITE, 1f)

    private val upgradeCost = if (model is StatUpgradeable)
        TextView(model.upgradeCost, assets.font10, PURPLE_LIGHT, 1f) { it.value.format(0) } else null
    private val bonusPerUpgrade = if (model is StatUpgradeable)
        TextView(model.bonusPerUpgrade, assets.font10, PURPLE_LIGHT, 1f) { it.value.format(1) } else null
    private val upgrades = if (model is StatUpgradeable)
        TextView(model.upgrades, assets.font10, PURPLE_LIGHT, 1f) { "${it.actual.value}-${it.maximum.value}"} else null

    private val baseBonus = if (model is StatUpgradeable)
        TextView(model.baseBonus, assets.font14, PURPLE_DARK, 1f) { it.value.format(1) } else null
    private val upgradeBar = if (model is StatUpgradeable)
        UpgradeView(model, assets, controller)  else null
    private val totalBonus = TextView(model.totalBonus(), assets.font14, ORANGE, 1f) { it.value.format(1) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left().padTop(-3f).padBottom(-3f)
        reBuildTable()
    }

    private fun reBuildTable() {

        add(name.align(Align.left)).minWidth(NAME_SIZE)

        add(upgradeCost?.align(Align.center or Align.bottom)).minWidth(BONUS_PER_UPGRADE_SIZE)
        add(bonusPerUpgrade?.align(Align.right or Align.bottom)).minWidth(UPGRADECOST_SIZE)
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

        if (model is StatUpgradeable)
            upgrades?.setText(model.upgrades)

        totalBonus.setText(model.totalBonus())
        upgradeBar?.update()
    }

    // LISTENER:
    //--------------------------------------------------------------------------------------------------------

    private fun createListener(amountToUpgrade: Int) = object : ClickListener() {

        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {

            val numUpgrades = if (button == Buttons.RIGHT) amountToUpgrade*2 else amountToUpgrade
            controller.upgrade(model.id, NumUpgrades(numUpgrades))
            return true
        }
    }
}
