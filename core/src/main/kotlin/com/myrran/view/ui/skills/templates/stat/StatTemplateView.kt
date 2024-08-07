package com.myrran.view.ui.skills.templates.stat

import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.misc.format
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.domain.skills.templates.stat.StatUpgradeableTemplate
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.assets.BASE_BONUS_SIZE
import com.myrran.view.ui.skills.assets.BONUS_PER_UPGRADE_SIZE
import com.myrran.view.ui.skills.assets.NAME_SIZE
import com.myrran.view.ui.skills.assets.PURPLE_DARK
import com.myrran.view.ui.skills.assets.PURPLE_LIGHT
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.assets.TOTAL_BONUS_SIZE
import com.myrran.view.ui.skills.assets.UPGRADECOST_SIZE
import com.myrran.view.ui.skills.assets.UPGRADES_SIZE

class StatTemplateView(

    val stat: StatTemplate,
    private val assets: SkillViewAssets

): Table()
{
    private val name = TextView(stat.name.value, assets.font12, WHITE, 1f)

    private val upgradeCost = if (stat is StatUpgradeableTemplate)
        TextView(stat.upgradeCost, assets.font10, PURPLE_LIGHT, 1f) { it.value.format(0) } else null
    private val bonusPerUpgrade = if (stat is StatUpgradeableTemplate)
        TextView(stat.bonusPerUpgrade, assets.font10, PURPLE_LIGHT, 1f) { it.value.format(1) } else null
    private val maximum = if (stat is StatUpgradeableTemplate)
        TextView(stat.maximum, assets.font10, PURPLE_LIGHT, 1f) { it.value.toString() } else null

    private val baseBonus = if (stat is StatUpgradeableTemplate)
        TextView(stat.baseBonus, assets.font14, PURPLE_DARK, 1f) { it.value.format(1) } else null
    private val baseBonusWithUpgrades = if (stat is StatUpgradeableTemplate)
        TextView(stat, assets.font14, ORANGE, 1f) { (it.baseBonus + it.maximum * it.bonusPerUpgrade).value.format(1) } else
        TextView(stat, assets.font14, ORANGE, 1f) { it.baseBonus.value.format(1) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left().padTop(-3f).padBottom(-3f)

        add(name.align(Align.left)).minWidth(NAME_SIZE)

        add(upgradeCost?.align(Align.center or Align.bottom)).minWidth(BONUS_PER_UPGRADE_SIZE)
        add(bonusPerUpgrade?.align(Align.right or Align.bottom)).minWidth(UPGRADECOST_SIZE)
        add(maximum?.align(Align.center)).minWidth(UPGRADES_SIZE)

        add(baseBonus?.align(Align.right)).minWidth(BASE_BONUS_SIZE)
        add(baseBonusWithUpgrades.align(Align.right)).minWidth(TOTAL_BONUS_SIZE)

        row()
    }
}
