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
    assets: SkillAssets

): Table()
{
    companion object {

        val MAGENTA: Color = Color(170 / 255f, 70 / 255f, 255 / 255f, 1f)
    }

    private val background = assets.spellIconBackground
    private val name = TextView(skill.name, assets.font12, ORANGE, 0f) { it.value }
    private val extra = TextView("Spell", assets.font12, WHITE, 0f)
    private val cost = TextView(skill.statCost(), assets.font12, MAGENTA, 0f) { it.value.format(0) }

    init {

        val tableRow = Table().bottom().left()

        tableRow.add(extra.align(Align.left))
        tableRow.add(cost.align(Align.right)).expand().right().padBottom(-7f).padRight(-2f)

        bottom().left()

        add(name.align(Align.left)).left().row()
        add(tableRow).left().expand().fillX().row()

        setBackground(background)
    }

    public fun update() {

        cost.setText(skill.statCost())
    }

    private fun Float.format(decimals: Int) =

        "%.${decimals}f".format(Locale.US,this)
}
