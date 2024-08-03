package com.myrran.view.ui.skills.custom.subskill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.SubSkillController
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.skills.custom.subskill.SubSkill
import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.domain.skills.custom.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.utils.observer.Observable
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.buff.BuffSkillSlotView
import com.myrran.view.ui.skills.custom.stat.StatsView

class SubSkillSlotView(

    private val observable: Observable,
    private val subSkillSlot: SubSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: SubSkillController

): Table()
{
    private val subSlotKeyView = SubSlotKeyView(observable, subSkillSlot, assets)
    private var stats = StatsView( observable, { getStats() }, assets, controller)
    private var buffSkillSlotViews = getBuffSkillSLotViews()

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
        buffSkillSlotViews.forEach{ add(it).right().row() }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getBuffSkillSLotViews(): List<BuffSkillSlotView> =

        when (val subSkill = subSkillSlot.content) {

            is NoSubSkill -> emptyList()
            is SubSkill -> subSkill.getBuffSkillSlots().map { BuffSkillSlotView(observable, it, assets, controller.toBuffSkillController(it)) }
        }

    private fun getStats(): Collection<Stat> =

        when (val subSkill = subSkillSlot.content) {

            NoSubSkill -> emptyList()
            is SubSkill -> subSkill.stats.getStats()
        }
}
