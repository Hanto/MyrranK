package com.myrran.infraestructure.view.mobs.common

import box2dLight.ConeLight
import box2dLight.PointLight
import box2dLight.RayHandler
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.physics.box2d.Filter
import com.myrran.domain.entities.common.corporeal.Box2dFilters.Companion.ENEMY
import com.myrran.domain.entities.common.corporeal.Box2dFilters.Companion.LIGHT_PLAYER
import com.myrran.domain.entities.common.corporeal.Box2dFilters.Companion.LIGHT_SPELLS
import com.myrran.domain.entities.common.corporeal.Box2dFilters.Companion.PLAYER
import com.myrran.domain.entities.common.corporeal.Box2dFilters.Companion.WALLS
import com.myrran.domain.entities.mob.enemy.Enemy
import com.myrran.domain.entities.mob.player.Player
import com.myrran.domain.entities.mob.spells.form.Form
import com.myrran.domain.entities.mob.spells.form.FormCircle
import com.myrran.domain.entities.mob.spells.form.FormPoint
import com.myrran.domain.entities.mob.spells.spell.Spell
import com.myrran.domain.entities.mob.spells.spell.SpellBolt
import com.myrran.domain.misc.metrics.Pixel
import com.myrran.domain.misc.metrics.SizePixels
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.view.mobs.enemy.EnemyView
import com.myrran.infraestructure.view.mobs.enemy.EnemyViewAssets
import com.myrran.infraestructure.view.mobs.player.EnemyAnimation
import com.myrran.infraestructure.view.mobs.player.PlayerAnimation
import com.myrran.infraestructure.view.mobs.player.PlayerView
import com.myrran.infraestructure.view.mobs.player.PlayerViewAssets
import com.myrran.infraestructure.view.mobs.spells.SpellViewAssets
import com.myrran.infraestructure.view.mobs.spells.form.FormCircleView
import com.myrran.infraestructure.view.mobs.spells.form.FormPointView
import com.myrran.infraestructure.view.mobs.spells.spell.SpellAnimation
import com.myrran.infraestructure.view.mobs.spells.spell.SpellBoltView
import ktx.collections.toGdxArray
import kotlin.experimental.or

