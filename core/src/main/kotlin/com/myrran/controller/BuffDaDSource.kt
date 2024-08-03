package com.myrran.controller

import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.templates.BuffTemplateView

class BuffDaDSource(

    private val view: BuffTemplateView,
    private val assets: SkillViewAssets

): Source(view.header.icon)
{
    override fun dragStart(event: InputEvent?, x: Float, y: Float, pointer: Int): Payload {

        val payload = Payload()
        payload.`object` = view.buff
        payload.dragActor = TextView(view.buff.name, assets.font20, ORANGE, 2f) { it.value }
        return payload
    }
}
