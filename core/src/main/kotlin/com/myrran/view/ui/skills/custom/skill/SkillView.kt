package com.myrran.view.ui.skills.custom.skill

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.controller.SkillController
import com.myrran.domain.Identifiable
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.BuffSkillStatUpgradedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.skills.custom.skill.Skill
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.utils.observer.Observer
import com.myrran.view.ui.misc.ActorClickListener
import com.myrran.view.ui.misc.ActorMoveListener
import com.myrran.view.ui.skills.SkillViewFactory
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.stat.StatsView
import com.myrran.view.ui.skills.custom.subskill.SubSkillSlotView

class SkillView(

    override val id: SkillViewId,
    private val model: Skill,
    private val assets: SkillViewAssets,
    private val controller: SkillController,
    private val factory: SkillViewFactory,

): Container<Table>(), Identifiable<SkillViewId>, Observer<SkillEvent>, Disposable
{
    private val skillHeader: SkillHeader = SkillHeader(model, assets)
    private val skillStats: StatsView = StatsView( { model.getStats() }, assets, controller)
    private val subSlots: Map<SubSkillSlotId, SubSkillSlotView> = createSubSkillSlotViews()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        model.addObserver(this)
        skillHeader.touchable = Touchable.enabled
        skillHeader.addListener(ActorMoveListener(this))
        addListener(ActorClickListener { toFront() } )

        val table = Table().top().left()
        val bodyTable = Table()
        val skillStatsTable = Table()
        skillStatsTable.touchable = Touchable.enabled
        subSlots.values.forEach { it.touchable = Touchable.enabled }

        skillStatsTable.add(skillStats)
        bodyTable.add(skillStatsTable).top().left().padBottom(0f).padLeft(4f).row()
        subSlots.values.forEach { bodyTable.add(it).top().right().expand().fillX().row() }

        table.add(skillHeader).left().minWidth(338f).padBottom(0f).padLeft(4f).row()
        table.add(bodyTable)

        actor = table
        setSize(prefWidth, prefHeight)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillStatUpgradedEvent ->  { skillStats.update(event.statId); skillHeader.update() }
            is SubSkillStatUpgradedEvent -> { subSlots[event.subId]?.update(event); skillHeader.update() }
            is BuffSkillStatUpgradedEvent -> { subSlots[event.subId]?.buffSlots?.get(event.buffId)?.update(event.statId); skillHeader.update() }
            is SubSkillChangedEvent -> subSlots[event.subId]?.update()
            is BuffSkillChangedEvent -> subSlots[event.subId]?.buffSlots?.get(event.buffId)?.update()
            else -> Unit
        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createSubSkillSlotViews(): Map<SubSkillSlotId, SubSkillSlotView> =

        model.getSubSkillSlots()
            .associate { it.id to SubSkillSlotView(it, assets, controller.toSubSkillController(it), factory)  }

    override fun dispose() =

        model.removeAllObservers()
}
