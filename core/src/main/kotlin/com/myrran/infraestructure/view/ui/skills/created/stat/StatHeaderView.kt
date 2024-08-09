package com.myrran.infraestructure.view.ui.skills.created.stat

import com.badlogic.gdx.graphics.Color.LIGHT_GRAY
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.infraestructure.assets.BASE_BONUS_SIZE
import com.myrran.infraestructure.assets.BONUS_PER_UPGRADE_SIZE
import com.myrran.infraestructure.assets.NAME_SIZE
import com.myrran.infraestructure.assets.TOTAL_BONUS_SIZE
import com.myrran.infraestructure.assets.UPGRADECOST_SIZE
import com.myrran.infraestructure.assets.UPGRADES_SIZE
import com.myrran.infraestructure.assets.UPGRADE_BAR_SIZE
import com.myrran.infraestructure.view.ui.misc.TextView
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets

class StatHeaderView(

    assets: SkillViewAssets

): Table()
{
    private val name = TextView("Name", assets.font14, LIGHT_GRAY)
    private val min = TextView("Min", assets.font14, LIGHT_GRAY)
    private val max = TextView("Max", assets.font14, LIGHT_GRAY)
    private val ranks = TextView("ranks", assets.font14, LIGHT_GRAY)
    private val cost = TextView("cost", assets.font14, LIGHT_GRAY)
    private val bon = TextView("bon", assets.font14, LIGHT_GRAY)

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
