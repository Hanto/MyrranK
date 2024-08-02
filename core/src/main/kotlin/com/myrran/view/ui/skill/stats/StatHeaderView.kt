package com.myrran.view.ui.skill.stats

import com.badlogic.gdx.graphics.Color.LIGHT_GRAY
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.BASE_BONUS_SIZE
import com.myrran.view.ui.skill.BONUS_PER_UPGRADE_SIZE
import com.myrran.view.ui.skill.NAME_SIZE
import com.myrran.view.ui.skill.TOTAL_BONUS_SIZE
import com.myrran.view.ui.skill.UPGRADECOST_SIZE
import com.myrran.view.ui.skill.UPGRADES_SIZE
import com.myrran.view.ui.skill.UPGRADE_BAR_SIZE
import com.myrran.view.ui.skill.assets.SkillAssets

class StatHeaderView(

    assets: SkillAssets

): Table()
{
    private val name = TextView("Name", assets.font12, LIGHT_GRAY)
    private val min = TextView("Min", assets.font10, LIGHT_GRAY)
    private val max = TextView("Max", assets.font10, LIGHT_GRAY)
    private val ranks = TextView("ranks", assets.font10, LIGHT_GRAY)
    private val cost = TextView("cost", assets.font10, LIGHT_GRAY)
    private val bon = TextView("bon", assets.font10, LIGHT_GRAY)

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left().padTop(-3f).padBottom(-3f)

        add(name.align(Align.left)).minWidth(NAME_SIZE)

        add(cost.align(Align.center)).minWidth(UPGRADECOST_SIZE)
        add(bon.align(Align.center)).minWidth(BONUS_PER_UPGRADE_SIZE)
        add(ranks.align(Align.center)).minWidth(UPGRADES_SIZE)

        add(min.align(Align.center)).minWidth(BASE_BONUS_SIZE)
        add().minWidth(UPGRADE_BAR_SIZE)
        add(max.align(Align.center)).minWidth(TOTAL_BONUS_SIZE)
    }
}
