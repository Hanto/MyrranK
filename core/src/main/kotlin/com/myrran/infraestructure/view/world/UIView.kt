package com.myrran.infraestructure.view.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.myrran.application.LearnedSkillTemplates
import com.myrran.application.SpellBook
import com.myrran.badlogic.DaD
import com.myrran.domain.misc.DeSerializer
import com.myrran.infraestructure.assets.AssetStorage
import com.myrran.infraestructure.controller.BookSkillController
import com.myrran.infraestructure.controller.DragAndDropManager
import com.myrran.infraestructure.repositories.learnedskilltemplate.LearnedSkillTemplateRepository
import com.myrran.infraestructure.repositories.skill.SkillAdapter
import com.myrran.infraestructure.repositories.skill.SkillRepository
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateAdapter
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateRepository
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.skill.SkillViews
import com.myrran.infraestructure.view.ui.skills.templates.effect.EffectTemplateViews
import com.myrran.infraestructure.view.ui.skills.templates.form.FormTemplateViews
import com.myrran.infraestructure.view.ui.skills.templates.skill.SkillTemplateViews
import java.util.UUID

class UIView(

    private val stage: Stage,
    private val assetStorage: AssetStorage

): Disposable
{
    private val skillTemplateRepository: SkillTemplateRepository
    private val skillRepository: SkillRepository
    private val learnedRepository: LearnedSkillTemplateRepository
    private val learnedTemplates: LearnedSkillTemplates
    private val spellBook: SpellBook

    init {

        val deSerializer = DeSerializer()
        val skillAdapter = SkillAdapter()
        skillRepository = SkillRepository(skillAdapter, deSerializer)
        learnedRepository = LearnedSkillTemplateRepository(deSerializer)
        val skillTemplateAdapter = SkillTemplateAdapter()
        skillTemplateRepository = SkillTemplateRepository(skillTemplateAdapter, deSerializer)
        learnedTemplates = LearnedSkillTemplates(learnedRepository, skillTemplateRepository)
        spellBook = SpellBook(skillRepository, learnedTemplates)

        val assets = SkillViewAssets(
            font20 = assetStorage.getFont("20.fnt"),
            font14 = assetStorage.getFont("14.fnt"),
            font12 = assetStorage.getFont("Arial12.fnt"),
            font10 =  assetStorage.getFont("Arial10.fnt"),
            statBarBack = assetStorage.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalentoFondo"),
            statBarFront = assetStorage.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalento"),
            skillHeader = assetStorage.getNinePatchDrawable("Atlas.atlas", "TexturasIconos/NineLightToDark", Color(0.7f, 0.7f, 0.7f, 1.0f), 0.90f),
            templateHeader = assetStorage.getNinePatchDrawable("Atlas.atlas", "TexturasIconos/NineLightToDark", Color(0.7f, 0.7f, 0.7f, 1.0f), 0.90f),
            containerHeader = assetStorage.getNinePatchDrawable("Atlas.atlas", "TexturasIconos/NineLightToDark", Color(0.6f, 0.6f, 0.6f, 1.0f), 0.90f),
            containerBackground = assetStorage.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineLight", Color.WHITE, 0.90f),
            tableBackground = assetStorage.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineLight", Color.WHITE, 0.90f),
            tooltipBackground = assetStorage.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineLight", Color.WHITE, 0.95f),
            iconTextures = mapOf(
                "SkillIcon" to assetStorage.getTextureRegion("Atlas.atlas", "TexturasIconos/FireBall"),
                "EffectIcon" to assetStorage.getTextureRegion("Atlas.atlas", "TexturasIconos/Editar"),
                "FormIcon" to assetStorage.getTextureRegion("Atlas.atlas", "TexturasIconos/Muros")
            )
        )

        val dragAndDropManager = DragAndDropManager(DaD(), DaD())
        val skillViewFactory = SkillViewFactory(dragAndDropManager, assets)
        val bookController = BookSkillController(spellBook)

        val effectList = EffectTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, skillViewFactory)
        stage.addActor(effectList)
        effectList.setPosition(533f, 154f)

        val formList = FormTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, skillViewFactory)
        stage.addActor(formList)
        formList.setPosition(268f, 154f)

        val skillTemplateList = SkillTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, bookController, skillViewFactory)
        stage.addActor(skillTemplateList)
        skillTemplateList.setPosition(3f, 154f)

        val skillList = SkillViews(SkillViewId(UUID.randomUUID()), spellBook, assets, bookController, skillViewFactory)
        stage.addActor(skillList)
        skillList.setPosition(860f, 154f)

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
