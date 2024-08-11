package com.myrran.infraestructure.view.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.myrran.application.SpellBook
import com.myrran.infraestructure.controller.SpellBookController
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.skill.SkillViews
import com.myrran.infraestructure.view.ui.skills.templates.effect.EffectTemplateViews
import com.myrran.infraestructure.view.ui.skills.templates.form.FormTemplateViews
import com.myrran.infraestructure.view.ui.skills.templates.skill.SkillTemplateViews

class UIView(

    private val spellBook: SpellBook,
    private val stage: Stage,
    private val assets: SkillViewAssets,
    private val bookController: SpellBookController,
    private val skillViewFactory: SkillViewFactory

): Disposable
{
    private val skillTemplates = SkillTemplateViews(SkillViewId(), spellBook, assets, bookController, skillViewFactory)
    private val effectTemplates = EffectTemplateViews(SkillViewId(), spellBook, assets, skillViewFactory)
    private val formTemplates = FormTemplateViews(SkillViewId(), spellBook, assets, skillViewFactory)
    private val createdSkills = SkillViews(SkillViewId(), spellBook, assets, bookController, skillViewFactory)

    init {

        stage.addActor(skillTemplates)
        stage.addActor(formTemplates)
        stage.addActor(effectTemplates)
        stage.addActor(createdSkills)

        effectTemplates.setPosition(533f, 154f)
        formTemplates.setPosition(268f, 154f)
        skillTemplates.setPosition(3f, 154f)
        createdSkills.setPosition(860f, 154f)
    }

    fun render(deltaTime: Float) {

        stage.act(deltaTime)

        stage.draw()
    }

    fun resize(width: Int, height: Int) {

        stage.viewport.update(Gdx.graphics.width, Gdx.graphics.height)
    }

    override fun dispose() {

        stage.dispose()
    }
}
