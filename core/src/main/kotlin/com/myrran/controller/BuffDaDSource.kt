package com.myrran.controller

import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.myrran.badlogic.DaDSource
import com.myrran.badlogic.Payload
import com.myrran.domain.Identifiable
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.templates.BuffTemplateView

class BuffDaDSource(

    private val model: BuffSkillTemplate,
    private val view: BuffTemplateView,
    private val assets: SkillViewAssets

): DaDSource, Identifiable<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getActor(): Actor = view.header.icon
    override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): Payload {

        val payload = Payload()
        payload.`object` = model
        payload.dragActor = TextView(model.name, assets.font20, ORANGE, 2f) { it.value }
        return payload
    }
}
