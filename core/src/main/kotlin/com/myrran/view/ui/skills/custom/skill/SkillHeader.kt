package com.myrran.view.ui.skills.custom.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.custom.Skill
import com.myrran.domain.utils.format
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.assets.MAGENTA_EPIC
import com.myrran.view.ui.skills.assets.SkillViewAssets

class SkillHeader(

    val skill: Skill,
    val assets: SkillViewAssets,

): Table()
{
    private val icon = Image(assets.skillIcon)
    private val name = TextView(skill.name, assets.font20, ORANGE, 2f) { it.value }
    private val templateId = TextView(skill.templateId, assets.font14, WHITE, 1f) { it.value }
    private val cost = TextView(skill.totalCost(), assets.font14, MAGENTA_EPIC, 2f) { it.value.format(0) }

    init {

        left()
        setBackground(assets.tableBackgroundLightToDark.tint(Color(0.6f, 0.6f, 0.6f, 0.90f)))
        rebuildTable()
    }

    private fun rebuildTable() {

        val nameCostTable = Table().bottom().left()
        nameCostTable.add(name.align(Align.left)).left()
        nameCostTable.add(cost.align(Align.left)).bottom().left().padLeft(3f)

        val templateNameCostTable = Table().bottom().left()
        templateNameCostTable.padLeft(3f)
        templateNameCostTable.add(templateId.align(Align.left)).left().padBottom(-4f).padBottom(-2f).row()
        templateNameCostTable.add(nameCostTable).padTop(-4f)

        add(icon).left()
        add(templateNameCostTable).left().padTop(-2f).padBottom(-2f).row()
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        cost.setText(skill.totalCost())
    }
}
