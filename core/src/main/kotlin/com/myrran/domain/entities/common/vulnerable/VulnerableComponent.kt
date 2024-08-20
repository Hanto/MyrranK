package com.myrran.domain.entities.common.vulnerable

data class VulnerableComponent(

    private var actualHPs: HP,
    private var maxHPs: HP

): Vulnerable
{
    constructor(actualHPs: Int, maxHps: Int): this(HP(actualHPs.toFloat()), HP(maxHps.toFloat()))

    private var damageReceived: MutableList<Damage> = mutableListOf()

    override fun getHPs(): HP =
        actualHPs

    override fun getMaxHps(): HP =
        maxHPs

    override fun reduceHps(hps: HP) {

        actualHPs = (actualHPs - hps).atMin(HP(0f)) }

    override fun increaseHps(hps: HP) {
        actualHPs = (actualHPs + hps).atMax(maxHPs) }

    // RAW DAMAGE:
    //--------------------------------------------------------------------------------------------------------

    override fun receiveDamage(damage: Damage) {

        damageReceived.add(damage) }

    override fun retrieveDamage(): List<Damage> =

        damageReceived

    override fun clearAllDamage() =

        damageReceived.clear()
}
