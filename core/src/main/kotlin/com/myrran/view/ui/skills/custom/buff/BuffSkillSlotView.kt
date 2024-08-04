package com.myrran.view.ui.skills.custom.buff

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.BuffSKillController
import com.myrran.domain.Identifiable
import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.stat.StatsView

class BuffSkillSlotView(

    override val id: SkillViewId,
    private val model: BuffSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController

): Table(), Identifiable<SkillViewId>
{
    val buffSlotKeyView: BuffSlotKeyView = BuffSlotKeyView(model, assets)
    private var stats: StatsView = getStatsView()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        right()
        rebuildTable()
    }

    private fun rebuildTable() {

        clear()
        if (model.content is BuffSkill)
            add(stats).right()

        add(buffSlotKeyView).expandY().fillY().right()
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update(statId: StatId) {

        stats.update(statId)
    }

    fun update() {

        buffSlotKeyView.update()
        stats = getStatsView()
        rebuildTable()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getStatsView(): StatsView =

        StatsView( { getStats() }, assets, controller )

    private fun getStats(): Collection<Stat> =

        when (val buffSkill = model.content) {

            is NoBuffSkill -> emptyList()
            is BuffSkill -> buffSkill.stats.getStats()
        }
}
