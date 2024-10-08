package com.myrran.infraestructure.controller.skills

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.badlogic.DaDSource
import com.myrran.badlogic.DaDTarget
import com.myrran.badlogic.Payload
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.infraestructure.view.skills.SkillViewAssets
import com.myrran.infraestructure.view.skills.SkillViewId
import com.myrran.infraestructure.view.skills.created.form.FormSkillSlotView

class FormDaDTarget(

    private val view: FormSkillSlotView,
    private val assets: SkillViewAssets,
    private val controller: FormSkillController

): DaDTarget, Identifiable<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getActor(): Actor = view.keyView
    override fun drag(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int): Boolean =

        true

    override fun drop(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int) {

        val template = payload.`object` as Quantity<FormTemplate>

        controller.setFormSkill(template.value.id)
    }

    override fun notifyNewPayload(payload: Payload) =

        when (controller.isOpenedBy((payload.`object` as Quantity<FormTemplate>).value.id)) {
            true -> view.keyView.highlightWithColor(Color.GREEN)
            false -> view.keyView.highlightWithColor(Color.RED)
        }

    override fun notifyNoPayload() {

        view.keyView.dontHighlight()
    }
}
