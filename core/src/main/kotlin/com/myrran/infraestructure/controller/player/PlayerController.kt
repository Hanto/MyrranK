package com.myrran.infraestructure.controller.player

import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.InputProcessor
import com.myrran.domain.World

class PlayerController(

    private val world: World,

): InputProcessor
{
    override fun keyDown(keycode: Int): Boolean {

        val playerInputs = world.player.inputs

        when(keycode) {

            Input.Keys.W -> playerInputs.goNorth = true
            Input.Keys.S -> playerInputs.goSouth = true
            Input.Keys.A -> playerInputs.goWest = true
            Input.Keys.D -> playerInputs.goEast = true
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {

        val playerInputs = world.player.inputs

        when(keycode) {

            Input.Keys.W -> playerInputs.goNorth = false
            Input.Keys.S -> playerInputs.goSouth = false
            Input.Keys.A -> playerInputs.goWest = false
            Input.Keys.D -> playerInputs.goEast = false
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        val playerInputs = world.player.inputs

        when(button) {

            Buttons.RIGHT -> playerInputs.tryToCast = true
        }
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        val playerInputs = world.player.inputs

        when(button) {

            Buttons.RIGHT -> playerInputs.tryToCast = false
        }
        return false
    }

    override fun keyTyped(character: Char): Boolean = false
    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false
    override fun scrolled(amountX: Float, amountY: Float): Boolean = false
}
