package com.myrran.view.ui.skills.view.skill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.controller.SkillController
import com.myrran.view.ui.skills.view.stat.StatsView
import com.myrran.view.ui.skills.view.subskill.SubSkillSlotView

class SkillView(

    private val skill: Skill,
    assets: SkillViewAssets,
    controller: SkillController,

): Table(), Disposable
{
    private val header = SkillHeader(skill, assets)
    private val statsView = StatsView( skill, { skill.getStats() }, assets, controller.toStatController(skill))
    private val slotsView = skill.getSubSkillSlots()
        .map { SubSkillSlotView(skill, it, assets, controller.toSubSkillController(skill, it)) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left()
        val outerTable = Table()
        val statsTable = Table()

        add(header).right().padBottom(0f).minWidth(338f).padRight(4f).row()
        add(outerTable)
        outerTable.add(statsTable).top().right().padBottom(0f).padRight(4f).row()
        statsTable.add(statsView)
        slotsView.forEach { outerTable.add(it).top().left().expand().fillX().row() }
    }

    override fun dispose() =

        skill.removeAllObservers()
}
