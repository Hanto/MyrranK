package com.myrran.model.skills.templates

import com.myrran.model.skills.custom.skill.subskill.buff.BuffSkill
import com.myrran.model.skills.custom.skill.subskill.buff.BuffSkillId
import com.myrran.model.skills.custom.skill.subskill.buff.BuffSkillName
import com.myrran.model.skills.custom.stat.Stat
import com.myrran.model.skills.custom.stat.StatUpgradeable
import com.myrran.model.skills.custom.stat.Stats
import com.myrran.model.skills.custom.stat.Upgrades
import com.myrran.model.skills.lock.LockTypes
import com.myrran.model.skills.templates.stat.TemplateStat
import com.myrran.model.skills.templates.stat.TemplateStatUpgradeable
import com.myrran.model.spells.bdebuffs.BuffType
import java.util.UUID

class BuffSkillTemplate(

    val type: BuffType,
    val name: BuffSkillName,
    val stats: Collection<TemplateStat>,
    val keys: Collection<LockTypes>
)
{
    fun toBuffSkill(): BuffSkill =

        BuffSkill(
            id = BuffSkillId(UUID.randomUUID()),
            type = type,
            name = name,
            stats = Stats( stats.map { toStat(it) } )
        )

    private fun toStat(templateStat: TemplateStat): Stat =

        when (templateStat) {

            is TemplateStatUpgradeable -> StatUpgradeable(
                templateStat.id,
                templateStat.name,
                templateStat.baseBonus,
                Upgrades(0),
                templateStat.maximum,
                templateStat.upgradeCost,
                templateStat.bonusPerUpgrade)
        }

}
