package com.myrran.domain.skills.templates.stat

import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatBonus
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.StatName

sealed interface StatTemplate {

    val id: StatId
    val name: StatName
    val baseBonus: StatBonus
    fun toStat(): Stat

}
