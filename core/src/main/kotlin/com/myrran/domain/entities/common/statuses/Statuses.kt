package com.myrran.domain.entities.common.statuses

interface Statuses
{
    fun slowMagnitude(): Float
    fun isFreezable(): Boolean
    fun vulnerableTo(damageFamily: Status.DamageFamily): Long
}
