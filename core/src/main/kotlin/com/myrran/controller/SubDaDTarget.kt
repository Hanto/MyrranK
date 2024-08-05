package com.myrran.controller

import com.badlogic.gdx.graphics.Color
import com.myrran.badlogic.Payload
import com.myrran.badlogic.Source
import com.myrran.badlogic.Target
import com.myrran.domain.skills.templates.SubSkillTemplate
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.subskill.SubSkillSlotView

class SubDaDTarget(

    private val view: SubSkillSlotView,
    private val assets: SkillViewAssets,
    private val controller: SubSkillController

): Target(view.subSlotKeyView), DaDTarget<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getTarget(): Target = this

    override fun drag(source: Source, payload: Payload, x: Float, y: Float, pointer: Int): Boolean =

        true

    override fun drop(source: Source, payload: Payload, x: Float, y: Float, pointer: Int) {

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
