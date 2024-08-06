package com.myrran.view.ui.skills.custom.skill

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.controller.SkillController
import com.myrran.domain.Identifiable
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.BuffSkillRemovedEvent
import com.myrran.domain.events.BuffSkillStatUpgradedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.events.SubSkillRemovedEvent
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.skills.custom.Skill
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.utils.observer.Observer
import com.myrran.view.ui.misc.UIClickListener
import com.myrran.view.ui.misc.UIMoveListener
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
    private var skillHeader: SkillHeaderView = SkillHeaderView(model, assets)
    private var skillStats: StatsView = StatsView( { model.getStats() }, assets, controller)
    private var subSlots: Map<SubSkillSlotId, SubSkillSlotView> = createSubSkillSlotViews()
    private val table = Table().top().left()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        model.addObserver(this)
        addListener(UIClickListener { toFront() } )

        top().left()
        rebuildTable()

        actor = table
        setSize(prefWidth, prefHeight)
    }

    private fun rebuildTable() {

        table.clearChildren()
        skillHeader.touchable = Touchable.enabled
        skillHeader.addListener(UIMoveListener(this))

        val bodyTable = Table()
        val skillStatsTable = Table()
        skillStatsTable.touchable = Touchable.enabled
        subSlots.values.forEach { it.touchable = Touchable.enabled }

        skillStatsTable.add(skillStats)
        bodyTable.add(skillStatsTable).top().left().padBottom(0f).padLeft(4f).row()
        subSlots.values.forEach { bodyTable.add(it).top().right().expand().fillX().row() }

        table.add(skillHeader).left().minWidth(336f).padBottom(0f).padLeft(4f).row()
        table.add(bodyTable)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    private fun update() {

        factory.disposeView(id)
        subSlots.values.forEach { it.dispose() }
        subSlots = createSubSkillSlotViews()
        rebuildTable()
    }

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillStatUpgradedEvent ->  { skillStats.update(event.statId); skillHeader.update() }
            is SubSkillStatUpgradedEvent -> { subSlots[event.subSlot]?.update(event.statId); skillHeader.update() }
            is SubSkillChangedEvent -> update()
            is SubSkillRemovedEvent -> update()
            is BuffSkillStatUpgradedEvent -> { subSlots[event.subSlot]?.buffSlots?.get(event.buffSlot)?.update(event.statId); skillHeader.update() }
            is BuffSkillChangedEvent -> subSlots[event.subSlot]?.buffSlots?.get(event.buffSlot)?.update()
            is BuffSkillRemovedEvent -> update()
        }
    }

    override fun dispose() {

        factory.disposeView(id)
        subSlots.values.forEach { it.dispose() }
        model.removeAllObservers()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createSubSkillSlotViews(): Map<SubSkillSlotId, SubSkillSlotView> =

        model.getSubSkillSlots()
            .associate { it.id to factory.createSubSlotView(it, controller)  }
}
