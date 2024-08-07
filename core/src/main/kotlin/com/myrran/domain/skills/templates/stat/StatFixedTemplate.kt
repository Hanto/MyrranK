package com.myrran.domain.skills.templates.stat

import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatBonus
import com.myrran.domain.skills.created.stat.StatFixed
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.StatName

class StatFixedTemplate(

    override val id: StatId,
    override val name: StatName,
    override val baseBonus: StatBonus,

): StatTemplate
{
    override fun toStat(): Stat =

        StatFixed(
            id = id,
            name = name,
            baseBonus = baseBonus)
}
