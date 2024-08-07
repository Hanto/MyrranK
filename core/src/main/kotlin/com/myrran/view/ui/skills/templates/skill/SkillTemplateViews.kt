package com.myrran.view.ui.skills.templates.skill

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.application.SpellBook
import com.myrran.controller.BookSkillController
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
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.view.ui.misc.AutoFocusScrollPane
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.misc.UIMoveListener
import com.myrran.view.ui.skills.SkillViewFactory
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.templates.TemplatesHeaderView

class SkillTemplateViews(

    override val id: SkillViewId,
    private val model: SpellBook,
    private val assets: SkillViewAssets,
    private val controller: BookSkillController,
    private val factory: SkillViewFactory,

): Container<Table>(), Identifiable<SkillViewId>, Observer<SkillEvent>, Disposable {

    private val header: TemplatesHeaderView = TemplatesHeaderView("Forms", assets)
    private var views: Map<SkillTemplateId, SkillTemplateView> = createSkillTemplateViews()
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
        views.values.sortedBy { it.model.value.name.value }.forEach { templatesTable.add(it).left().row() }
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

    private fun updateQuantities() {

        model.learnedSkillTemplates().forEach { views[it.value.id]?.setAvailable(it) }
    }

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillCreatedEvent -> updateQuantities()
            is SkillRemovedEvent -> updateQuantities()
            is SkillStatUpgradedEvent ->  Unit
            is SubSkillStatUpgradedEvent -> Unit
            is SubSkillChangedEvent -> Unit
            is SubSkillRemovedEvent -> Unit
            is BuffSkillStatUpgradedEvent -> Unit
            is BuffSkillChangedEvent -> Unit
            is BuffSkillRemovedEvent -> Unit
        }
    }

    override fun dispose() {

        factory.disposeView(id)
        views.values.forEach { factory.disposeView(it.id) }
        model.removeAllObservers()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createSkillTemplateViews(): Map<SkillTemplateId, SkillTemplateView> =

        model.learnedSkillTemplates()
            .associate { it.value.id to factory.createSkillTemplateView(it, controller) }
}
