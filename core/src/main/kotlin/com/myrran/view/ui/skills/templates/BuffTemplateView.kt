package com.myrran.view.ui.skills.templates

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.view.ui.misc.ActorClickListener
import com.myrran.view.ui.misc.ActorMoveListener
import com.myrran.view.ui.skills.assets.SkillViewAssets

class BuffTemplateView(

    buff: BuffSkillTemplate,
    assets: SkillViewAssets

): Container<Table>()
{
    private val header = BuffTemplateHeaderView(buff, assets)

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
    }
}
