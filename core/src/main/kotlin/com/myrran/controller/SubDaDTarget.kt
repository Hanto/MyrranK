package com.myrran.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.badlogic.DaDSource
import com.myrran.badlogic.DaDTarget
import com.myrran.badlogic.Payload
import com.myrran.domain.Identifiable
import com.myrran.domain.skills.templates.SubSkillTemplate
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.subskill.SubSkillSlotView

class SubDaDTarget(

    private val view: SubSkillSlotView,
    private val assets: SkillViewAssets,
    private val controller: SubSkillController

): DaDTarget, Identifiable<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getActor(): Actor = view.subSlotKeyView
    override fun drag(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int): Boolean =

        true

    override fun drop(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int) {

        val template = payload.`object` as SubSkillTemplate

        controller.setSubSkill(template.id)
    }

    override fun notifyNewPayload(payload: Payload) =

        when (controller.isOpenedBy((payload.`object` as SubSkillTemplate).id)) {
            true -> view.subSlotKeyView.highlightWithColor(Color.GREEN)
            false -> view.subSlotKeyView.highlightWithColor(Color.RED)
        }

    override fun notifyNoPayload() {

        view.subSlotKeyView.dontHighlight()
    }
}
