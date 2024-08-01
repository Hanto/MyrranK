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
    private val statsView = StatsView(skill.getStats(), assets, controller.toStatController(skill))

    init {

        top().left()
        setBackground(assets.background)

        add(statsView).top().left().row()

        skill.getSubSkillSlots()
            .map { SubSkillSlotView(it, controller.toSubSkillController(skill, it)) }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() =

        statsView.update()

    override fun propertyChange(evt: PropertyChangeEvent) =
        update()
}
