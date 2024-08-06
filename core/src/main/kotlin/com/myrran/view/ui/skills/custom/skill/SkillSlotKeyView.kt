package com.myrran.view.ui.skills.custom.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.custom.Skill
import com.myrran.domain.skills.custom.skill.SkillName
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.assets.SkillViewAssets

class SkillSlotKeyView(

    private val model: Skill,
    private val assets: SkillViewAssets

): Table()
{
    private val label: TextView<SkillName> = TextView(model.name, assets.font12, Color.ORANGE, 1f) { it.value }

    init {

        touchable = Touchable.enabled
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
