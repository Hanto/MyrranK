package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.LIGHT_GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.domain.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.templates.LockTypes
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.assets.SkillAssets

class SlotKeyView(

    private val subSkillSlot: SubSkillSlot,
    private val assets: SkillAssets

): Table() {

    private val runesLabel = TextView("${subSkillSlot.name.value}:", assets.font10, LIGHT_GRAY)
    private val keys = subSkillSlot.lock.openedBy
        .associateWith { TextView(it.value, assets.font10, getColor(it), 1f) }

    init {

        left()
        val runesRow = Table()

        keys.forEach{ runesRow.add(it.value) }
        add(runesLabel.align(Align.left)).padLeft(1f).left().padTop(-3f).row()
        add(runesRow).left().padLeft(1f).padTop(-6f).padBottom(-1f)

        setBackground(assets.tableBackgroundLight)
    }

    fun update() {

        keys.entries
            .sortedBy { it.key.order }
            .forEach{ it.value.setColor(getColor(it.key)) }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getColor(lock: LockTypes): Color =

        when (val subSkill = subSkillSlot.content)
        {
            is NoSubSkill -> GRAY
            is SubSkill -> when (subSkill.keys.contains(lock)) {
                true -> ORANGE
                false -> GRAY
            }
        }
}
