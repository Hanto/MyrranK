package com.myrran.domain.skills.custom.buff

import com.myrran.domain.skills.custom.BuffSkill
import com.myrran.domain.skills.custom.BuffSkillSlotContent
import com.myrran.domain.skills.custom.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.custom.stat.UpgradeCost.Companion.ZERO
import com.myrran.domain.skills.lock.Lock
import com.myrran.domain.skills.lock.LockI
import kotlin.reflect.KClass

data class BuffSkillSlot(

    val id: BuffSkillSlotId,
    val name: BuffSkillSlotName,
    val lock: Lock,
    var content: BuffSkillSlotContent

): LockI by lock
{
    // CONTENT:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(): BuffSkill? =

        content.ifIs(BuffSkill::class)

    fun removeBuffSkill(): BuffSkill? =

        content.ifIs(BuffSkill::class).also { content = NoBuffSkill }

    fun setBuffSkill(buffSkill: BuffSkill) =

        when (lock.isOpenedBy(buffSkill.keys)) {

            true -> content = buffSkill
            false -> Unit
        }

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: NumUpgrades) =

        content.ifIs(BuffSkill::class)?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        content.ifIs(BuffSkill::class)?.statCost() ?: ZERO

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
