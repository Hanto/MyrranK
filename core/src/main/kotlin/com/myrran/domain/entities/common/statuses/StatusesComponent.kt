package com.myrran.domain.entities.common.statuses

import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.statuses.Status.Chilled
import com.myrran.domain.entities.common.statuses.Status.DamageFamily
import com.myrran.domain.entities.common.statuses.Status.Slowed
import com.myrran.domain.entities.common.statuses.Status.VulnerableToDamage
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.mob.spells.effect.Effect
import com.myrran.domain.entities.mob.spells.effect.EffectType

class StatusesComponent: Statuses
{
    private var effects: MutableList<Effect> = mutableListOf()
    private var allStatuses: List<Status> = mutableListOf()

    fun addEffect(effect: Effect) =

        effects.add(effect)

    fun removeEffect(effectId: EntityId) =

        effects.removeIf { it.id == effectId }

    fun removeEffectsWithStatus(typeOfStatus: Class<out Status>) =

        effects.removeIf { it.statusEffects.any { status -> typeOfStatus.isInstance(status) } }

    fun removeExpired() =

        effects.removeIf { it.hasExpired() }

    fun findAll(): List<Effect> =

        effects

    fun findExpired(): List<Effect> =

        effects.filter { it.hasExpired() }

    fun findByCasterAndType(casterId: EntityId, effectType: EffectType): Effect? =

        effects.firstOrNull { it.caster.id == casterId && it.effectType == effectType }

    fun recalc() {

        allStatuses = effects.flatMap { it.statusEffects }  }

    //
    //--------------------------------------------------------------------------------------------------------

    fun damage(): List<Damage> =

        effects.flatMap { effect -> effect.damages }
            .also { effects.forEach { it.damages.clear() } }

    override fun slowMagnitude(): Float =

        allStatuses.filterIsInstance<Slowed>().minOfOrNull { it.magnitude } ?: 1f

    override fun isFreezable(): Boolean =

        allStatuses.filterIsInstance<Chilled>().sumOf { it.chillAmount } > 300

    override fun vulnerableTo(damageFamily: DamageFamily): Long =

        allStatuses.filterIsInstance<VulnerableToDamage>()
            .filter { it.damageFamily == damageFamily }
            .sumOf { it.percentage }
}
