package com.myrran.infraestructure.controller.skills

import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.myrran.badlogic.DaDSource
import com.myrran.badlogic.Payload
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.infraestructure.view.skills.SkillViewAssets
import com.myrran.infraestructure.view.skills.SkillViewId
import com.myrran.infraestructure.view.skills.templates.effect.EffectTemplateView
import com.myrran.infraestructure.view.ui.TextView

class EffectDaDSource(

    private val model: Quantity<EffectTemplate>,
    private val view: EffectTemplateView,
    private val assets: SkillViewAssets

): DaDSource, Identifiable<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getActor(): Actor = view.header.icon
    override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): Payload {

        val payload = Payload()
        payload.`object` = model
        payload.dragActor = TextView(model.value.name, assets.font20, ORANGE, 2f) { it.value }
        return payload
    }
}
