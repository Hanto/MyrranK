package com.myrran.infraestructure.repositories.skill

import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillId
import com.myrran.domain.skills.created.effect.EffectSkillName
import com.myrran.domain.skills.created.effect.EffectSkillSlot
import com.myrran.domain.skills.created.effect.EffectSkillSlotContent.NoEffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.effect.EffectSkillSlotName
import com.myrran.domain.skills.created.effect.EffectSkillSlots
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillId
import com.myrran.domain.skills.created.form.FormSkillName
import com.myrran.domain.skills.created.form.FormSkillSlot
import com.myrran.domain.skills.created.form.FormSkillSlotContent.NoFormSkill
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.form.FormSkillSlotName
import com.myrran.domain.skills.created.form.FormSkillSlots
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.skills.created.skill.SkillName
import com.myrran.domain.skills.created.stat.BonusPerUpgrade
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatBonus
import com.myrran.domain.skills.created.stat.StatFixed
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.StatName
import com.myrran.domain.skills.created.stat.StatUpgradeable
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.created.stat.Upgrades
import com.myrran.domain.skills.lock.Lock
import com.myrran.domain.skills.templates.effect.EffectTemplateId
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import java.util.UUID

class SkillAdapter
{
    fun fromDomain(domain: Skill): SkillEntity =

        SkillEntity(
            id = domain.id.value.toString(),
            templateId = domain.templateId.value,
            type = domain.type,
            name = domain.name.value,
            customName = domain.customName.value,
            stats = domain.getStats().map { fromDomain(it) },
            slots = domain.getFormSkillSlots().map { fromDomain(it) }
        )

    fun toDomain(entity: SkillEntity): Skill =

        Skill(
            id = SkillId(UUID.fromString(entity.id)),
            templateId = SkillTemplateId(entity.templateId),
            type = entity.type,
            name = SkillName(entity.name),
            customName = SkillName(entity.customName),
            stats = Stats(entity.stats.map { toDomain(it) }.associateBy { it.id } ),
            slots = FormSkillSlots(entity.slots.map { toDomain(it) }.associateBy { it.id } )
        )

    private fun fromDomain(domain: FormSkillSlot): FormSkillSlotEntity =

        FormSkillSlotEntity(
            id = domain.id.value,
            name = domain.name.value,
            lock = domain.lock.openedBy.toList(),
            content = when (val content = domain.content) {

                NoFormSkill -> null
                is FormSkill -> fromDomain(content)
            }
        )

    private fun toDomain(entity: FormSkillSlotEntity): FormSkillSlot =

        FormSkillSlot(
            id = FormSkillSlotId(entity.id),
            name = FormSkillSlotName(entity.name),
            lock = Lock(entity.lock),
            content = when (val content = entity.content) {

                null -> NoFormSkill
                else -> toDomain(content)
            }
        )

    private fun fromDomain(domain: FormSkill): FormSkillEntity =

        FormSkillEntity(
            id = domain.id.value.toString(),
            templateId = domain.templateId.value,
            type = domain.type,
            name = domain.name.value,
            collisionType = domain.collisionType,
            stats = domain.getStats().map { fromDomain(it) },
            slots = domain.slots.getEffectSkillSlots().map { fromDomain(it) },
            keys = domain.keys
        )

    private fun toDomain(entity: FormSkillEntity): FormSkill =

        FormSkill(
            id = FormSkillId(UUID.fromString(entity.id)),
            templateId = FormTemplateId(entity.templateId),
            type = entity.type,
            name = FormSkillName(entity.name),
            collisionType = entity.collisionType,
            stats = Stats(entity.stats.map { toDomain(it) }.associateBy { it.id } ),
            slots = EffectSkillSlots(entity.slots.map { toDomain(it) }.associateBy { it.id } ),
            keys = entity.keys
        )

    private fun fromDomain(domain: EffectSkillSlot): EffectSKillSlotEntity =

        EffectSKillSlotEntity(
            id = domain.id.value,
            name = domain.name.value,
            lock = domain.lock.openedBy.toList(),
            content = when (val content = domain.content) {

                NoEffectSkill -> null
                is EffectSkill -> fromDomain(content)
            }
        )

    private fun toDomain(entity: EffectSKillSlotEntity): EffectSkillSlot =

        EffectSkillSlot(
            id = EffectSkillSlotId(entity.id),
            name = EffectSkillSlotName(entity.name),
            lock = Lock(entity.lock),
            content = when (val content = entity.content) {

                null -> NoEffectSkill
                else -> toDomain(content)
            }
        )

    private fun fromDomain(domain: EffectSkill): EffectSkillEntity =

        EffectSkillEntity(
            id = domain.id.value.toString(),
            templateId = domain.templateId.value,
            type = domain.type,
            name = domain.name.value,
            stats = domain.getStats().map { fromDomain(it) },
            keys = domain.keys.toList()
        )

    private fun toDomain(entity: EffectSkillEntity): EffectSkill =

        EffectSkill(
            id = EffectSkillId(UUID.fromString(entity.id)),
            templateId = EffectTemplateId(entity.templateId),
            type = entity.type,
            name = EffectSkillName(entity.name),
            stats = Stats(entity.stats.map { toDomain(it) }.associateBy { it.id } ),
            keys = entity.keys
        )

    private fun fromDomain(domain: Stat): StatEntity =

        when (domain) {

            is StatFixed -> StatEntity(
                id = domain.id.value,
                name = domain.name.value,
                baseBonus = domain.baseBonus.value,
            )

            is StatUpgradeable -> StatEntity(
                id = domain.id.value,
                name = domain.name.value,
                baseBonus = domain.baseBonus.value,
                actual = domain.upgrades.actual.value,
                maximum = domain.upgrades.maximum.value,
                upgradeCost = domain.upgradeCost.value,
                bonusPerUpgrade = domain.bonusPerUpgrade.value
            )
        }

    private fun toDomain(entity: StatEntity): Stat =

        when (entity.actual == null) {

            true -> StatFixed(
                id = StatId(entity.id),
                name = StatName(entity.name),
                baseBonus = StatBonus(entity.baseBonus)
            )

            false -> StatUpgradeable(
                id = StatId(entity.id),
                name = StatName(entity.name),
                baseBonus = StatBonus(entity.baseBonus),
                upgrades = Upgrades(
                    actual = NumUpgrades(entity.actual),
                    maximum = NumUpgrades(entity.maximum!!)),
                upgradeCost = UpgradeCost(entity.upgradeCost!!),
                bonusPerUpgrade = BonusPerUpgrade(entity.bonusPerUpgrade!!),
            )
        }
}
