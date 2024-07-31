package com.myrran.view.zmain

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.book.PlayerSkillBook
import com.myrran.domain.skills.book.WorldSkillBook
import com.myrran.domain.skills.skills.buff.BuffSkillName
import com.myrran.domain.skills.skills.buff.BuffSkillSlotId
import com.myrran.domain.skills.skills.buff.BuffSkillSlotName
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.domain.skills.skills.skill.SkillName
import com.myrran.domain.skills.skills.stat.BonusPerUpgrade
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatBonus
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.StatName
import com.myrran.domain.skills.skills.stat.UpgradeCost
import com.myrran.domain.skills.skills.subskill.SubSkillName
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.skills.subskill.SubSkillSlotName
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.LockTypes
import com.myrran.domain.skills.templates.buff.BuffSkillSlotTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.stat.StatUpgradeableTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillSlotTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.spells.buff.BuffType
import com.myrran.domain.spells.spell.SkillType
import com.myrran.domain.spells.subspell.SubSkillType
import com.myrran.domain.utils.DeSerializer
import com.myrran.domain.utils.QuantityMap
import com.myrran.infraestructure.Repository
import com.myrran.infraestructure.adapters.SkillAdapter
import com.myrran.infraestructure.adapters.SkillBookAdapter
import com.myrran.infraestructure.adapters.SkillTemplateAdapter
import com.myrran.view.atlas.Atlas
import com.myrran.view.ui.WidgetText
import com.myrran.view.ui.skill.SkillView
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.SkillController
import ktx.app.KtxScreen

