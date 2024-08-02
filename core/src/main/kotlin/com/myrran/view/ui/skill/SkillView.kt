package com.myrran.view.ui.skill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.SkillController
import com.myrran.view.ui.skill.stats.StatsView
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

class SkillView(

    skill: Skill,
    assets: SkillAssets,
    controller: SkillController,

): Table(), PropertyChangeListener
{
    private val header = SkillHeader(skill, assets)
    private val statsView = StatsView( { skill.getStats() }, assets, controller.toStatController(skill))
    private val slotsView = skill.getSubSkillSlots()
        .map { SubSkillSlotView(it, assets, controller.toSubSkillController(skill, it)) }

    init {

        top().left()

        val outerTable = Table()
        val statsTable = Table()

        //statsTable.setBackground(assets.tableBackgroundDark)

        add(header).right().padBottom(0f).minWidth(338f).padRight(4f).row()
        add(outerTable)
        outerTable.add(statsTable).top().right().padBottom(0f).padRight(4f).row()
        statsTable.add(statsView)
        slotsView.forEach { outerTable.add(it).top().left().expand().fillX().padBottom(1f).row() }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    private fun update(event: SkillEvent) {

        header.update(event)
        statsView.update(event)
        slotsView.forEach{ it.update(event) }
    }

    override fun propertyChange(event: PropertyChangeEvent) {

        if (event.newValue is SkillEvent)
            update(event.newValue as SkillEvent)
    }
}
