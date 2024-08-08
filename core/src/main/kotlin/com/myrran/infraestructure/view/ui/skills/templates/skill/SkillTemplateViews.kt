package com.myrran.infraestructure.view.ui.skills.templates.skill

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.application.SpellBook
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
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.controller.BookSkillController
import com.myrran.infraestructure.view.ui.misc.AutoFocusScrollPane
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.misc.UIMoveListener
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.templates.TemplatesHeaderView

class SkillTemplateViews(

    override val id: SkillViewId,
    private val model: SpellBook,
    private val assets: SkillViewAssets,
    private val controller: BookSkillController,
    private val factory: SkillViewFactory,

): Container<Table>(), Identifiable<SkillViewId>, Observer<SkillEvent>, Disposable
{
    private val header: TemplatesHeaderView = TemplatesHeaderView("Skill Templates", assets)
    private var views: Map<SkillTemplateId, SkillTemplateView> = createSkillTemplateViews()

    private val rootTable = Table()
    private val templatesTable = Table().top().left()
    private val scrollTable = Table().top().left()
    private val scrollPane = AutoFocusScrollPane(templatesTable)

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

        // templates inside scrollpane
        views.values.sortedBy { it.model.value.name.value }.forEach { templatesTable.add(it).left().row() }

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
            is FormSkillStatUpgradedEvent -> Unit
            is FormSkillChangedEvent -> Unit
            is FormSkillRemovedEvent -> Unit
            is EffectSkillStatUpgradedEvent -> Unit
            is EffectSkillChangedEvent -> Unit
            is EffectSkillRemovedEvent -> Unit
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
