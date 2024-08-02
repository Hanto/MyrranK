package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.domain.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.templates.LockType
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.assets.SkillAssets

class SubSlotKeyView(

    private val subSkillSlot: SubSkillSlot,
    private val assets: SkillAssets

): Table() {

    private val runesLabel = TextView("${subSkillSlot.getName()}:", assets.font10, subSkillSlot.getColor())
    private val keys = subSkillSlot.lock.openedBy
        .associateWith { TextView("${it.value} ", assets.font10, getColor(it), 1f) }

    init {

        left()
        val runesRow = Table()

        keys.forEach{ runesRow.add(it.value) }
        add(runesLabel.align(Align.left)).padLeft(1f).left().padTop(-3f).row()
        add(runesRow).left().padLeft(1f).padTop(-6f).padBottom(-1f)

        setBackground(assets.tableBackgroundLight)
    }

    fun update(event: SkillEvent) {

        keys.entries
            .sortedBy { it.key.order }
            .forEach{ it.value.setColor(getColor(it.key)) }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun SubSkillSlot.getName(): String =

        when (val subSkill = subSkillSlot.content) {

            is NoSubSkill -> this.name.value
            is SubSkill -> subSkill.name.value
        }

    private fun SubSkillSlot.getColor(): Color =

        when (val subSkill = subSkillSlot.content) {

            is NoSubSkill -> Color.GRAY
            is SubSkill -> Color.LIGHT_GRAY
        }

    private fun getColor(lock: LockType): Color =

        when (val subSkill = subSkillSlot.content) {

            is NoSubSkill -> GRAY
            is SubSkill -> when (subSkill.keys.contains(lock)) {
                true -> ORANGE
                false -> GRAY
            }
        }
}
