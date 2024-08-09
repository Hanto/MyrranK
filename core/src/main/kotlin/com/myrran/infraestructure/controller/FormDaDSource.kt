package com.myrran.infraestructure.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.myrran.badlogic.DaDSource
import com.myrran.badlogic.Payload
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.infraestructure.view.ui.misc.TextView
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.templates.form.FormTemplateView

class FormDaDSource(

    private val model: Quantity<FormTemplate>,
    private val view: FormTemplateView,
    private val assets: SkillViewAssets

): DaDSource, Identifiable<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getActor(): Actor = view.header.icon
    override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): Payload {

        val payload = Payload()
        payload.`object` = model
        payload.dragActor = TextView(model.value.name, assets.font20, Color.ORANGE, 2f) { it.value }
        return payload
    }
}
