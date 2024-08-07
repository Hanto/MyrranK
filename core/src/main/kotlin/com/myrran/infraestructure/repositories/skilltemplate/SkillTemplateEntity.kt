package com.myrran.infraestructure.repositories.skilltemplate

import com.myrran.domain.skills.lock.LockType
import com.myrran.domain.spells.effect.EffectType
import com.myrran.domain.spells.form.FormSkillType
import com.myrran.domain.spells.spell.SkillType

data class SkillTemplateEntity(
    val id: String,
    val type: SkillType,
    val name: String,
    val stats: List<StatTemplateEntity>,
    val slots: List<SlotTemplateEntity>
)

data class FormTemplateEntity(
    val id: String,
    val type: FormSkillType,
    val name: String,
    val stats: List<StatTemplateEntity>,
    val slots: List<SlotTemplateEntity>,
    val keys: List<LockType>
)

data class EffectTemplateEntity(
    val id: String,
    val type: EffectType,
    val name: String,
    val stats: List<StatTemplateEntity>,
    val keys: Collection<LockType>
)

data class SlotTemplateEntity(
    val id: String,
    val name: String,
    val lock: List<LockType>
)

data class StatTemplateEntity(
    val id: String,
    val name: String,
    val baseBonus: Float,
    val maximum: Int? = null,
    val upgradeCost: Float? = null,
    val bonusPerUpgrade: Float? = null
)