class MainScreen(

    private val inputMultiplexer: InputMultiplexer = InputMultiplexer(),
    private val batch: SpriteBatch = SpriteBatch(),
    private val uiStage: Stage = Stage(),
    private val atlas: Atlas = Atlas(
        assetManager = AssetManager()),

    private val repository: Repository = Repository(
        skillBookAdapter = SkillBookAdapter(
            skillAdapter = SkillAdapter(),
            skillTemplateAdapter = SkillTemplateAdapter()),
        deSerializer =  DeSerializer()),

    private val worldSkillBook: WorldSkillBook = repository.loadSkillBook(),

): KtxScreen
{
    private val fpsText: WidgetText<String>
    private val boltSkill: Skill

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        inputMultiplexer.addProcessor(uiStage)
        Gdx.input.inputProcessor = inputMultiplexer

        val initialAssets = repository.loadAssetCollection("InitialAssets.json")
        atlas.load(initialAssets)
        atlas.finishLoading()



        fpsText = WidgetText("FPS: ?", atlas.getFont("20.fnt"), shadowTickness = 2f, formater = {it.toString()})
        uiStage.addActor(fpsText)

        val bolt = SkillTemplate(
            id = SkillTemplateId("FIREBOLT_1"),
            type = SkillType.BOLT,
            name = SkillName("Fire bolt"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("1:SPEED"),
                    name = StatName("Speed"),
                    baseBonus = StatBonus(100.0f),
                    maximum = NumUpgrades(50),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
                StatUpgradeableTemplate(
                    id = StatId("2:COOLDOWN"),
                    name = StatName("Cooldown"),
                    baseBonus = StatBonus(20.0f),
                    maximum = NumUpgrades(25),
                    upgradeCost = UpgradeCost(1.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.5f)
                ),
                StatUpgradeableTemplate(
                    id = StatId("3:DAMAGE"),
                    name = StatName("Damage"),
                    baseBonus = StatBonus(30.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(3.0f),
                    bonusPerUpgrade = BonusPerUpgrade(2.5f)
                )
            ),
            slots = listOf(
                SubSkillSlotTemplate(
                    id = SubSkillSlotId("IMPACT"),
                    name = SubSkillSlotName("impact"),
                    lock = Lock(listOf(LockTypes.ALPHA, LockTypes.BETA))
                ),
                SubSkillSlotTemplate(
                    id = SubSkillSlotId("TRAIL"),
                    name = SubSkillSlotName("trail"),
                    lock = Lock(listOf(LockTypes.ALPHA, LockTypes.BETA))
                )
            )
        )

        val explosion = SubSkillTemplate(
            id = SubSkillTemplateId("EXPLOSION_1"),
            type = SubSkillType.EXPLOSION,
            name = SubSkillName("Explosion"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("RADIUS"),
                    name = StatName("radius"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                )
            ),
            slots = listOf(
                BuffSkillSlotTemplate(
                    id = BuffSkillSlotId("DEBUFF_1"),
                    name = BuffSkillSlotName("Debuff 1"),
                    lock = Lock(listOf(LockTypes.GAMMA, LockTypes.EPSILON)),
                ),
                BuffSkillSlotTemplate(
                    id = BuffSkillSlotId("DEBUFF_2"),
                    name = BuffSkillSlotName("Debuff 2"),
                    lock = Lock(listOf(LockTypes.GAMMA, LockTypes.EPSILON)),
                )
            ),
            keys = listOf(LockTypes.ALPHA)
        )

        val fire = BuffSkillTemplate(
            id = BuffSkillTemplateId("FIRE_1"),
            type = BuffType.FIRE,
            name = BuffSkillName("Fire"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("DAMAGE"),
                    name = StatName("damage per second"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
                StatUpgradeableTemplate(
                    id = StatId("DURATION"),
                    name = StatName("duration"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                )
            ),
            keys = listOf(LockTypes.GAMMA)
        )

        boltSkill = bolt.toSkill()
        val explosionSkill = explosion.toSubSkill()
        val fireBuff = fire.toBuffSkill();

        val playerSkillBook = PlayerSkillBook(QuantityMap(), QuantityMap(), QuantityMap(), mutableMapOf())
        playerSkillBook.learn(bolt.id)
        playerSkillBook.addSkill(boltSkill)
        playerSkillBook.learn(explosion.id)
        playerSkillBook.addSubSkillTo(boltSkill.id, SubSkillSlotId("IMPACT"), explosionSkill)
        playerSkillBook.learn(fire.id)
        playerSkillBook.addBuffSKillTo(boltSkill.id, SubSkillSlotId("IMPACT"), BuffSkillSlotId("DEBUFF_1"), fireBuff)
        playerSkillBook.upgrade(boltSkill.id, StatId("1:SPEED"), NumUpgrades(15))

        val controller = SkillController(playerSkillBook)

        val assets = SkillAssets(
            background = atlas.getNinePatchDrawable("Atlas.atlas","TexturasIconos/IconoVacioNine2", Color.WHITE, 0.90f),
            font14 = atlas.getFont("14.fnt"),
            font12 = atlas.getFont("Calibri12.fnt"),
            font10 =  atlas.getFont("Arial10.fnt"),
            statBarBack = atlas.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalentoFondo"),
            statBarFront = atlas.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalento"),
        )

        val skillView = SkillView(boltSkill, assets, controller)
        skillView.debug()
        skillView.setDebug(true, true)

        boltSkill.addObserver(skillView)

        val container = Container<Table>()
        container.actor = skillView

        println(skillView.minHeight)

        uiStage.addActor(container)
        container.setPosition(200f, 100f)

        uiStage.setDebugUnderMouse(true)
        uiStage.setDebugAll(true)

    }

    // RENDER:
    //--------------------------------------------------------------------------------------------------------

    override fun render(delta: Float) {

        clearScreen()

        batch.begin()
        renderWorld(delta)
        batch.end()

        uiStage.act()
        renderUI(delta)
        uiStage.draw()
    }

    private fun renderWorld(delta: Float) {

    }

    private fun renderUI(delta: Float) {

        fpsText.setText("Hola Mundo: ${Gdx.graphics.framesPerSecond}")
    }

    private fun clearScreen() {

        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun dispose() {

        batch.dispose()
        uiStage.dispose()
        atlas.dispose()
    }
}
