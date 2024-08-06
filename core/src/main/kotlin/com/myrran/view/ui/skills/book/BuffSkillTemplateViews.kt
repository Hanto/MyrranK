package com.myrran.view.ui.skills.book

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.Identifiable
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.BuffSkillRemovedEvent
import com.myrran.domain.events.BuffSkillStatUpgradedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.events.SubSkillRemovedEvent
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.spells.SpellBook
import com.myrran.domain.utils.observer.Observer
import com.myrran.view.ui.misc.AutoFocusScrollPane
import com.myrran.view.ui.misc.UIClickListener
import com.myrran.view.ui.misc.UIMoveListener
import com.myrran.view.ui.skills.SkillViewFactory
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.book.buff.BuffSkillTemplateViewsHeader
import com.myrran.view.ui.skills.templates.BuffTemplateView

class BuffSkillTemplateViews(

    override val id: SkillViewId,
    private val model: SpellBook,
    private val assets: SkillViewAssets,
    private val factory: SkillViewFactory,

): Container<Table>(), Identifiable<SkillViewId>, Observer<SkillEvent>, Disposable
{
    private val header: BuffSkillTemplateViewsHeader = BuffSkillTemplateViewsHeader("Debuff SpellBook", assets)
    private var views: Map<BuffSkillTemplateId, BuffTemplateView> = createBuffSkillTemplateViews()
    private val rootTable = Table().top().left()

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

    private fun updateQuantities() {

        model.learnedBuffSkillTemplates().forEach { views[it.item.id]?.setAvailable(it.quantity) }
    }

    override fun propertyChange(event: SkillEvent) {

        when (event) {

            is SkillStatUpgradedEvent ->  Unit
            is SubSkillStatUpgradedEvent -> Unit
            is SubSkillChangedEvent -> updateQuantities()
            is SubSkillRemovedEvent -> updateQuantities()
            is BuffSkillStatUpgradedEvent -> Unit
            is BuffSkillChangedEvent -> updateQuantities()
            is BuffSkillRemovedEvent -> updateQuantities()
        }
    }

    override fun dispose() {

        factory.disposeView(id)
        views.values.forEach { factory.disposeView(it.id) }
        model.removeAllObservers()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createBuffSkillTemplateViews(): Map<BuffSkillTemplateId, BuffTemplateView> =

        model.learnedBuffSkillTemplates()
            .associate { it.item.id to factory.createBuffTemplateView(it.item, it.quantity) }
}
