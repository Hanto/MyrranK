package com.myrran.infraestructure.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.skills.SpellBook
import com.myrran.infraestructure.controller.skills.SpellBookController
import com.myrran.infraestructure.view.skills.SkillViewAssets
import com.myrran.infraestructure.view.skills.SkillViewFactory
import com.myrran.infraestructure.view.skills.SkillViewId
import com.myrran.infraestructure.view.skills.created.skill.SkillViews
import com.myrran.infraestructure.view.skills.templates.effect.EffectTemplateViews
import com.myrran.infraestructure.view.skills.templates.form.FormTemplateViews
import com.myrran.infraestructure.view.skills.templates.skill.SkillTemplateViews
import com.myrran.infraestructure.view.ui.TextView

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
    private val fps = TextView("FPS: ", assets.font20)

    init {

        stage.addActor(skillTemplates)
        stage.addActor(formTemplates)
        stage.addActor(effectTemplates)
        stage.addActor(createdSkills)
        stage.addActor(fps).also { fps.setPosition(10f ,2f) }

        effectTemplates.setPosition(533f, 154f)
        formTemplates.setPosition(268f, 154f)
        skillTemplates.setPosition(3f, 154f)
        createdSkills.setPosition(860f, 154f)
    }

    fun render(deltaTime: Float) {

        stage.act(deltaTime)

        fps.setText("FPS: ${Gdx.graphics.framesPerSecond}")

        stage.draw()
    }

    fun resize(width: Int, height: Int) {

        stage.viewport.update(Gdx.graphics.width, Gdx.graphics.height)
    }

    override fun dispose() {

        stage.dispose()
    }
}
