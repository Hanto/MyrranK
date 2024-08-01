package com.myrran.view.ui.skill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.SkillController
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

class SkillView(

    skill: Skill,
    assets: SkillAssets,
    controller: SkillController,

): Table(), PropertyChangeListener
{
    private val header = SkillHeader(skill, assets)
    private val icon = SkillIconView(skill, assets)
    private val statsView = StatsView(skill.getStats(), assets, controller.toStatController(skill))
    private val slotsView = skill.getSubSkillSlots()
        .map { SubSkillSlotView(it, assets, controller.toSubSkillController(skill, it)) }

    init {

        top().left()

        val outerTable = Table()
        val innerTable = Table()

        outerTable.setBackground(assets.tableBackgroundDark)

        //innerTable.add(icon).top().left()
        outerTable.add(innerTable).left().row()
        outerTable.add(statsView).top().left().row()
        slotsView.forEach{ outerTable.add(it).top().left().row() }

        add(header).left().minWidth(132f).row()
        add(outerTable)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        icon.update()
        statsView.update()
        slotsView.forEach{ it.update() }
    }


    override fun propertyChange(evt: PropertyChangeEvent) =
        update()
}
