package com.myrran.view.ui.skill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.StatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.domain.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.SubSkillController
import com.myrran.view.ui.skill.stats.StatsView

class SubSkillSlotView(

    private val subSkillSlot: SubSkillSlot,
    private val assets: SkillAssets,
    private val controller: SubSkillController

): Table()
{
    private val keyAndStatsTable = Table().left()
    private val subSlotKeyView = SubSlotKeyView(subSkillSlot, assets)
    private var stats = StatsView( { getStats() }, assets, controller.toStatController())
    private var buffSkillSlotViews: List<BuffSkillSlotView> = getBuffSkillSLotViews()

    init {

        top().left()

        setBackground(assets.tableBackgroundDark)
        fillTable()
    }

    private fun fillTable() {

        keyAndStatsTable.add(subSlotKeyView.left())
        if (subSkillSlot.content is SubSkill) keyAndStatsTable.add(stats).left()
        keyAndStatsTable.row()

        add(keyAndStatsTable).left().row()

        buffSkillSlotViews.forEach{ add(it).left().row() }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update(event: SkillEvent) {

        when (event) {

            is StatUpgradedEvent ->  {

                stats.update(event)
                buffSkillSlotViews.forEach { it.update(event) }
            }
            is BuffSkillChangedEvent -> if (event.subId == subSkillSlot.id) {

                buffSkillSlotViews.forEach { it.update(event) }
            }
            is SubSkillChangedEvent -> if (event.subId == subSkillSlot.id) {

                subSlotKeyView.update(event)
                stats.update(event)
                fillTable()
            }
        }
    }

    private fun getBuffSkillSLotViews(): List<BuffSkillSlotView> =

        when (val subSkill = subSkillSlot.content) {

            is NoSubSkill -> emptyList()
            is SubSkill -> subSkill.getBuffSkillSlots().map { BuffSkillSlotView(it, assets, controller.toBuffSkillController(it)) }
        }

    private fun getStats(): Collection<Stat> =

        when (val subSkill = subSkillSlot.content) {

            NoSubSkill -> emptyList()
            is SubSkill -> subSkill.stats.getStats()
        }
}
