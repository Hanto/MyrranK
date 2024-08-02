package com.myrran.view.ui.skills.custom.buff

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.Event
import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.templates.LockType
import com.myrran.domain.utils.observer.Observable
import com.myrran.domain.utils.observer.Observer
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.assets.PURPLE_LIGHT
import com.myrran.view.ui.skills.assets.SkillViewAssets

@Suppress("DuplicatedCode")
class BuffSlotKeyView(

    private val observable: Observable,
    private val buffSkillSlot: BuffSkillSlot,
    private val assets: SkillViewAssets

): Table(), Observer
{
    private val runesLabel = TextView("${buffSkillSlot.getName()}:", assets.font10, buffSkillSlot.getColor())
    private var keys = buffSkillSlot.lock.openedBy.map { TextView("${it.value} ", assets.font10, it.getColor(), 1f) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        observable.addObserver(this)
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

    override fun propertyChange(event: Event) {

        if (event is BuffSkillChangedEvent && event.buffId == buffSkillSlot.id) {

            keys = buffSkillSlot.lock.openedBy.map { TextView("${it.value} ", assets.font10, it.getColor(), 1f) }
            rebuildTable()
        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun BuffSkillSlot.getName(): String =

        when (val buffSkill = buffSkillSlot.content) {

            is NoBuffSkill -> this.name.value
            is BuffSkill -> buffSkill.name.value
        }

    private fun BuffSkillSlot.getColor(): Color =

        when (buffSkillSlot.content) {

            is NoBuffSkill -> GRAY
            is BuffSkill -> ORANGE
        }

    private fun LockType.getColor(): Color =

        when (val buffSkill = buffSkillSlot.content)
        {
            is NoBuffSkill -> GRAY
            is BuffSkill -> when (buffSkill.keys.contains(this)) {
                true -> PURPLE_LIGHT
                false -> GRAY
            }
        }
}
