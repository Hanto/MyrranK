package com.myrran.domain.skills.created.effect

import com.myrran.domain.skills.created.effect.EffectSkillSlotContent.NoEffectSkill
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.created.stat.UpgradeCost.Companion.ZERO
import com.myrran.domain.skills.lock.Lock
import com.myrran.domain.skills.lock.LockI
import kotlin.reflect.KClass

data class EffectSkillSlot(

    val id: EffectSkillSlotId,
    val name: EffectSkillSlotName,
    val lock: Lock,
    var content: EffectSkillSlotContent

): LockI by lock
{
    // CONTENT:
    //--------------------------------------------------------------------------------------------------------

    fun getEffectSkill(): EffectSkill? =

        content.ifIs(EffectSkill::class)

    fun removeEffectSkill(): EffectSkill? =

        content.ifIs(EffectSkill::class).also { content = NoEffectSkill }

    fun setEffectSkill(effectSkill: EffectSkill) =

        when (lock.isOpenedBy(effectSkill.keys)) {

            true -> content = effectSkill
            false -> Unit
        }

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: NumUpgrades) =

        content.ifIs(EffectSkill::class)?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        content.ifIs(EffectSkill::class)?.statCost() ?: ZERO

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
