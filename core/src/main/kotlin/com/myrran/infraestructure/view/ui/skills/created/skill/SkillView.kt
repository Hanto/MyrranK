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
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.infraestructure.controller.SkillController
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
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
    private var headerView: SkillHeaderView = SkillHeaderView(model, assets, controller)
    private var statsView: StatsView = StatsView( { model.getStats() }, assets, controller)
    private var formSlotViews: Map<FormSkillSlotId, FormSkillSlotView> = createFormSlotViews()
    private val keyView: SkillSlotKeyView = SkillSlotKeyView(model, assets, controller)

    private val rootTable = Table().top().left()
    private val bodyTable = Table()
    private val skillStatsTable = Table()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        addListener(UIClickListener { toFront() } )

        touchable = Touchable.enabled
        skillStatsTable.setBackground(assets.tableBackground)
        rebuildTable()

        actor = rootTable
        setSize(prefWidth, prefHeight)
    }

    private fun rebuildTable() {

        rootTable.clearChildren()
        bodyTable.clearChildren()
        skillStatsTable.clearChildren()

        // stats:
        skillStatsTable.add(keyView).fillY()
        skillStatsTable.add(statsView)

        // stats + forms:
        bodyTable.add(skillStatsTable).top().right().padBottom(0f).padBottom(2f).row()
        formSlotViews.values.forEach { bodyTable.add(it).top().right().expand().fillX().padBottom(2f).row() }

        // all:
        rootTable.add(headerView).left().fillX().padBottom(0f).row()
        rootTable.add(bodyTable)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    private fun updateStat(statId: StatId) {

        statsView.update(statId)
        headerView.update()
    }

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillCreatedEvent -> Unit
            is SkillRemovedEvent -> Unit
            is SkillStatUpgradedEvent ->  updateStat(event.statId)
            is FormSkillStatUpgradedEvent -> formSlotViews[event.formSlot]?.updateStat(event.statId).also { headerView.update() }
            is FormSkillChangedEvent -> formSlotViews[event.formSlot]?.updateForm()
            is FormSkillRemovedEvent -> formSlotViews[event.formSlot]?.updateForm()
            is EffectSkillStatUpgradedEvent -> formSlotViews[event.formSlot]?.effectSlotViews?.get(event.effectSlot)?.updateStat(event.statId).also { headerView.update() }
            is EffectSkillChangedEvent -> formSlotViews[event.formSlot]?.effectSlotViews?.get(event.effectSlot)?.updateEffect().also { headerView.update() }
            is EffectSkillRemovedEvent -> formSlotViews[event.formSlot]?.effectSlotViews?.get(event.effectSlot)?.updateEffect().also { headerView.update() }
        }
    }

    override fun dispose() {

        factory.disposeView(id)
        formSlotViews.forEach { it.value.dispose() }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createFormSlotViews(): Map<FormSkillSlotId, FormSkillSlotView> {

        formSlotViews?.forEach { it.value.dispose() }

        return model.getFormSkillSlots()
            .associate { it.id to factory.createFormSlotView(it, controller) }
    }
}
