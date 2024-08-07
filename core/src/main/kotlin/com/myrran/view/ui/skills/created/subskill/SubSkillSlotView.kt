package com.myrran.view.ui.skills.created.subskill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.SubSkillController
import com.myrran.domain.Identifiable
import com.myrran.domain.skills.created.SubSkill
import com.myrran.domain.skills.created.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.created.buff.BuffSkillSlotId
import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.subskill.SubSkillSlot
import com.myrran.view.ui.skills.SkillViewFactory
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.created.buff.BuffSkillSlotView
import com.myrran.view.ui.skills.created.stat.StatsView

class SubSkillSlotView(

    override val id: SkillViewId,
    private val model: SubSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: SubSkillController,
    private val factory: SkillViewFactory,

): Table(), Identifiable<SkillViewId>
{
    val subSlotKeyView: SubSlotKeyView = SubSlotKeyView(model, assets, controller)
    private var stats: StatsView = StatsView( { getStats() }, assets, controller)
    var buffSlots: Map<BuffSkillSlotId, BuffSkillSlotView> = createBuffSkillSlotViews()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        left()
        setBackground(assets.tableBackgroundLight)
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()

        val keyAndStatsTable = Table()
        keyAndStatsTable.add(subSlotKeyView).expandY().fillY()
        if (model.content is SubSkill)
            keyAndStatsTable.add(stats)

        keyAndStatsTable.row()

        add(keyAndStatsTable).row()
        buffSlots.values.forEach{ add(it).left().row() }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update(statId: StatId) {

        stats.update(statId)
    }

    fun dispose() {

        factory.disposeView(id)
        buffSlots.values.forEach { factory.disposeView(it.id) }
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

            is NoSubSkill -> emptyList()
            is SubSkill -> subSkill.stats.getStats()
        }
}
