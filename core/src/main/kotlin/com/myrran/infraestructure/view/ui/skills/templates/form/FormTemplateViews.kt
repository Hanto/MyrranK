package com.myrran.infraestructure.view.ui.skills.templates.form

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
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.view.ui.misc.AutoFocusScrollPane
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.misc.UIMoveListener
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.templates.TemplatesHeaderView

class FormTemplateViews(

    override val id: SkillViewId,
    private val model: SpellBook,
    private val assets: SkillViewAssets,
    private val factory: SkillViewFactory,

): Container<Table>(), Identifiable<SkillViewId>, Observer<SkillEvent>, Disposable {

    private val header: TemplatesHeaderView = TemplatesHeaderView("Form Templates", assets)
    private var views: Map<FormTemplateId, FormTemplateView> = createFormTemplateViews()
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

        model.learnedFormTemplates().forEach { views[it.value.id]?.setAvailable(it) }
    }

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillCreatedEvent -> Unit
            is SkillRemovedEvent -> Unit
            is SkillStatUpgradedEvent ->  Unit
            is FormSkillStatUpgradedEvent -> Unit
            is FormSkillChangedEvent -> updateQuantities()
            is FormSkillRemovedEvent -> updateQuantities()
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

    private fun createFormTemplateViews(): Map<FormTemplateId, FormTemplateView> =

        model.learnedFormTemplates()
            .associate { it.value.id to factory.createFormTemplateView(it) }
}
