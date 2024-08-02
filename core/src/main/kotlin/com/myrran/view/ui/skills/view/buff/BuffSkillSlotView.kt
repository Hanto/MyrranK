package com.myrran.view.ui.skills.view.buff

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlot
import com.myrran.domain.skills.skills.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.domain.utils.observer.Observable
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.controller.BuffSKillController
import com.myrran.view.ui.skills.view.stat.StatsView

class BuffSkillSlotView(

    private val observable: Observable,
    private val buffSkillSlot: BuffSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController

): Table()
{
    private val buffSlotKeyView = BuffSlotKeyView(observable, buffSkillSlot, assets)
    private var stats = StatsView( observable, { getStats() }, assets, controller.toStatController() )

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
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getStats(): Collection<Stat> =

        when (val buffSkill = buffSkillSlot.content) {

            is NoBuffSkill -> emptyList()
            is BuffSkill -> buffSkill.stats.getStats()
        }
}
