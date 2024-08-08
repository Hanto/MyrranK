package com.myrran.domain.skills.created.form

import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.form.FormSkillSlotContent.NoFormSkill
import com.myrran.domain.skills.created.skill.SkillsRemoved
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.created.stat.UpgradeCost.Companion.ZERO
import com.myrran.domain.skills.lock.Lock
import com.myrran.domain.skills.lock.LockI
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.form.FormTemplate
import kotlin.reflect.KClass

data class FormSkillSlot(

    val id: FormSkillSlotId,
    val name: FormSkillSlotName,
    val lock: Lock,
    var content: FormSkillSlotContent

): LockI by lock
{
    // FORMSKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getFormSkill(): FormSkill? =

        content.ifIs(FormSkill::class)

    fun isFormSkillSlotOpenedBy(formTemplate: FormTemplate): Boolean =

        lock.isOpenedBy(formTemplate.keys)

    fun removeFormSkill(): SkillsRemoved =

        when (val formSkill = content) {

            is FormSkill -> (formSkill.removeAllEffectSkills() + formSkill).also { content = NoFormSkill}
            is NoFormSkill -> SkillsRemoved()
        }

    fun setFormSkill(formSkill: FormSkill) =

        when (lock.isOpenedBy(formSkill.keys)) {

            true -> content = formSkill
            false -> Unit
        }

    // EFFECTSKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getEffectSkill(effectSkillSlotId: EffectSkillSlotId): EffectSkill? =

        content.ifIs(FormSkill::class)?.getEffectSkill(effectSkillSlotId)

    fun isEffectSkillSlotOpenedBy(effectSkillSlotId: EffectSkillSlotId, effectTemplate: EffectTemplate): Boolean =

        content.ifIs(FormSkill::class)?.isOpenedBy(effectSkillSlotId, effectTemplate) ?: false

    fun removeEffectSkillFrom(effectSkillSlotId: EffectSkillSlotId): SkillsRemoved =

        content.ifIs(FormSkill::class)?.removeEffectSkillFrom(effectSkillSlotId) ?: SkillsRemoved()

    fun setEffectSkill(effectSkillSlotId: EffectSkillSlotId, effectSkill: EffectSkill) =

        content.ifIs(FormSkill::class)?.setEffectSkill(effectSkillSlotId, effectSkill)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: NumUpgrades) =

        content.ifIs(FormSkill::class)?.upgrade(statId, upgradeBy)

    fun upgrade(slotId: EffectSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        content.ifIs(FormSkill::class)?.upgrade(slotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        content.ifIs(FormSkill::class)?.totalCost() ?: ZERO

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T> ): T? =

        if (this is T) this else null
}
