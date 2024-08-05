package com.myrran.view.ui.skills.book

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.spells.SpellBook
import com.myrran.view.ui.misc.ActorClickListener
import com.myrran.view.ui.misc.ActorMoveListener
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.SkillViewFactory
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.templates.BuffTemplateView

class BuffSkillTemplateViews(

    private val model: SpellBook,
    private val assets: SkillViewAssets,
    private val factory: SkillViewFactory,

): Container<Table>()
{
    private val headerText = TextView("Buff SpellBook", assets.font20, WHITE, 2f)
    private val views = createBuffSkillTemplateViews()

    init {

        touchable = Touchable.enabled
        addListener(ActorClickListener { toFront() } )

        top().left()
        val headerTable = Table().top().left()
        headerTable.add(headerText.align(Align.left)).minHeight(33f).padLeft(8f).left()
        headerTable.background = assets.tableBackgroundDark.tint(Color(0.6f, 0.6f, 0.6f, 0.90f))
        headerTable.addListener(ActorMoveListener(this))
        headerTable.touchable = Touchable.enabled

        val templateTable = Table().top().left()
        templateTable.background = assets.tableBackgroundDark.tint(Color(0.6f, 0.6f, 0.6f, 0.90f))
        views.values.forEach { templateTable.add(it).left().row() }
        val scrollPane = ScrollPane(templateTable)

        val rootTable = Table().top().left()
        rootTable.add(headerTable).expand().fillX().row()
        rootTable.add(scrollPane).minHeight(400f).row()

        actor = rootTable
        setSize(prefWidth, prefHeight)
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createBuffSkillTemplateViews(): Map<BuffSkillTemplateId, BuffTemplateView> =

        model.learnedBuffSkillTemplates()
            .map { factory.createBuffTemplateView(it.item, it.quantity) }
            .associateBy { it.model.id }

}
