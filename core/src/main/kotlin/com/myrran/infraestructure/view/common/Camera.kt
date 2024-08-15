package com.myrran.infraestructure.view.common

import box2dLight.RayHandler
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.metrics.PositionPixels

class Camera(

    private val cameraPixel: OrthographicCamera,
    private val cameraBox2D: OrthographicCamera
)
{
    fun setPosition(position: PositionPixels) {

        cameraPixel.position
            .also { it.x = position.x.toFloat() }
            .also { it.y = position.y.toFloat() }

        cameraBox2D.position
            .also { it.x = position.x.toBox2DUnits() }
            .also { it.y = position.y.toBox2DUnits() }
    }

    fun setPosition(position: PositionMeters) {

        cameraPixel.position
            .also { it.x = position.x.toPixel().toFloat() }
            .also { it.y = position.y.toPixel().toFloat() }

        cameraBox2D.position
            .also { it.x = position.x.toBox2DUnits() }
            .also { it.y = position.y.toBox2DUnits() }
    }

    fun zoom(factor: Float) {

        cameraPixel.zoom = factor
        cameraBox2D.zoom = factor
    }

    fun update() {

        cameraPixel.update()
        cameraBox2D.update()
    }

    fun assignCameraToStage(stage: Stage) {

        stage.viewport.camera = cameraPixel
    }

    fun updateRayHandler(rayHandler: RayHandler) =

        rayHandler.setCombinedMatrix(cameraBox2D)

    fun resize(width: Int, height: Int) {

        cameraPixel.setToOrtho(false, width.toFloat(), height.toFloat())
        cameraBox2D.setToOrtho(false, Pixel(width).toBox2DUnits(), Pixel(height).toBox2DUnits())
    }

    fun toWorldCoordinates(position: PositionPixels): PositionMeters =

        cameraBox2D.unproject(Vector3(position.x.toFloat(), position.y.toFloat(), 0f))
            .let { PositionMeters(it.x, it.y) }
}
