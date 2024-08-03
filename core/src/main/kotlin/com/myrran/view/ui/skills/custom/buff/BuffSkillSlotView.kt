package com.myrran.view.ui.skills.custom.buff

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.BuffDaDTarget
import com.myrran.controller.BuffSKillController
import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.utils.observer.Observable
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.stat.StatsView

class BuffSkillSlotView(

    private val observable: Observable,
    private val buffSkillSlot: BuffSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController

): Table()
{
    private val buffSlotKeyView = BuffSlotKeyView(observable, buffSkillSlot, assets)
    private var stats = StatsView( observable, { getStats() }, assets, controller )
    private val dadTarget = BuffDaDTarget(buffSlotKeyView, assets, controller)

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        right()
        rebuildTable()
    }

    private fun rebuildTable() {

        if (buffSkillSlot.content is BuffSkill)
            add(stats).right()

        add(buffSlotKeyView).expandY().fillY().right()

        controller.dadManager.addTarget(dadTarget)
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getStats(): Collection<Stat> =

        when (val buffSkill = buffSkillSlot.content) {

            is NoBuffSkill -> emptyList()
            is BuffSkill -> buffSkill.stats.getStats()
        }
}
