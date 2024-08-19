package com.myrran.infraestructure.view.skills.created.skill

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
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.world.SpellBook
import com.myrran.infraestructure.controller.skills.SpellBookController
import com.myrran.infraestructure.view.skills.SkillViewAssets
import com.myrran.infraestructure.view.skills.SkillViewFactory
import com.myrran.infraestructure.view.skills.SkillViewId
import com.myrran.infraestructure.view.skills.templates.TemplatesHeaderView
import com.myrran.infraestructure.view.ui.AutoFocusScrollPane
import com.myrran.infraestructure.view.ui.UIClickListener
import com.myrran.infraestructure.view.ui.UIMoveListener

class SkillViews(

    override val id: SkillViewId,
    private val model: SpellBook,
    private val assets: SkillViewAssets,
    private val controller: SpellBookController,
    private val factory: SkillViewFactory,

): Container<Table>(), Identifiable<SkillViewId>, Observer<SkillEvent>, Disposable {

    private val header: TemplatesHeaderView = TemplatesHeaderView("Spells", assets)
    private var views: Map<SkillId, SkillView> = createSkillViews()

    private val rootTable = Table()
    private val templatesTable = Table().top().left()
    private val scrollPane = AutoFocusScrollPane(templatesTable)
    private val scrollTable = Table().top().left()


    init {

        model.addObserver(this)
        header.addListener(UIMoveListener(this))
        addListener(UIClickListener { toFront() } )
        scrollPane.setScrollbarsVisible(true)

        touchable = Touchable.enabled
        scrollTable.background = assets.containerBackground
        top().left()
        rebuildTable()

        actor = rootTable
        setSize(prefWidth, prefHeight)
    }

    private fun rebuildTable() {

        rootTable.clearChildren()
        templatesTable.clearChildren()
        scrollTable.clearChildren()

        // skills inside scrollpane
        views.values.sortedBy { it.model.name.value }.forEach { templatesTable.add(it).left().row() }

        scrollTable.add(scrollPane)

        rootTable.add(header).expand().fillX().row()
        rootTable.add(scrollTable).height(400f).row()
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    private fun update() {

        factory.disposeView(id)
        views.values.forEach { it.dispose() }
        views = createSkillViews()
        rebuildTable()
    }

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillCreatedEvent -> update()
            is SkillRemovedEvent -> update().also { views[event.skillId]?.dispose() }
            is SkillStatUpgradedEvent ->  views[event.skillId]?.propertyChange(event)
            is FormSkillStatUpgradedEvent -> views[event.skillId]?.propertyChange(event)
            is FormSkillChangedEvent -> views[event.skillId]?.propertyChange(event)
            is FormSkillRemovedEvent -> views[event.skillId]?.propertyChange(event)
            is EffectSkillStatUpgradedEvent -> views[event.skillId]?.propertyChange(event)
            is EffectSkillChangedEvent -> views[event.skillId]?.propertyChange(event)
            is EffectSkillRemovedEvent -> views[event.skillId]?.propertyChange(event)
        }
    }

    override fun dispose() {

        factory.disposeView(id)
        views.values.forEach { it.dispose() }
        model.removeAllObservers()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createSkillViews(): Map<SkillId, SkillView> =

        model.findAllPlayerSkills()
            .associate { it.id to factory.createSkillView(it, controller) }
}
