package com.myrran.domain.mob.player

import com.badlogic.gdx.math.Vector2
import com.myrran.application.World
import com.myrran.domain.mob.Mob
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.Movable
import com.myrran.domain.mob.metrics.Pixel
import com.myrran.domain.mob.steerable.SteeringComponent
import com.myrran.infraestructure.controller.PlayerInputs

data class Player(

    override val id: MobId,
    private val movable: SteeringComponent,
    var state: State = StateIddle(Vector2(0f, 0f))

): Movable by movable, Mob
{
    override var toBeRemoved: Boolean = false
    private var doCast = false
    private var pointingAt: Vector2 = Vector2(0f, 0f)

    fun applyInputs(inputs: PlayerInputs) {

        state = state.nextState(inputs)
        val force = state.direction.cpy().scl(1000f)
        movable.setLinearVelocity(state.direction, maxLinearSpeed)
        //movable.spatial.applyForceToCenter(force)

        doCast = inputs.doCast
        pointingAt = inputs.touchedWorld.toBox2dUnits()
    }


    override fun act(deltaTime: Float, world: World) {

        if (doCast) {

            val characterCenter = Vector2(position.x + Pixel(16).toBox2DUnits(), position.y + Pixel(16).toBox2DUnits())

            val skill = world.spellBook.created.findAll().first()
            val spell = world.mobFactory.createSpell(skill, characterCenter, pointingAt)

            world.addMob(spell)

            doCast = false
        }
    }
}
