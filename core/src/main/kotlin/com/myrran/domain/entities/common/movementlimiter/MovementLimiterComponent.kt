package com.myrran.domain.entities.common.movementlimiter

import com.myrran.domain.misc.metrics.Acceleration
import com.myrran.domain.misc.metrics.AngularAcceleration
import com.myrran.domain.misc.metrics.AngularVelocity
import com.myrran.domain.misc.metrics.Meter
import com.myrran.domain.misc.metrics.Radian
import com.myrran.domain.misc.metrics.Speed

class MovementLimiterComponent(

    private var zeroLinearSpeedTreshold: Speed<Meter> = Speed(Meter(0.001f)),
    private var maxLinearSpeed: Speed<Meter> = Speed(Meter(4f)),
    private var maxLinearAcceleration: Acceleration<Meter> = Acceleration(Meter(12f)),
    private var maxAngularSpeed: AngularVelocity<Radian> = AngularVelocity(Radian(6f)),
    private var maxAngularAcceleration: AngularAcceleration<Radian> = AngularAcceleration(Radian(12f))

): MovementLimiter
{
    override var slowModifier: Float = 1f

    override fun getZeroLinearSpeedThreshold(): Float =

        zeroLinearSpeedTreshold.value.toFloat()

    override fun setZeroLinearSpeedThreshold(threesholdInMeters: Float) {

        zeroLinearSpeedTreshold = Speed(Meter(threesholdInMeters)) }

    override fun getMaxLinearSpeed(): Float =

        maxLinearSpeed.value.toFloat() * slowModifier

    override fun setMaxLinearSpeed(maxSpeedInMeters: Float) {

        maxLinearSpeed = Speed(Meter(maxSpeedInMeters)) }

    override fun getMaxLinearAcceleration(): Float =

        maxLinearAcceleration.value.toFloat()

    override fun setMaxLinearAcceleration(maxAccelerationInMeters: Float) {

        maxLinearAcceleration = Acceleration(Meter(maxAccelerationInMeters)) }

    override fun getMaxAngularSpeed(): Float =

        maxAngularSpeed.value.toFloat()

    override fun setMaxAngularSpeed(maxAngularSpeedInRadians: Float) {

        maxAngularSpeed = AngularVelocity(Radian(maxAngularSpeedInRadians)) }

    override fun getMaxAngularAcceleration(): Float =

        maxAngularAcceleration.value.toFloat()

    override fun setMaxAngularAcceleration(maxAngularAccelerationInRadians: Float) {

        maxAngularAcceleration = AngularAcceleration(Radian(maxAngularAccelerationInRadians)) }
}
