package com.myrran.view.ui.skill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.skill.Skill

class SkillView(

    skill: Skill,
    assets: SkillAssets,
    controller: SkillController,

): Table()
{
    private val statsView = StatsView(skill.getStats(), assets, controller.skillUpgrade.invoke(skill.id))

    init {

        top().left()
        setBackground(assets.background)

        add(statsView).top().left().row()

        skill.getSubSkillSlots().map {

            SubSkillSlotView(it, controller.toSubSkillController(skill, it))
        }
    }

    fun update() {

        statsView.update()
    }
}
