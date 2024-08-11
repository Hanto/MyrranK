package com.myrran.infraestructure.input

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.myrran.domain.mob.metricunits.PositionPixels

class PlayerInputListener(

    private val playerInputs: PlayerInputs,
    private val camera: OrthographicCamera

): InputProcessor
{
    override fun keyDown(keycode: Int): Boolean {

        when(keycode) {

            Input.Keys.W -> playerInputs.goNorth = true
            Input.Keys.S -> playerInputs.goSouth = true
            Input.Keys.A -> playerInputs.goWest = true
            Input.Keys.D -> playerInputs.goEast = true
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {

        when(keycode) {

            Input.Keys.W -> playerInputs.goNorth = false
            Input.Keys.S -> playerInputs.goSouth = false
            Input.Keys.A -> playerInputs.goWest = false
            Input.Keys.D -> playerInputs.goEast = false
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        val screenPosition = PositionPixels(screenX, screenY)
        playerInputs.touchedScreen = screenPosition
        playerInputs.touchedWorld = screenPosition.toWorldPosition(camera)
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        val screenPosition = PositionPixels(screenX, screenY)
        playerInputs.touchedScreen = screenPosition
        playerInputs.touchedWorld = screenPosition.toWorldPosition(camera)
        return false
    }

    override fun keyTyped(character: Char): Boolean {

        return false
    }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}
