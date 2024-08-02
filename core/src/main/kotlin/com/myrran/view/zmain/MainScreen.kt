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
import com.myrran.domain.skills.custom.buff.BuffSkillName
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.buff.BuffSkillSlotName
import com.myrran.domain.skills.custom.skill.Skill
import com.myrran.domain.skills.custom.skill.SkillName
import com.myrran.domain.skills.custom.stat.BonusPerUpgrade
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatBonus
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.StatName
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.custom.subskill.SubSkillName
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotName
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.LockType
import com.myrran.domain.skills.templates.buff.BuffSkillSlotTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.stat.StatFixedTemplate
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
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.controller.SkillController
import com.myrran.view.ui.skills.custom.skill.SkillView
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
    private val fpsText: TextView<String>
    private val boltSkill: Skill

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        inputMultiplexer.addProcessor(uiStage)
        Gdx.input.inputProcessor = inputMultiplexer

        val initialAssets = repository.loadAssetCollection("InitialAssets.json")
        atlas.load(initialAssets)
        atlas.finishLoading()



        fpsText = TextView("FPS: ?", atlas.getFont("20.fnt"), shadowTickness = 2f, formater = {it.toString()})
        uiStage.addActor(fpsText)

        val bolt = SkillTemplate(
            id = SkillTemplateId("FIREBOLT_1"),
            type = SkillType.BOLT,
            name = SkillName("Fire Bolt"),
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
                    name = SubSkillSlotName("Impact"),
                    lock = Lock(listOf(LockType.ALPHA, LockType.BETA, LockType.GAMMA, LockType.EPSILON, LockType.OMEGA))
                ),
                SubSkillSlotTemplate(
                    id = SubSkillSlotId("TRAIL"),
                    name = SubSkillSlotName("Trail"),
                    lock = Lock(listOf(LockType.ALPHA, LockType.BETA))
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
                    name = StatName("Radius"),
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
                    lock = Lock(listOf(LockType.GAMMA, LockType.EPSILON)),
                ),
                BuffSkillSlotTemplate(
                    id = BuffSkillSlotId("DEBUFF_2"),
                    name = BuffSkillSlotName("Debuff 2"),
                    lock = Lock(listOf(LockType.GAMMA, LockType.EPSILON)),
                )
            ),
            keys = listOf(LockType.BETA, LockType.GAMMA)
        )

        val fire = BuffSkillTemplate(
            id = BuffSkillTemplateId("FIRE_1"),
            type = BuffType.FIRE,
            name = BuffSkillName("Fire"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("1:DAMAGE"),
                    name = StatName("Damage per tick"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
                StatUpgradeableTemplate(
                    id = StatId("2:DURATION"),
                    name = StatName("Duration"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
                StatUpgradeableTemplate(
                    id = StatId("3:SPEED"),
                    name = StatName("Speed"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
                StatFixedTemplate(
                    id = StatId("4:INITIAL_DAMAGE"),
                    name = StatName("Initial damage"),
                    baseBonus = StatBonus(10.0f),
                )
            ),
            keys = listOf(LockType.GAMMA)
        )

        boltSkill = bolt.toSkill()
        val explosionSkill = explosion.toSubSkill()
        val fireBuff = fire.toBuffSkill()

        val playerSkillBook = PlayerSkillBook(QuantityMap(), QuantityMap(), QuantityMap(), mutableMapOf())
        playerSkillBook.learn(bolt.id)
        playerSkillBook.addSkill(boltSkill)
        playerSkillBook.learn(explosion.id)
        playerSkillBook.addSubSkillTo(boltSkill.id, SubSkillSlotId("IMPACT"), explosionSkill)
        playerSkillBook.learn(fire.id)
        playerSkillBook.addBuffSKillTo(boltSkill.id, SubSkillSlotId("IMPACT"), BuffSkillSlotId("DEBUFF_1"), fireBuff)
        playerSkillBook.upgrade(boltSkill.id, StatId("1:SPEED"), NumUpgrades(15))

        val controller = SkillController(playerSkillBook)

        val assets = SkillViewAssets(
            skillIcon = atlas.getTextureRegion("Atlas.atlas", "TexturasIconos/FireBall"),
            tableBackgroundLightToDark = atlas.getNinePatchDrawable("Atlas.atlas", "TexturasIconos/NineLightToDark", Color.WHITE, 1f),
            tableBackgroundLight = atlas.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineLight", Color.WHITE, 0.90f),
            tableBackgroundDark = atlas.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineDark", Color.WHITE, 0.90f),
            font20 = atlas.getFont("20.fnt"),
            font14 = atlas.getFont("14.fnt"),
            font12 = atlas.getFont("14.fnt"),
            font10 =  atlas.getFont("14.fnt"),
            statBarBack = atlas.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalentoFondo"),
            statBarFront = atlas.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalento"),
        )

        val skillView = SkillView(boltSkill, assets, controller)
        //skillView.setDebug(true, true)

        val container = Container<Table>()
        container.actor = skillView

        uiStage.addActor(container)
        container.setPosition(200f, 100f)

        container.pack()

        //uiStage.setDebugUnderMouse(true)
        //uiStage.setDebugAll(true)

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

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.10f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun dispose() {

        batch.dispose()
        uiStage.dispose()
        atlas.dispose()
    }
}
