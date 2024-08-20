package com.myrran.infraestructure.repositories.skill

import com.myrran.domain.entities.mob.spells.effect.EffectType
import com.myrran.domain.entities.mob.spells.form.FormSkillType
import com.myrran.domain.entities.mob.spells.spell.SkillType
import com.myrran.domain.skills.created.form.CollisionType
import com.myrran.domain.skills.lock.LockType

data class SkillEntity(
    val id: String,
    val templateId: String,
    val type: SkillType,
    val name: String,
    val customName: String,
    val stats: List<StatEntity>,
    val slots: List<FormSkillSlotEntity>
)

data class FormSkillSlotEntity(
    val id: String,
    val name: String,
    val lock: List<LockType>,
    val content: FormSkillEntity?
)

data class FormSkillEntity(
    val id: String,
    val templateId: String,
    val type: FormSkillType,
    val name: String,
    val collisionType: CollisionType,
    val stats: List<StatEntity>,
    val slots: List<EffectSKillSlotEntity>,
    val keys: Collection<LockType>
)

data class EffectSKillSlotEntity(
    val id: String,
    val name: String,
    val lock: List<LockType>,
    val content: EffectSkillEntity?
)

data class EffectSkillEntity(
    val id: String,
    val templateId: String,
    val type: EffectType,
    val name: String,
    val stats: List<StatEntity>,
    val keys: List<LockType>
)

data class StatEntity(
    val id: String,
    val name: String,
    val baseBonus: Float,
    val actual: Int? = null,
    val maximum: Int? = null,
    val upgradeCost: Float? = null,
    val bonusPerUpgrade: Float? = null,
)
