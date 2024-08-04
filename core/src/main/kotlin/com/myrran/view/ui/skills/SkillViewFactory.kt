package com.myrran.view.ui.skills

import com.myrran.controller.BuffDaDSource
import com.myrran.controller.BuffDaDTarget
import com.myrran.controller.BuffSKillController
import com.myrran.controller.DragAndDropManager
import com.myrran.controller.SkillController
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.skill.Skill
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.buff.BuffSkillSlotView
import com.myrran.view.ui.skills.custom.skill.SkillView
import com.myrran.view.ui.skills.templates.BuffTemplateView

class SkillViewFactory(

    private val dragAndDropManager: DragAndDropManager,
    private val assets: SkillViewAssets,
)
{
    fun createBuffTemplateView(model: BuffSkillTemplate): BuffTemplateView {

        val id = SkillViewId()
        val view = BuffTemplateView(id, model, assets)
        val dadSource = BuffDaDSource(model, view, assets)
        dragAndDropManager.addSource(dadSource)

        return view
    }

    fun disposeBuffTemplateView(view: BuffTemplateView) =

        dragAndDropManager.removeSource(view.id)

    fun createSkillView(model: Skill, controller: SkillController): SkillView {

        val id = SkillViewId()
        return SkillView(id, model, assets, controller, this)
    }

    fun createBuffSlotView(model: BuffSkillSlot, controller: BuffSKillController): BuffSkillSlotView {

        val id = SkillViewId()
        val view = BuffSkillSlotView(id, model, assets, controller)
        val dadTarget = BuffDaDTarget(view, assets, controller)
        dragAndDropManager.addTarget(dadTarget)

        return view;
    }

    fun disposeBuffSlotView(view: BuffSkillSlotView) {

        dragAndDropManager.removeTarget(view.id)
    }

}
