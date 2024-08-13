package com.myrran.infraestructure.view.mobs

import com.badlogic.gdx.graphics.g2d.Animation
import com.myrran.domain.mobs.player.Player
import com.myrran.domain.mobs.spells.spell.Spell
import com.myrran.domain.mobs.spells.spell.SpellBolt
import com.myrran.domain.mobs.steerable.metrics.SizePixels
import com.myrran.infraestructure.view.mobs.player.PlayerAnimation
import com.myrran.infraestructure.view.mobs.player.PlayerView
import com.myrran.infraestructure.view.mobs.player.PlayerViewAssets
import com.myrran.infraestructure.view.mobs.spells.SpellViewAssets
import com.myrran.infraestructure.view.mobs.spells.spell.SpellAnimation
import com.myrran.infraestructure.view.mobs.spells.spell.SpellBoltView
import com.myrran.infraestructure.view.mobs.spells.spell.SpellView
import ktx.collections.toGdxArray

class MobViewFactory(

    private val playerAssets: PlayerViewAssets,
    private val spellAssets: SpellViewAssets,
)
{
    companion object {

        private val size = SizePixels(32, 32)
    }

    fun createPlayer(model: Player): PlayerView {

        val frames = playerAssets.character.split(size.width.value(), size.height.value())
        val animations = mapOf(
            PlayerAnimation.WALK_SOUTH to Animation(0.2f, arrayOf(frames[0][0], frames[0][1], frames[0][2]).toGdxArray()),
            PlayerAnimation.WALK_WEST to Animation(0.2f, arrayOf(frames[1][0], frames[1][1], frames[1][2]).toGdxArray()),
            PlayerAnimation.WALK_EAST to Animation(0.2f, arrayOf(frames[2][0], frames[2][1], frames[2][2]).toGdxArray()),
            PlayerAnimation.WALK_NORTH to Animation(0.2f, arrayOf(frames[3][0], frames[3][1], frames[3][2]).toGdxArray()),
            PlayerAnimation.IDDLE to Animation(0.5f, arrayOf(frames[2][3], frames[2][4], frames[2][5]).toGdxArray())
        )

        return PlayerView(model, animations, size)
    }

    fun createSpell(spell: Spell): SpellView =

        when (spell) {
            is SpellBolt -> createSpellBolt(spell)
        }

    private fun createSpellBolt(model: SpellBolt): SpellBoltView {

        val frames = spellAssets.spellBolt.split(size.width.value(), size.height.value())
        val animations = mapOf(
            SpellAnimation.GLOW to Animation(0.1f,  arrayOf(frames[0][3], frames[0][4], frames[0][5], frames[0][4]).toGdxArray()))

        return SpellBoltView(model, animations, size)
    }

}
