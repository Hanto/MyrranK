package com.myrran.infraestructure.view.ui.skills.created.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.skill.SkillName
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.controller.SkillController
import com.myrran.infraestructure.view.ui.misc.TextView
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.misc.UIClickListener.Button.RIGHT_BUTTON

class SkillSlotKeyView(

    private val model: Skill,
    private val assets: SkillViewAssets,
    private val controller: SkillController

): Table()
{
    private val label: TextView<SkillName> = TextView(model.name, assets.font14, Color.ORANGE, 1f) { it.value }

    init {

        touchable = Touchable.enabled
        addListener(UIClickListener(RIGHT_BUTTON) { controller.removeSkill() })
        top().center()
        setBackground(assets.tableBackgroundLight)
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()
        val runesRow = Table()
        add(label.align(Align.center)).padLeft(1f).padTop(-3f).row()
        add(runesRow).padLeft(1f).padTop(-6f).padBottom(-1f)
    }
}
