package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Limiter
import com.myrran.domain.mob.units.Acceleration
import com.myrran.domain.mob.units.AngularAcceleration
import com.myrran.domain.mob.units.AngularVelocity
import com.myrran.domain.mob.units.Meter
import com.myrran.domain.mob.units.Radian
import com.myrran.domain.mob.units.Speed

class SpeedLimits(

    private var linearSpeedGreeshold: Speed<Meter> = Speed(Meter(0.001f)),
    private var maxLinearSpeed: Speed<Meter> = Speed(Meter(50f)),
    private var maxLinearAcceleration: Acceleration<Meter> = Acceleration(Meter(200f)),
    private var maxAngularSpeed: AngularVelocity<Radian> = AngularVelocity(Radian(5f)),
    private var maxAngularAcceleration: AngularAcceleration<Radian> = AngularAcceleration(Radian(5f))

): Limiter
{
    override fun getZeroLinearSpeedThreshold(): Float =

        linearSpeedGreeshold.value.toFloat()

    override fun setZeroLinearSpeedThreshold(threesholdInMeters: Float) {

        linearSpeedGreeshold = Speed(Meter(threesholdInMeters)) }

    override fun getMaxLinearSpeed(): Float =

        maxLinearSpeed.value.toFloat()

    override fun setMaxLinearSpeed(maxSpeedInMeters: Float) {

        maxLinearSpeed = Speed(Meter(maxSpeedInMeters)) }

    override fun getMaxLinearAcceleration(): Float =

        maxLinearAcceleration.value.toFloat()

    override fun setMaxLinearAcceleration(maxAccelerationInMeters: Float) {

        maxLinearAcceleration = Acceleration(Meter(maxAccelerationInMeters))
    }

    override fun getMaxAngularSpeed(): Float =

        maxAngularSpeed.value.toFloat()

    override fun setMaxAngularSpeed(maxAngularSpeedInRadians: Float) {

        maxAngularSpeed = AngularVelocity(Radian(maxAngularSpeedInRadians)) }

    override fun getMaxAngularAcceleration(): Float =

        maxAngularAcceleration.value.toFloat()

    override fun setMaxAngularAcceleration(maxAngularAccelerationInRadians: Float) {

        maxAngularAcceleration = AngularAcceleration(Radian(maxAngularAccelerationInRadians)) }
}
