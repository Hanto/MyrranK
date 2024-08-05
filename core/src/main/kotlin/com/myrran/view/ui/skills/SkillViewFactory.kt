package com.myrran.view.ui.skills

import com.myrran.controller.BuffDaDSource
import com.myrran.controller.BuffDaDTarget
import com.myrran.controller.DragAndDropManager
import com.myrran.controller.SkillController
import com.myrran.controller.SubSkillController
import com.myrran.domain.skills.custom.Skill
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.buff.BuffSkillSlotView
import com.myrran.view.ui.skills.custom.skill.SkillView
import com.myrran.view.ui.skills.custom.subskill.SubSkillSlotView
import com.myrran.view.ui.skills.templates.BuffTemplateView

class SkillViewFactory(

    private val dragAndDropManager: DragAndDropManager,
    private val assets: SkillViewAssets,
)
{
    // BUFF TEMPLATE:
    //--------------------------------------------------------------------------------------------------------

    fun createBuffTemplateView(model: BuffSkillTemplate): BuffTemplateView {

        val id = SkillViewId()
        val view = BuffTemplateView(id, model, assets)
        val dadSource = BuffDaDSource(model, view, assets)
        dragAndDropManager.addSource(dadSource)

        return view
    }

    fun disposeBuffTemplateView(view: BuffTemplateView) =

        dragAndDropManager.removeSource(view.id)

    // SKILL:
    //--------------------------------------------------------------------------------------------------------

    fun createSkillView(model: Skill, controller: SkillController): SkillView {

        val id = SkillViewId()
        return SkillView(id, model, assets, controller, this)
    }

    // SUBSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun createSubSlotView(model: SubSkillSlot, controller: SkillController): SubSkillSlotView {

        val id = SkillViewId()
        val subController = controller.toSubSkillController(model)
        return SubSkillSlotView(id, model, assets, subController, this)
    }

    // BUFFSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun createBuffSlotView(model: BuffSkillSlot, controller: SubSkillController): BuffSkillSlotView {

        val id = SkillViewId()
        val buffController = controller.toBuffSkillController(model)
        val view = BuffSkillSlotView(id, model, assets, buffController)
        val dadTarget = BuffDaDTarget(view, assets, buffController)
        dragAndDropManager.addTarget(dadTarget)

        return view;
    }

    fun disposeBuffSlotView(view: BuffSkillSlotView) {

        dragAndDropManager.removeTarget(view.id)
    }
}
