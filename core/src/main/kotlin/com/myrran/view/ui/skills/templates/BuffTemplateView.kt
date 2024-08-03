package com.myrran.view.ui.skills.templates

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.view.ui.misc.ActorMoveListener2
import com.myrran.view.ui.skills.assets.SkillViewAssets

class BuffTemplateView(

    buff: BuffSkillTemplate,
    assets: SkillViewAssets

): Table()
{
    private val header = BuffTemplateHeaderView(buff, assets)

    init {

        header.touchable = Touchable.enabled
        header.addListener(ActorMoveListener2(this))

        add(header).expandX().fillX().row()
        add(StatsTemplateView(buff.stats, assets))
    }
}
