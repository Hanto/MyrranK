package com.myrran.view.ui.skills.custom.subskill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.SubSkillController
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.skills.custom.subskill.SubSkill
import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.domain.skills.custom.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.view.ui.skills.SkillViewFactory
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.buff.BuffSkillSlotView
import com.myrran.view.ui.skills.custom.stat.StatsView

class SubSkillSlotView(

    private val subSkillSlot: SubSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: SubSkillController,
    private val factory: SkillViewFactory,

): Table()
{
    private val subSlotKeyView: SubSlotKeyView = SubSlotKeyView(subSkillSlot, assets)
    private var stats: StatsView = StatsView( { getStats() }, assets, controller)
    var buffSlots: Map<BuffSkillSlotId, BuffSkillSlotView> = createBuffSkillSlotViews()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        right()
        setBackground(assets.tableBackgroundDark)
        rebuildTable()
    }

    private fun rebuildTable() {

        val keyAndStatsTable = Table().left()
        if (subSkillSlot.content is SubSkill)
            keyAndStatsTable.add(stats).left()

        keyAndStatsTable.add(subSlotKeyView.left())
        keyAndStatsTable.row()

        add(keyAndStatsTable).left().row()
        buffSlots.values.forEach{ add(it).right().row() }
    }

    fun update(event: SubSkillStatUpgradedEvent) {

        stats.update(event.statId)
    }

    fun update() {

        subSlotKeyView.update()
        buffSlots.values.forEach{ factory.disposeBuffSlotView(it) }
        buffSlots = createBuffSkillSlotViews()
        rebuildTable()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createBuffSkillSlotViews(): Map<BuffSkillSlotId, BuffSkillSlotView> =

        when (val subSkill = subSkillSlot.content) {

            is NoSubSkill -> emptyMap()
            is SubSkill -> subSkill.getBuffSkillSlots()
                .associate { it.id to factory.createBuffSlotView(it, controller.toBuffSkillController(it)) }
        }

    private fun getStats(): Collection<Stat> =

        when (val subSkill = subSkillSlot.content) {

            NoSubSkill -> emptyList()
            is SubSkill -> subSkill.stats.getStats()
        }
}
