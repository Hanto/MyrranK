package com.myrran.infraestructure.view.ui.skills.templates.effect

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.templates.stat.StatsTemplateView

class EffectTemplateView(

    override val id: SkillViewId,
    val model: Quantity<EffectTemplate>,
    private val assets: SkillViewAssets,

): Container<Table>(), Identifiable<SkillViewId>
{
    val header = EffectTemplateHeaderView(model, assets)

    init {

        header.touchable = Touchable.enabled
        addListener(UIClickListener { toFront() } )

        val table = Table()
        table.touchable = Touchable.enabled
        table.add(header).expandX().fillX().row()
        table.add(StatsTemplateView(model.value.stats, assets))

        actor = table
        setSize(prefWidth, prefHeight)
    }

    fun setAvailable(quantity: Quantity<EffectTemplate>) {

        header.setAvailable(quantity)
    }
}
