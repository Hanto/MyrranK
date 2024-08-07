package com.myrran.view.ui.skills.book

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.application.SpellBook
import com.myrran.controller.BookSkillController
import com.myrran.domain.Identifiable
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
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.utils.observer.Observer
import com.myrran.view.ui.misc.AutoFocusScrollPane
import com.myrran.view.ui.misc.UIClickListener
import com.myrran.view.ui.misc.UIMoveListener
import com.myrran.view.ui.skills.SkillViewFactory
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.skill.SkillView

class SkillsView(

    override val id: SkillViewId,
    private val model: SpellBook,
    private val assets: SkillViewAssets,
    private val controller: BookSkillController,
    private val factory: SkillViewFactory,

): Container<Table>(), Identifiable<SkillViewId>, Observer<SkillEvent>, Disposable {

    private val header: TemplatesHeaderView = TemplatesHeaderView("Spells", assets)
    private var views: Map<SkillId, SkillView> = createSkillViews()
    private val rootTable = Table()

    init {

        model.addObserver(this)
        header.addListener(UIMoveListener(this))
        touchable = Touchable.enabled
        addListener(UIClickListener { toFront() } )

        top().left()
        rebuildTable()

        actor = rootTable
        setSize(prefWidth, prefHeight)
    }

    private fun rebuildTable() {

        rootTable.clearChildren()

        val templatesTable = Table().top().left()
        views.values.sortedBy { it.model.name.value }.forEach { templatesTable.add(it).left().row() }
        val scrollPane = AutoFocusScrollPane(templatesTable)
        scrollPane.setScrollbarsVisible(true)

        val scrollTable = Table().top().left()
        scrollTable.background = assets.tableBackgroundLight
        scrollTable.add(scrollPane)

        rootTable.add(header).expand().fillX().row()
        rootTable.add(scrollTable).height(400f).row()
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    private fun update() {

        factory.disposeView(id)
        views.values.forEach { factory.disposeView(it.id) }
        views = createSkillViews()
        rebuildTable()
    }

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillCreatedEvent -> update()
            is SkillRemovedEvent -> update()
            is SkillStatUpgradedEvent ->  views[event.skillId]?.propertyChange(event)
            is SubSkillStatUpgradedEvent -> views[event.skillId]?.propertyChange(event)
            is SubSkillChangedEvent -> views[event.skillId]?.propertyChange(event)
            is SubSkillRemovedEvent -> views[event.skillId]?.propertyChange(event)
            is BuffSkillStatUpgradedEvent -> views[event.skillId]?.propertyChange(event)
            is BuffSkillChangedEvent -> views[event.skillId]?.propertyChange(event)
            is BuffSkillRemovedEvent -> views[event.skillId]?.propertyChange(event)
        }
    }

    override fun dispose() {

        factory.disposeView(id)
        views.values.forEach { factory.disposeView(it.id) }
        model.removeAllObservers()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createSkillViews(): Map<SkillId, SkillView> =

        model.created.findAll()
            .associate { it.id to factory.createSkillView(it, controller) }
}
