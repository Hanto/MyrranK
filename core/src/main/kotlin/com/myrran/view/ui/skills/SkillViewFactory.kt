package com.myrran.view.ui.skills

import com.myrran.controller.BookSkillController
import com.myrran.controller.BuffDaDSource
import com.myrran.controller.BuffDaDTarget
import com.myrran.controller.DragAndDropManager
import com.myrran.controller.SkillController
import com.myrran.controller.SubDaDSource
import com.myrran.controller.SubDaDTarget
import com.myrran.controller.SubSkillController
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.created.Skill
import com.myrran.domain.skills.created.buff.BuffSkillSlot
import com.myrran.domain.skills.created.subskill.SubSkillSlot
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.domain.skills.templates.SkillTemplate
import com.myrran.domain.skills.templates.SubSkillTemplate
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.created.buff.BuffSkillSlotView
import com.myrran.view.ui.skills.created.skill.SkillView
import com.myrran.view.ui.skills.created.subskill.SubSkillSlotView
import com.myrran.view.ui.skills.templates.buff.BuffTemplateView
import com.myrran.view.ui.skills.templates.skill.SkillTemplateView
import com.myrran.view.ui.skills.templates.subskill.SubSkillTemplateView

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

    // SKILL TEMPLATE:
    //--------------------------------------------------------------------------------------------------------

    fun createSkillTemplateView(model: Quantity<SkillTemplate>, controller: BookSkillController): SkillTemplateView {

        val id = SkillViewId()
        val skillTemplateController = controller.toSkillTemplateController()
        val view = SkillTemplateView(id, model, assets, skillTemplateController)

        return view
    }

    // SUBSKILL TEMPLATE:
    //--------------------------------------------------------------------------------------------------------

    fun createSubTemplateView(model: Quantity<SubSkillTemplate>): SubSkillTemplateView {

        val id = SkillViewId()
        val view = SubSkillTemplateView(id, model, assets)
        val dadSource = SubDaDSource(model, view, assets)
        dragAndDropManager.addSource(dadSource)

        return view
    }

    // BUFF TEMPLATE:
    //--------------------------------------------------------------------------------------------------------

    fun createBuffTemplateView(model: Quantity<BuffSkillTemplate>): BuffTemplateView {

        val id = SkillViewId()
        val view = BuffTemplateView(id, model, assets)
        val dadSource = BuffDaDSource(model, view, assets)
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

    // SUBSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun createSubSlotView(model: SubSkillSlot, controller: SkillController): SubSkillSlotView {

        val id = SkillViewId()
        val subController = controller.toSubSkillController(model)
        val view = SubSkillSlotView(id, model, assets, subController, this)
        val dadTarget = SubDaDTarget(view, assets, subController)
        dragAndDropManager.addTarget(dadTarget)

        return view
    }

    // BUFFSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun createBuffSlotView(model: BuffSkillSlot, controller: SubSkillController): BuffSkillSlotView {

        val id = SkillViewId()
        val buffController = controller.toBuffSkillController(model)
        val view = BuffSkillSlotView(id, model, assets, buffController)
        val dadTarget = BuffDaDTarget(view, assets, buffController)
        dragAndDropManager.addTarget(dadTarget)

        return view
    }
}
