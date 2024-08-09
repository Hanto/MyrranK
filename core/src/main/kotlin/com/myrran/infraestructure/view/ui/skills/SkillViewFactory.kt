package com.myrran.infraestructure.view.ui.skills

import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.created.effect.EffectSkillSlot
import com.myrran.domain.skills.created.form.FormSkillSlot
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.infraestructure.controller.BookSkillController
import com.myrran.infraestructure.controller.DragAndDropManager
import com.myrran.infraestructure.controller.EffectDaDSource
import com.myrran.infraestructure.controller.EffectDaDTarget
import com.myrran.infraestructure.controller.FormDaDSource
import com.myrran.infraestructure.controller.FormDaDTarget
import com.myrran.infraestructure.controller.FormSkillController
import com.myrran.infraestructure.controller.SkillController
import com.myrran.infraestructure.view.ui.skills.created.effect.EffectSkillSlotView
import com.myrran.infraestructure.view.ui.skills.created.form.FormSkillSlotView
import com.myrran.infraestructure.view.ui.skills.created.skill.SkillView
import com.myrran.infraestructure.view.ui.skills.templates.effect.EffectTemplateView
import com.myrran.infraestructure.view.ui.skills.templates.form.FormTemplateView
import com.myrran.infraestructure.view.ui.skills.templates.skill.SkillTemplateView

class SkillViewFactory(

    private val dragAndDropManager: DragAndDropManager,
    private val assets: SkillViewAssets,
)
{
    // DISPOSE:
    //--------------------------------------------------------------------------------------------------------

    fun disposeView(id: SkillViewId) {

        dragAndDropManager.removeSource(id)
        dragAndDropManager.removeTarget(id)
    }

    // SKILLTEMPLATE:
    //--------------------------------------------------------------------------------------------------------

    fun createSkillTemplateView(model: Quantity<SkillTemplate>, controller: BookSkillController): SkillTemplateView {

        val id = SkillViewId()
        val skillTemplateController = controller.toSkillTemplateController()
        val view = SkillTemplateView(id, model, assets, skillTemplateController)

        return view
    }

    // FORMTEMPLATE:
    //--------------------------------------------------------------------------------------------------------

    fun createFormTemplateView(model: Quantity<FormTemplate>): FormTemplateView {

        val id = SkillViewId()
        val view = FormTemplateView(id, model, assets)
        val dadSource = FormDaDSource(model, view, assets)
        dragAndDropManager.addSource(dadSource)

        return view
    }

    // EFFECTTEMPLATE:
    //--------------------------------------------------------------------------------------------------------

    fun createEffectTemplateView(model: Quantity<EffectTemplate>): EffectTemplateView {

        val id = SkillViewId()
        val view = EffectTemplateView(id, model, assets)
        val dadSource = EffectDaDSource(model, view, assets)
        dragAndDropManager.addSource(dadSource)

        return view
    }

    // SKILL:
    //--------------------------------------------------------------------------------------------------------

    fun createSkillView(model: Skill, controller: BookSkillController): SkillView {

        val id = SkillViewId()
        val skillController = controller.toSkillController(model)
        val view = SkillView(id, model, assets, skillController, this)

        return view
    }

    // FORMSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun createFormSlotView(model: FormSkillSlot, controller: SkillController): FormSkillSlotView {

        val id = SkillViewId()
        val formController = controller.toFormSkillController(model)
        val view = FormSkillSlotView(id, model, assets, formController, this)
        val dadTarget = FormDaDTarget(view, assets, formController)
        dragAndDropManager.addTarget(dadTarget)

        return view
    }

    // EFFECTSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun createEffectSlotView(model: EffectSkillSlot, controller: FormSkillController): EffectSkillSlotView {

        val id = SkillViewId()
        val effectController = controller.toEffectSkillController(model)
        val view = EffectSkillSlotView(id, model, assets, effectController, this)
        val dadTarget = EffectDaDTarget(view, assets, effectController)
        dragAndDropManager.addTarget(dadTarget)

        return view
    }
}
