package com.myrran.view.ui.skills.custom.subskill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.domain.skills.lock.LockType
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.assets.PURPLE_LIGHT
import com.myrran.view.ui.skills.assets.SkillViewAssets

@Suppress("DuplicatedCode")
class SubSlotKeyView(

    private val model: SubSkillSlot,
    private val assets: SkillViewAssets

): Table()
{
    private val runesLabel: TextView<String> = TextView("${model.getName()}:", assets.font10, model.getColor())
    private var keys: List<TextView<String>> = getKeys()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left()
        setBackground(assets.tableBackgroundLight)
        rebuildTable()
    }

    private fun rebuildTable() {

        clear()
        val runesRow = Table()
        keys.forEach{ runesRow.add(it) }
        add(runesLabel.align(Align.left)).padLeft(1f).padTop(-3f).row()
        add(runesRow).left().padLeft(1f).padTop(-6f).padBottom(-1f)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        keys = getKeys()
        rebuildTable()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getKeys() =

        model.lock.openedBy
            .map { TextView("${it.value} ", assets.font10, it.getColor(), 1f) }

    private fun SubSkillSlot.getName(): String =

        when (val subSkill = model.content) {

            is NoSubSkill -> this.name.value
            is SubSkill -> subSkill.name.value
        }

    private fun SubSkillSlot.getColor(): Color =

        when (model.content) {

            is NoSubSkill -> GRAY
            is SubSkill -> ORANGE
        }

    private fun LockType.getColor(): Color =

        when (val subSkill = model.content) {

            is NoSubSkill -> GRAY
            is SubSkill -> when (subSkill.keys.contains(this)) {
                true -> PURPLE_LIGHT
                false -> GRAY
            }
        }
}
