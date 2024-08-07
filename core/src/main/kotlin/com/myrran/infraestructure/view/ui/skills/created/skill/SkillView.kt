package com.myrran.infraestructure.view.ui.skills.created.skill

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.EffectSkillChangedEvent
import com.myrran.domain.events.EffectSkillRemovedEvent
import com.myrran.domain.events.EffectSkillStatUpgradedEvent
import com.myrran.domain.events.FormSkillChangedEvent
import com.myrran.domain.events.FormSkillRemovedEvent
import com.myrran.domain.events.FormSkillStatUpgradedEvent
import com.myrran.domain.events.SkillCreatedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillRemovedEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.observer.Observer
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.controller.SkillController
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.form.FormSkillSlotView
import com.myrran.infraestructure.view.ui.skills.created.stat.StatsView

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
    private var formSlots: Map<FormSkillSlotId, FormSkillSlotView> = createFormSlotViews()
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
        formSlots.values.forEach { it.touchable = Touchable.enabled }

        skillStatsTable.add(skillKey).fillY()
        skillStatsTable.add(skillStats)
        bodyTable.add(skillStatsTable).top().right().padBottom(0f).padBottom(2f).row()
        formSlots.values.forEach { bodyTable.add(it).top().right().expand().fillX().padBottom(2f).row() }

        table.add(skillHeader).left().fillX().padBottom(0f).row()
        table.add(bodyTable)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        factory.disposeView(id)
        formSlots.values.forEach { it.dispose() }
        formSlots = createFormSlotViews()
        rebuildTable()
    }

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillCreatedEvent -> Unit
            is SkillRemovedEvent -> Unit
            is SkillStatUpgradedEvent ->  { skillStats.update(event.statId); skillHeader.update() }
            is FormSkillStatUpgradedEvent -> { formSlots[event.formSlot]?.update(event.statId); skillHeader.update() }
            is FormSkillChangedEvent -> update()
            is FormSkillRemovedEvent -> update()
            is EffectSkillStatUpgradedEvent -> { formSlots[event.formSlot]?.effectSlotsViews?.get(event.effectSlot)?.update(event.statId); skillHeader.update() }
            is EffectSkillChangedEvent -> formSlots[event.formSlot]?.effectSlotsViews?.get(event.effectSlot)?.update()
            is EffectSkillRemovedEvent -> update()
        }
    }

    override fun dispose() {

        factory.disposeView(id)
        formSlots.values.forEach { it.dispose() }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createFormSlotViews(): Map<FormSkillSlotId, FormSkillSlotView> =

        model.getFormSkillSlots()
            .associate { it.id to factory.createFormSlotView(it, controller)  }
}
