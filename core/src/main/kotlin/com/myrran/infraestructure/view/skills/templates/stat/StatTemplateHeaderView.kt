package com.myrran.infraestructure.view.skills.templates.stat

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.infraestructure.assets.BASE_BONUS_SIZE
import com.myrran.infraestructure.assets.BONUS_PER_UPGRADE_SIZE
import com.myrran.infraestructure.assets.NAME_SIZE
import com.myrran.infraestructure.assets.TOTAL_BONUS_SIZE
import com.myrran.infraestructure.assets.UPGRADECOST_SIZE
import com.myrran.infraestructure.assets.UPGRADES_SIZE
import com.myrran.infraestructure.view.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.TextView

class StatTemplateHeaderView(

    assets: SkillViewAssets

): Table()
{
    private val name = TextView("Name", assets.font14, Color.LIGHT_GRAY)
    private val min = TextView("Min", assets.font14, Color.LIGHT_GRAY)
    private val max = TextView("Max", assets.font14, Color.LIGHT_GRAY)
    private val ranks = TextView("ranks", assets.font14, Color.LIGHT_GRAY)
    private val cost = TextView("cost", assets.font14, Color.LIGHT_GRAY)
    private val bon = TextView("bon", assets.font14, Color.LIGHT_GRAY)

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left().padTop(-3f).padBottom(-3f)

        add(name.align(Align.left)).minWidth(NAME_SIZE)

        add(cost.align(Align.center)).minWidth(UPGRADECOST_SIZE)
        add(bon.align(Align.center)).minWidth(BONUS_PER_UPGRADE_SIZE)
        add(ranks.align(Align.center)).minWidth(UPGRADES_SIZE)

        add(min.align(Align.center)).minWidth(BASE_BONUS_SIZE)
        add(max.align(Align.center)).minWidth(TOTAL_BONUS_SIZE)
    }
}
