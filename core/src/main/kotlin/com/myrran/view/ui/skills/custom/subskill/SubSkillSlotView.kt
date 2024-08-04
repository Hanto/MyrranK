package com.myrran.view.ui.skills.custom.subskill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.SubSkillController
import com.myrran.domain.Identifiable
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.skills.custom.subskill.SubSkill
import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.domain.skills.custom.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.view.ui.skills.SkillViewFactory
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.buff.BuffSkillSlotView
import com.myrran.view.ui.skills.custom.stat.StatsView

class SubSkillSlotView(

    override val id: SkillViewId,
    private val model: SubSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: SubSkillController,
    private val factory: SkillViewFactory,

): Table(), Identifiable<SkillViewId>
{
    private val subSlotKeyView: SubSlotKeyView = SubSlotKeyView(model, assets)
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
        if (model.content is SubSkill)
            keyAndStatsTable.add(stats).left()

        keyAndStatsTable.add(subSlotKeyView.left())
        keyAndStatsTable.row()

        add(keyAndStatsTable).left().row()
        buffSlots.values.forEach{ add(it).right().row() }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

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

        when (val subSkill = model.content) {

            is NoSubSkill -> emptyMap()
            is SubSkill -> subSkill.getBuffSkillSlots()
                .associate { it.id to factory.createBuffSlotView(it, controller) }
        }

    private fun getStats(): Collection<Stat> =

        when (val subSkill = model.content) {

            NoSubSkill -> emptyList()
            is SubSkill -> subSkill.stats.getStats()
        }
}
