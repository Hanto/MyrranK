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
    private val skillHeader = SkillHeader(skill, skill, assets)
    private val skillStats = StatsView( skill, { skill.getStats() }, assets, controller.toStatController(skill))
    private val subSlots = skill.getSubSkillSlots()
        .map { SubSkillSlotView(skill, it, assets, controller.toSubSkillController(skill, it)) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left()
        val skillStatsTable = Table()
        val outerTable = Table()

        skillStatsTable.add(skillStats)
        outerTable.add(skillStatsTable).top().left().padBottom(0f).padLeft(4f).row()
        subSlots.forEach { outerTable.add(it).top().right().expand().fillX().row() }

        add(skillHeader).left().padBottom(0f).minWidth(338f).padLeft(4f).row()
        add(outerTable)
    }

    override fun dispose() =

        skill.removeAllObservers()
}
