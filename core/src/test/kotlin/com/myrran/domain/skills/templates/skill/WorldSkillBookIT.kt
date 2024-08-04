package com.myrran.domain.skills.templates.skill

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.myrran.domain.skills.book.WorldSkillBook
import com.myrran.domain.skills.custom.buff.BuffSkillName
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.buff.BuffSkillSlotName
import com.myrran.domain.skills.custom.skill.SkillName
import com.myrran.domain.skills.custom.stat.BonusPerUpgrade
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatBonus
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.StatName
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.custom.subskill.SubSkillName
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotName
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.LockType
import com.myrran.domain.skills.templates.buff.BuffSkillSlotTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.stat.StatUpgradeableTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillSlotTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.spells.buff.BuffType
import com.myrran.domain.spells.spell.SkillType
import com.myrran.domain.spells.subspell.SubSkillType
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
                SubSkillSlotTemplate(
                    id = SubSkillSlotId("IMPACT"),
                    name = SubSkillSlotName("impact"),
                    lock = Lock(listOf(LockType.ALPHA, LockType.BETA))
                ),
                SubSkillSlotTemplate(
                    id = SubSkillSlotId("TRAIL"),
                    name = SubSkillSlotName("trail"),
                    lock = Lock(listOf(LockType.ALPHA, LockType.BETA))
                )
            )
        )

        val explosion = SubSkillTemplate(
            id = SubSkillTemplateId("EXPLOSION_1"),
            type = SubSkillType.EXPLOSION,
            name = SubSkillName("Explosion"),
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
                BuffSkillSlotTemplate(
                    id = BuffSkillSlotId("DEBUFF_1"),
                    name = BuffSkillSlotName("Debuff 1"),
                    lock = Lock(listOf(LockType.GAMMA, LockType.EPSILON)),
                ),
                BuffSkillSlotTemplate(
                    id = BuffSkillSlotId("DEBUFF_2"),
                    name = BuffSkillSlotName("Debuff 2"),
                    lock = Lock(listOf(LockType.GAMMA, LockType.EPSILON)),
                )
            ),
            keys = listOf(LockType.ALPHA)
        )

        val fire = BuffSkillTemplate(
            id = BuffSkillTemplateId("FIRE_1"),
            type = BuffType.FIRE,
            name = BuffSkillName("Fire"),
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
            keys = listOf(LockType.GAMMA)
        )

        val originalDomain = WorldSkillBook(
            listOf(bolt).associateBy { it.id },
            listOf(explosion).associateBy { it.id },
            listOf(fire).associateBy { it.id },
        )

        val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    }


}
