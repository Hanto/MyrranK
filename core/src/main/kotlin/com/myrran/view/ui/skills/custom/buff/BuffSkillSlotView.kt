package com.myrran.view.ui.skills.custom.buff

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.BuffSKillController
import com.myrran.domain.Identifiable
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.Event
import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.utils.observer.Observable
import com.myrran.domain.utils.observer.Observer
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.stat.StatsView

class BuffSkillSlotView(

    override val id: SkillViewId,
    private val observable: Observable,
    private val buffSkillSlot: BuffSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController

): Table(), Identifiable<SkillViewId>, Observer
{
    val buffSlotKeyView: BuffSlotKeyView = BuffSlotKeyView(observable, buffSkillSlot, assets)
    private var stats: StatsView = StatsView( observable, { getStats() }, assets, controller )

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        observable.addObserver(this)
        right()
        rebuildTable()
    }

    private fun rebuildTable() {

        clear()
        if (buffSkillSlot.content is BuffSkill)
            add(stats).right()

        add(buffSlotKeyView).expandY().fillY().right()
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    override fun propertyChange(event: Event) {

        if (event is BuffSkillChangedEvent && event.buffId == buffSkillSlot.id) {

            stats = StatsView( observable, { getStats() }, assets, controller )
            rebuildTable()
        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getStats(): Collection<Stat> =

        when (val buffSkill = buffSkillSlot.content) {

            is NoBuffSkill -> emptyList()
            is BuffSkill -> buffSkill.stats.getStats()
        }
}
