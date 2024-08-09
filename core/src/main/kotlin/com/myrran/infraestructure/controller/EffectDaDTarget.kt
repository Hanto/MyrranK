package com.myrran.infraestructure.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.badlogic.DaDSource
import com.myrran.badlogic.DaDTarget
import com.myrran.badlogic.Payload
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.effect.EffectSkillSlotView

class EffectDaDTarget(

    private val view: EffectSkillSlotView,
    private val assets: SkillViewAssets,
    private val controller: EffectSKillController,

): DaDTarget, Identifiable<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getActor(): Actor = view.keyView

    override fun drag(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int): Boolean =

        true

    override fun drop(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int) {

        val template = payload.`object` as Quantity<EffectTemplate>

        controller.setEffectSkill(template.value.id)
    }

    override fun notifyNewPayload(payload: Payload) =

        when (controller.isOpenedBy((payload.`object` as Quantity<EffectTemplate>).value.id)) {
            true -> view.keyView.highlightWithColor(Color.GREEN)
            false -> view.keyView.highlightWithColor(Color.RED)
        }

    override fun notifyNoPayload() {

        view.keyView.dontHighlight()
    }
}
