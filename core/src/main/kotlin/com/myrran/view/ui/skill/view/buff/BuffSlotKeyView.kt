package com.myrran.view.ui.skill.view.buff

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.LIGHT_GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.Event
import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlot
import com.myrran.domain.skills.skills.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.templates.LockType
import com.myrran.domain.utils.observer.Observable
import com.myrran.domain.utils.observer.Observer
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skill.assets.SkillViewAssets

class BuffSlotKeyView(

    private val observable: Observable,
    private val buffSkillSlot: BuffSkillSlot,
    private val assets: SkillViewAssets

): Table(), Observer
{
    private val runesLabel = TextView("${buffSkillSlot.getName()}:", assets.font10, buffSkillSlot.getColor())
    private var keys = buffSkillSlot.lock.openedBy
        .map { TextView("${it.value} ", assets.font10, it.getColor(), 1f) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        observable.addObserver(this)
        top().left()
        setBackground(assets.tableBackgroundLight)
        rebuildTable()
    }

    private fun rebuildTable() {

        val runesRow = Table()
        keys.forEach{ runesRow.add(it) }
        add(runesLabel.align(Align.left)).left().padLeft(1f).padTop(-3f).row()
        add(runesRow).left().padLeft(1f).padTop(-6f).padBottom(-1f)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    override fun update(event: Event) {

        if (event is BuffSkillChangedEvent && event.buffId == buffSkillSlot.id)

        keys = buffSkillSlot.lock.openedBy
            .map { TextView("${it.value} ", assets.font10, it.getColor(), 1f) }
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

            is NoBuffSkill -> GRAY
            is BuffSkill -> LIGHT_GRAY
        }

    private fun LockType.getColor(): Color =

        when (val buffSkill = buffSkillSlot.content)
        {
            is NoBuffSkill -> GRAY
            is BuffSkill -> when (buffSkill.keys.contains(this)) {
                true -> ORANGE
                false -> GRAY
            }
        }
}
