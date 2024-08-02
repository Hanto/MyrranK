package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlot
import com.myrran.domain.skills.skills.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.templates.LockType
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.assets.SkillAssets

class BuffSlotKeyView(

    private val buffSkillSlot: BuffSkillSlot,
    private val assets: SkillAssets

): Table() {

    private val runesLabel = TextView("${buffSkillSlot.getName()}:", assets.font10, buffSkillSlot.getColor())
    private val keys = buffSkillSlot.lock.openedBy
        .associateWith { TextView("${it.value} ", assets.font10, getColor(it), 1f) }

    init {

        top().left()
        val runesRow = Table()

        keys.forEach{ runesRow.add(it.value) }
        add(runesLabel.align(Align.left)).left().padLeft(1f).padTop(-3f).row()
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

    private fun BuffSkillSlot.getName(): String =

        when (val buffSkill = buffSkillSlot.content) {

            is NoBuffSkill -> this.name.value
            is BuffSkill -> buffSkill.name.value
        }

    private fun BuffSkillSlot.getColor(): Color =

        when (val buffSkill = buffSkillSlot.content) {

            is NoBuffSkill -> Color.GRAY
            is BuffSkill -> Color.LIGHT_GRAY
        }

    private fun getColor(lock: LockType): Color =

        when (val buffSkill = buffSkillSlot.content)
        {
            is NoBuffSkill -> Color.GRAY
            is BuffSkill -> when (buffSkill.keys.contains(lock)) {
                true -> Color.ORANGE
                false -> Color.GRAY
            }
        }
}
