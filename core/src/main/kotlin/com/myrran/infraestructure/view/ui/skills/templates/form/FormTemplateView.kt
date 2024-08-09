package com.myrran.infraestructure.view.ui.skills.templates.form

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.templates.stat.StatsTemplateView

class FormTemplateView(

    override val id: SkillViewId,
    val model: Quantity<FormTemplate>,
    private val assets: SkillViewAssets,

): Container<Table>(), Identifiable<SkillViewId>
{
    val header = FormTemplateHeaderView(model, assets)

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

    fun setAvailable(quantity: Quantity<FormTemplate>) {

        header.setAvailable(quantity)
    }
}
