package com.myrran.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.myrran.badlogic.DaDSource
import com.myrran.badlogic.Payload
import com.myrran.domain.Identifiable
import com.myrran.domain.skills.templates.SubSkillTemplate
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.templates.SubSkillTemplateView

class SubDaDSource(

    private val model: SubSkillTemplate,
    private val view: SubSkillTemplateView,
    private val assets: SkillViewAssets

): DaDSource, Identifiable<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getActor(): Actor = view.header.icon
    override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): Payload {

        val payload = Payload()
        payload.`object` = model
        payload.dragActor = TextView(model.name, assets.font20, Color.ORANGE, 2f) { it.value }
        return payload
    }
}
