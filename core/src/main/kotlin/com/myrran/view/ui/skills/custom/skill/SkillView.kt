package com.myrran.view.ui.skills.custom.skill

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.controller.SkillController
import com.myrran.domain.skills.custom.skill.Skill
import com.myrran.view.ui.misc.ActorClickListener
import com.myrran.view.ui.misc.ActorMoveListener
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.stat.StatsView
import com.myrran.view.ui.skills.custom.subskill.SubSkillSlotView

class SkillView(

    private val skill: Skill,
    assets: SkillViewAssets,
    controller: SkillController,

): Container<Table>(), Disposable
{
    private val skillHeader = SkillHeader(skill, skill, assets)
    private val skillStats = StatsView( skill, { skill.getStats() }, assets, controller)
    private val subSlots = skill.getSubSkillSlots()
        .map { SubSkillSlotView(skill, it, assets, controller.toSubSkillController(it)) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        skillHeader.touchable = Touchable.enabled
        skillHeader.addListener(ActorMoveListener(this))
        addListener(ActorClickListener { toFront() } )

        val table = Table().top().left()
        val bodyTable = Table()
        val skillStatsTable = Table()
        skillStatsTable.touchable = Touchable.enabled
        subSlots.forEach { it.touchable = Touchable.enabled }

        skillStatsTable.add(skillStats)
        bodyTable.add(skillStatsTable).top().left().padBottom(0f).padLeft(4f).row()
        subSlots.forEach { bodyTable.add(it).top().right().expand().fillX().row() }

        table.add(skillHeader).left().minWidth(338f).padBottom(0f).padLeft(4f).row()
        table.add(bodyTable)

        actor = table
        setSize(prefWidth, prefHeight)
    }

    override fun dispose() =

        skill.removeAllObservers()
}
