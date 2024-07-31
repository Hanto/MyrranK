package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color.LIGHT_GRAY
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.view.ui.WidgetText
import com.myrran.view.ui.skill.assets.SkillAssets

class StatHeaderView(

    assets: SkillAssets

): Table()
{
    private val name = WidgetText("Name", assets.font12, LIGHT_GRAY)
    private val min = WidgetText("Min", assets.font10, LIGHT_GRAY)
    private val max = WidgetText("Max", assets.font10, LIGHT_GRAY)
    private val ranks = WidgetText("ranks", assets.font10, LIGHT_GRAY)
    private val cost = WidgetText("cost", assets.font10, LIGHT_GRAY)
    private val bon = WidgetText("bon", assets.font10, LIGHT_GRAY)

    init {

        top().left().padTop(-4f).padBottom(-4f)

        add(name.align(Align.left)).minWidth(90f)
        add(min.align(Align.right)).minWidth(35f)
        add().minWidth(80f)
        add(max.align(Align.right)).minWidth(35f)
        add(ranks.align(Align.center)).minWidth(30f)
        add(cost.align(Align.center)).minWidth(20f)
        add(bon.align(Align.center)).minWidth(20f)
    }
}
