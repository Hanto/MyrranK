package com.myrran.domain.mobs.player

import com.myrran.infraestructure.controller.player.PlayerInputs

// must remain iddle to cast spells, they are casted after expending the required casting time
//------------------------------------------------------------------------------------------------------------

data object StateTacticalIddle : State, StateIddle {

    override fun nextState(inputs: PlayerInputs, player: Player): State =

        when {

            inputs.tryToCast && player.isReadyToCast() -> {

                player.startCasting()
                    .let { StateTacticalCasting }
            }
            inputs.isMoving() -> {

                player.setLinearVelocity(inputs.calculateDirection(), player.maxLinearSpeed)
                    .let { StateTacticalMoving }
            }
            else -> StateTacticalIddle
        }
}

data object StateTacticalMoving: State, StateMoving {

    override fun nextState(inputs: PlayerInputs, player: Player): State =

        when {

            inputs.isMoving() -> {

                player.setLinearVelocity(inputs.calculateDirection(), player.maxLinearSpeed)
                    .let { StateTacticalMoving }
            }
            inputs.tryToCast && player.isReadyToCast() -> {

                player.setLinearVelocity(inputs.calculateDirection(), 0f)
                    .let { player.startCasting() }
                    .let { StateTacticalCasting }
            }
            else -> {

                player.setLinearVelocity(inputs.calculateDirection(), 0f)
                    .let { StateTacticalIddle }
            }
        }
}

data object StateTacticalCasting: State {

    override fun nextState(inputs: PlayerInputs, player: Player): State =

        when {

            inputs.isMoving() -> {

                player.setLinearVelocity(inputs.calculateDirection(), player.maxLinearSpeed)
                    .let { player.stopCasting() }
                    .let { StateTacticalMoving }
            }
            player.isReadyToCast() && inputs.tryToCast -> {

                player.castSpell()
                    .let { player.startCasting() }
                    .let { StateTacticalCasting }
            }
            player.isReadyToCast() && !inputs.tryToCast -> {

                player.castSpell()
                    .let { StateTacticalIddle }
            }
            else -> StateTacticalCasting
        }
}

// can cast spells on the run, they only trigger a cooldown on use:
//------------------------------------------------------------------------------------------------------------

data object StateActionIddle: State, StateIddle {

    override fun nextState(inputs: PlayerInputs, player: Player): State {

        if (inputs.tryToCast && player.isReadyToCast())
            player.castSpell().also { player.startCasting() }

        return when(inputs.isMoving()) {

            true -> player.setLinearVelocity(inputs.calculateDirection(), player.maxLinearSpeed)
                .let { StateActionMoving }

            false -> StateActionIddle
        }
    }
}

data object StateActionMoving: State, StateMoving {

    override fun nextState(inputs: PlayerInputs, player: Player): State {

        if (inputs.tryToCast && player.isReadyToCast())
            player.castSpell().also { player.startCasting() }

        return when(inputs.isMoving()) {

            true -> player.setLinearVelocity(inputs.calculateDirection(), player.maxLinearSpeed)
                .let { StateActionMoving }

            false -> player.setLinearVelocity(inputs.calculateDirection(), 0f)
                .let { StateActionIddle }
        }
    }
}


sealed interface State {

    fun nextState(inputs: PlayerInputs, player: Player): State
}

sealed interface StateIddle: State
sealed interface StateMoving: State
