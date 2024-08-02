package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.LIGHT_GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.domain.utils.format
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.assets.SkillAssets

class SkillIconView(

    private val skill: Skill,
    assets: SkillAssets

): Table()
{
    companion object {

        val MAGENTA: Color = Color(170 / 255f, 70 / 255f, 255 / 255f, 1f)
    }

    private val name = TextView(skill.name, assets.font14, ORANGE, 1f) { it.value }
    private val costLabel = TextView("Cost:", assets.font14, LIGHT_GRAY, 1f)
    private val cost = TextView(skill.statCost(), assets.font14, MAGENTA, 1f) { it.value.format(0) }

    init {

        val iconTable = Table().center()

        iconTable.add(name.align(Align.center)).center().row()
        iconTable.setBackground(assets.tableBackgroundLight)

        val costTable = Table().center()

        costTable.add(costLabel.align(Align.right)).right()
        costTable.add(cost.align(Align.left)).left()
        costTable.setBackground(assets.tableBackgroundLight)

        bottom().left()
        add(iconTable).left()
        add(costTable).left().row()
    }

    fun update(event: SkillEvent) {

        cost.setText(skill.statCost())
    }
}