class MobViewFactory(

    private val playerAssets: PlayerViewAssets,
    private val enemyAssets: EnemyViewAssets,
    private val spellAssets: SpellViewAssets,
    private val rayHandler: RayHandler,
    private val eventDispatcher: EventDispatcher
)
{
    companion object {

        private val size = SizePixels(32, 32)
    }

    // PLAYER:
    //--------------------------------------------------------------------------------------------------------

    fun createPlayer(model: Player): PlayerView {

        val frames = playerAssets.character.split(size.width.value(), size.height.value())
        val characterAnimations = mapOf(
            PlayerAnimation.WALK_SOUTH to Animation(0.2f, arrayOf(frames[0][0], frames[0][1], frames[0][2]).toGdxArray()),
            PlayerAnimation.WALK_WEST to Animation(0.2f, arrayOf(frames[1][0], frames[1][1], frames[1][2]).toGdxArray()),
            PlayerAnimation.WALK_EAST to Animation(0.2f, arrayOf(frames[2][0], frames[2][1], frames[2][2]).toGdxArray()),
            PlayerAnimation.WALK_NORTH to Animation(0.2f, arrayOf(frames[3][0], frames[3][1], frames[3][2]).toGdxArray()),
            PlayerAnimation.IDDLE to Animation(0.5f, arrayOf(frames[2][3], frames[2][4], frames[2][5]).toGdxArray()),
            PlayerAnimation.CASTING to Animation(0.25f, arrayOf(frames[4][6]).toGdxArray()) )
        val characterSprite = SpriteAnimated(characterAnimations, PlayerAnimation.IDDLE)

        val shadow = SpriteStatic(playerAssets.shadow)

        val filter = Filter()
            .also { it.categoryBits = LIGHT_PLAYER }
            .also { it.maskBits = PLAYER or ENEMY or WALLS}

        val light = PointLight(rayHandler, 300)
            .also { it.setContactFilter(filter) }
            .also { it.isSoft = true }
            .also { it.setSoftnessLength(10f) }
            .also { it.ignoreAttachedBody }

        model.steerable.attachLight(light)

        val castingBar = CastingBar(model, playerAssets.nameplateForeground, playerAssets.nameplateBackground)
        val healthBar = HealthBar(model, playerAssets.nameplateForeground, playerAssets.nameplateBackground)

        return PlayerView(model, characterSprite, shadow, castingBar, healthBar, light)
    }

    // ENEMY:
    //--------------------------------------------------------------------------------------------------------

    fun createEnemy(model: Enemy): EnemyView {

        val frames = enemyAssets.enemy.split(size.width.value(), size.height.value())
        val enemyAnimations = mapOf(
            EnemyAnimation.WALK_SOUTH to Animation(0.2f, arrayOf(frames[0][0], frames[0][1], frames[0][2]).toGdxArray()),
            EnemyAnimation.WALK_WEST to Animation(0.2f, arrayOf(frames[1][0], frames[1][1], frames[1][2]).toGdxArray()),
            EnemyAnimation.WALK_EAST to Animation(0.2f, arrayOf(frames[2][0], frames[2][1], frames[2][2]).toGdxArray()),
            EnemyAnimation.WALK_NORTH to Animation(0.2f, arrayOf(frames[3][0], frames[3][1], frames[3][2]).toGdxArray()),)
        val enemySprite = SpriteAnimated(enemyAnimations, EnemyAnimation.WALK_EAST)

        val shadow = SpriteStatic(enemyAssets.shadow)

        val filter = Filter()
            .also { it.categoryBits = LIGHT_PLAYER }
            .also { it.maskBits = WALLS}

        val lineOfSightLight = ConeLight(rayHandler, 300, Color(0.75f, 0.75f, 0.5f, 0.1f), 10f, 0f, 0f, 0f, 22.5f)
            .also { it.setContactFilter(filter) }
            //.also { it.isSoft = true }
            //.also { it.setSoftnessLength(10f) }
            .also { it.ignoreAttachedBody }

        model.steerable.attachLight(lineOfSightLight)

        val healthBar = HealthBar(model, playerAssets.nameplateForeground, playerAssets.nameplateBackground)
        val effectsView = EffectsView(model, spellAssets)

        return EnemyView(model, enemySprite, shadow, healthBar, effectsView, lineOfSightLight, eventDispatcher)
    }

    // SPELLS:
    //--------------------------------------------------------------------------------------------------------

    fun createSpell(spell: Spell): MobView =

        when (spell) {
            is SpellBolt -> createSpellBolt(spell)
        }

    private fun createSpellBolt(model: SpellBolt): SpellBoltView {

        val frames = spellAssets.spellBolt.split(size.width.value(), size.height.value())
        val animations = mapOf(
            SpellAnimation.GLOW to Animation(0.1f,  arrayOf(frames[0][3], frames[0][4], frames[0][5], frames[0][4]).toGdxArray()))

        val filter = Filter()
            .also { it.categoryBits = LIGHT_SPELLS }
            .also { it.maskBits = WALLS }

        val light = PointLight(rayHandler, 50)
            .also { it.setContactFilter(filter) }
            .also { it.isSoft = true }
            .also { it.setSoftnessLength(20f) }
            .also { it.color = Color(0.6f, 0.0f, 0.0f, 0.5f) }
            .also { it.distance = Pixel(256).toBox2DUnits() }
            .also { it.ignoreAttachedBody = true }

        model.steerable.attachLight(light)

        return SpellBoltView(model, light, animations)
    }

    // FORMS:
    //--------------------------------------------------------------------------------------------------------

    fun createForm(form: Form): MobView =
        when (form) {
            is FormCircle -> createFormCircle(form)
            is FormPoint -> createFormPoint(form)
        }

    private fun createFormCircle(model: FormCircle): FormCircleView {

        val frames = spellAssets.formCircle.split(size.width.value(), size.height.value())
        val animations = mapOf(
            SpellAnimation.GLOW to Animation(0.1f,  arrayOf(frames[0][3], frames[0][4], frames[0][5], frames[0][4]).toGdxArray()))
        val formSprite = SpriteAnimated(animations, SpellAnimation.GLOW)

        return FormCircleView(model, formSprite)
    }

    private fun createFormPoint(model: FormPoint): FormPointView {

        val frames = spellAssets.formPoint.split(size.width.value(), size.height.value())
        val animations = mapOf(
            SpellAnimation.GLOW to Animation(0.1f,  arrayOf(frames[0][3], frames[0][4], frames[0][5], frames[0][4]).toGdxArray()))
        val formSprite = SpriteAnimated(animations, SpellAnimation.GLOW)

        return FormPointView(model, formSprite)
    }
}
