package com.myrran.view.ui.skill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.StatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlot
import com.myrran.domain.skills.skills.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.BuffSKillController
import com.myrran.view.ui.skill.stats.StatsView

class BuffSkillSlotView(

    private val buffSkillSlot: BuffSkillSlot,
    private val assets: SkillAssets,
    private val controller: BuffSKillController

): Table()
{
    private val buffSlotKeyView = BuffSlotKeyView(buffSkillSlot, assets)
    private var stats = StatsView( { getStats() }, assets, controller.toStatController() )

    init {

        top().left()

        fillTable()

    }

    private fun fillTable() {

        add(buffSlotKeyView).expandY().fillY().left()

        if (buffSkillSlot.content is BuffSkill) {
            add(stats).left()
        }

    }

    fun update(event: SkillEvent) {

        when (event) {

            is StatUpgradedEvent -> stats.update(event)
            is BuffSkillChangedEvent -> Unit
            is SubSkillChangedEvent -> Unit
        }
    }

    private fun getStats(): Collection<Stat> =

        when (val buffSkill = buffSkillSlot.content) {

            is NoBuffSkill -> emptyList()
            is BuffSkill -> buffSkill.stats.getStats()
        }
}
