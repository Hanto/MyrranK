package com.myrran.model.skills.custom.stat

class Stats(
    stats: Collection<Stat>
)
{
    private val stats = stats.associateBy { it.id }

    fun getStats(): Collection<Stat> = stats.values
    fun getStat(statId: StatId): Stat = stats[statId]!!


    fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        when (val stat = stats[statId]) {
            is StatUpgradeable -> stat.upgrade(upgradeBy)
            is StatFixed, null -> Unit
        }

    fun totalCost(): UpgradeCost =

        stats.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }
}
