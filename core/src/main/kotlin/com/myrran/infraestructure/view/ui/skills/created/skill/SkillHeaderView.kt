package com.myrran.infraestructure.view.ui.skills.created.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.misc.format
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.infraestructure.assets.PURPLE_LIGHT
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.controller.SkillController
import com.myrran.infraestructure.view.ui.misc.TextFieldView
import com.myrran.infraestructure.view.ui.misc.TextView

class SkillHeaderView(

    val skill: Skill,
    val assets: SkillViewAssets,
    val controller: SkillController

): Table()
{
    private val icon = Image(assets.skillIcon)
    private val name = TextFieldView(skill.custonName, assets.font20, ORANGE, 2f, { controller.changeName(it) }, { it.value })
    private val description = TextView(skill.name, assets.font14, WHITE, 1f) { it.value.uppercase() }
    private val cost = TextView(skill.totalCost(), assets.font20, PURPLE_LIGHT, 2f) { it.value.format(0) }

    init {

        left()
        setBackground(assets.tableBackgroundLightToDark.tint(Color(0.6f, 0.6f, 0.6f, 0.90f)))
        rebuildTable()
    }

    private fun rebuildTable() {

        val lowerTable = Table().bottom().left()
        lowerTable.add(name.align(Align.left)).left()
        lowerTable.add(cost.align(Align.right)).bottom().expandX().fillX().padLeft(3f).padRight(6f)

        val mainTable = Table().bottom().left()
        mainTable.padLeft(3f)
        mainTable.add(description.align(Align.left)).left().padBottom(-4f).padBottom(-2f).row()
        mainTable.add(lowerTable).expandX().fillX().padTop(-4f)

        add(icon).left()
        add(mainTable).left().expandX().fillX().padTop(-2f).padBottom(-2f).row()
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        cost.setText(skill.totalCost())
    }
}
