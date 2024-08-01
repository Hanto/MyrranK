package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.assets.SkillAssets
import java.util.Locale

class SkillIconView(

    val skill: Skill,
    val assets: SkillAssets

): Table()
{
    companion object {

        val MAGENTA: Color = Color(170 / 255f, 70 / 255f, 255 / 255f, 1f)
    }

    private val name = TextView(skill.name, assets.font14, ORANGE) { it.value }
    private val costLabel = TextView("Cost:", assets.font14, WHITE)
    private val cost = TextView(skill.statCost(), assets.font14, MAGENTA) { it.value.format(0) }

    init {

        val iconTable = Table().center().left()

        iconTable.add(name.align(Align.left)).left().row()
        iconTable.setBackground(assets.spellIconBackground)

        val costTable = Table().center()

        costTable.add(costLabel.align(Align.right)).right()
        costTable.add(cost.align(Align.left)).left()
        costTable.setBackground(assets.tableBackgroundLight)

        bottom().left()
        add(iconTable).left()
        add(costTable).left().row()
    }

    fun update() {

        cost.setText(skill.statCost())
    }

    private fun Float.format(decimals: Int) =

        "%.${decimals}f".format(Locale.US,this)
}
