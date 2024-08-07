package com.myrran.infraestructure.view.ui.skills.created.skill

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.controller.SkillController
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.BuffSkillRemovedEvent
import com.myrran.domain.events.BuffSkillStatUpgradedEvent
import com.myrran.domain.events.SkillCreatedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillRemovedEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.events.SubSkillRemovedEvent
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.observer.Observer
import com.myrran.domain.skills.created.Skill
import com.myrran.domain.skills.created.subskill.SubSkillSlotId
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.stat.StatsView
import com.myrran.infraestructure.view.ui.skills.created.subskill.SubSkillSlotView

class SkillView(

    override val id: SkillViewId,
    val model: Skill,
    private val assets: SkillViewAssets,
    private val controller: SkillController,
    private val factory: SkillViewFactory,

): Container<Table>(), Identifiable<SkillViewId>, Observer<SkillEvent>, Disposable
{
    private var skillHeader: SkillHeaderView = SkillHeaderView(model, assets)
    private var skillStats: StatsView = StatsView( { model.getStats() }, assets, controller)
    private var subSlots: Map<SubSkillSlotId, SubSkillSlotView> = createSubSkillSlotViews()
    private val skillKey: SkillSlotKeyView = SkillSlotKeyView(model, assets, controller)
    private val table = Table().top().left()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        addListener(UIClickListener { toFront() } )

        top().left()
        rebuildTable()

        actor = table
        setSize(prefWidth, prefHeight)
    }

    private fun rebuildTable() {

        table.clearChildren()

        val bodyTable = Table()
        val skillStatsTable = Table()
        skillStatsTable.touchable = Touchable.enabled
        skillStatsTable.setBackground(assets.tableBackgroundLight)
        subSlots.values.forEach { it.touchable = Touchable.enabled }

        skillStatsTable.add(skillKey).fillY()
        skillStatsTable.add(skillStats)
        bodyTable.add(skillStatsTable).top().right().padBottom(0f).padBottom(2f).row()
        subSlots.values.forEach { bodyTable.add(it).top().right().expand().fillX().padBottom(2f).row() }

        table.add(skillHeader).left().fillX().padBottom(0f).row()
        table.add(bodyTable)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        factory.disposeView(id)
        subSlots.values.forEach { it.dispose() }
        subSlots = createSubSkillSlotViews()
        rebuildTable()
    }

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillCreatedEvent -> Unit
            is SkillRemovedEvent -> Unit
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
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createSubSkillSlotViews(): Map<SubSkillSlotId, SubSkillSlotView> =

        model.getSubSkillSlots()
            .associate { it.id to factory.createSubSlotView(it, controller)  }
}
