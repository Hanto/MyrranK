package com.myrran.model.skills.templates

import com.myrran.model.skills.skills.bdebuff.BuffSkill
import com.myrran.model.skills.skills.bdebuff.BuffSkillId
import com.myrran.model.skills.skills.bdebuff.BuffSkillName
import com.myrran.model.skills.stat.Stat
import com.myrran.model.skills.stat.StatFixed
import com.myrran.model.skills.stat.StatUpgradeable
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.stat.TemplateStat
import com.myrran.model.skills.templates.stat.TemplateStatFixed
import com.myrran.model.skills.templates.stat.TemplateStatUpgradeable
import com.myrran.model.spells.bdebuffs.BuffType

class BuffSkillTemplate(

    val type: BuffType,
    val name: BuffSkillName,
    val stats: Collection<TemplateStat>,
    val keys: Collection<LockTypes>
)
{
    fun toBuffSkill(): BuffSkill =

        BuffSkill(
            id = BuffSkillId.new(),
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

            is TemplateStatFixed -> StatFixed(
                templateStat.id,
                templateStat.name,
                templateStat.baseBonus
            )
        }

}
