package com.myrran.domain.skills.templates.skill

import com.myrran.domain.entities.mob.spells.effect.EffectType
import com.myrran.domain.entities.mob.spells.form.FormSkillType
import com.myrran.domain.entities.mob.spells.spell.SkillType
import com.myrran.domain.skills.created.effect.EffectSkillName
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.effect.EffectSkillSlotName
import com.myrran.domain.skills.created.form.CollisionType
import com.myrran.domain.skills.created.form.FormSkillName
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.form.FormSkillSlotName
import com.myrran.domain.skills.created.skill.SkillName
import com.myrran.domain.skills.created.stat.BonusPerUpgrade
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatBonus
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.StatName
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.lock.Lock
import com.myrran.domain.skills.lock.LockType
import com.myrran.domain.skills.templates.effect.EffectSlotTemplate
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.effect.EffectTemplateId
import com.myrran.domain.skills.templates.form.FormSlotTemplate
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.domain.skills.templates.stat.StatUpgradeableTemplate
import org.junit.jupiter.api.Test

class WorldSkillBookIT {


    @Test
    fun `worldSkillBook can be saved and loaded`() {

        val bolt = SkillTemplate(
            id = SkillTemplateId("FIREBOLT_1"),
            type = SkillType.BOLT,
            name = SkillName("Fire bolt"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("SPEED"),
                    name = StatName("speed"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
                StatUpgradeableTemplate(
                    id = StatId("Cooldown"),
                    name = StatName("cooldown"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                )
            ),
            slots = listOf(
                FormSlotTemplate(
                    id = FormSkillSlotId("IMPACT"),
                    name = FormSkillSlotName("impact"),
                    lock = Lock(listOf(LockType.A, LockType.B))
                ),
                FormSlotTemplate(
                    id = FormSkillSlotId("TRAIL"),
                    name = FormSkillSlotName("trail"),
                    lock = Lock(listOf(LockType.A, LockType.B))
                )
            )
        )

        val CIRCLE = FormTemplate(
            id = FormTemplateId("EXPLOSION_1"),
            type = FormSkillType.CIRCLE,
            name = FormSkillName("Explosion"),
            collisionType = CollisionType.ON_SINGLE_COLLISION_POINT,
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("RADIUS"),
                    name = StatName("radius"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                )
            ),
            slots = listOf(
                EffectSlotTemplate(
                    id = EffectSkillSlotId("DEBUFF_1"),
                    name = EffectSkillSlotName("Debuff 1"),
                    lock = Lock(listOf(LockType.C, LockType.D)),
                ),
                EffectSlotTemplate(
                    id = EffectSkillSlotId("DEBUFF_2"),
                    name = EffectSkillSlotName("Debuff 2"),
                    lock = Lock(listOf(LockType.C, LockType.D)),
                )
            ),
            keys = listOf(LockType.A)
        )

        val fire = EffectTemplate(
            id = EffectTemplateId("FIRE_1"),
            type = EffectType.BOMB,
            name = EffectSkillName("Fire"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("DAMAGE"),
                    name = StatName("damage per second"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
                StatUpgradeableTemplate(
                    id = StatId("DURATION"),
                    name = StatName("duration"),
                    baseBonus = StatBonus(10.0f),
                    maximum = NumUpgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                )
            ),
            keys = listOf(LockType.C)
        )
    }
}
