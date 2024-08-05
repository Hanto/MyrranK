package com.myrran.view.ui.skills.custom.subskill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.SubSkillController
import com.myrran.domain.Identifiable
import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.view.ui.misc.ActorClickListener
import com.myrran.view.ui.misc.Button.RIGHT_BUTTON
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
    val subSlotKeyView: SubSlotKeyView = SubSlotKeyView(model, assets)
    private var stats: StatsView = StatsView( { getStats() }, assets, controller)
    var buffSlots: Map<BuffSkillSlotId, BuffSkillSlotView> = createBuffSkillSlotViews()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        right()
        subSlotKeyView.addListener(ActorClickListener(RIGHT_BUTTON) { controller.removeSubSkill() })
        setBackground(assets.tableBackgroundDark)
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()

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
