package com.myrran.view.ui.skills.custom.buff

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.BuffSKillController
import com.myrran.domain.Identifiable
import com.myrran.domain.skills.custom.BuffSkill
import com.myrran.domain.skills.custom.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.stat.StatsView

class BuffSkillSlotView(

    override val id: SkillViewId,
    val model: BuffSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController

): Table(), Identifiable<SkillViewId>
{
    val buffSlotKeyView: BuffSlotKeyView = BuffSlotKeyView(model, assets, controller)
    private var stats: StatsView = createStatsView()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        left()
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()

        add(buffSlotKeyView).expandY().fillY()
        if (model.content is BuffSkill)
            add(stats)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update(statId: StatId) {

        stats.update(statId)
    }

    fun update() {

        buffSlotKeyView.update()
        stats = createStatsView()
        rebuildTable()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createStatsView(): StatsView =

        StatsView( { getStats() }, assets, controller )

    private fun getStats(): Collection<Stat> =

        when (val buffSkill = model.content) {

            is NoBuffSkill -> emptyList()
            is BuffSkill -> buffSkill.stats.getStats()
        }
}
