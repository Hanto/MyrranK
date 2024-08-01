package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color.LIGHT_GRAY
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.view.ui.TextView
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

        add(name.align(Align.left)).minWidth(90f)
        add(min.align(Align.right)).minWidth(35f)
        add().minWidth(80f)
        add(max.align(Align.center)).minWidth(35f)
        add(ranks.align(Align.center)).minWidth(30f)
        add(cost.align(Align.center)).minWidth(22f)
        add(bon.align(Align.center)).minWidth(20f)
    }
}
