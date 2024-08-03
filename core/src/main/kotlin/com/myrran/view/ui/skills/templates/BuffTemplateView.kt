package com.myrran.view.ui.skills.templates

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.BuffDaDSource
import com.myrran.controller.SkillController
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.view.ui.misc.ActorClickListener
import com.myrran.view.ui.misc.ActorMoveListener
import com.myrran.view.ui.skills.assets.SkillViewAssets

class BuffTemplateView(

    val buff: BuffSkillTemplate,
    val assets: SkillViewAssets,
    val controller: SkillController,

): Container<Table>()
{
    val header = BuffTemplateHeaderView(buff, assets)
    private val dadSource = BuffDaDSource(this, assets)

    init {

        header.touchable = Touchable.enabled
        header.addListener(ActorMoveListener(this))
        addListener(ActorClickListener { toFront() } )

        val table = Table()
        table.touchable = Touchable.enabled
        table.add(header).expandX().fillX().row()
        table.add(StatsTemplateView(buff.stats, assets))

        actor = table
        setSize(prefWidth, prefHeight)

        controller.dadManager.addSource(dadSource)
    }
}
