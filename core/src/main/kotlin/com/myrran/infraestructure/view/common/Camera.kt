package com.myrran.infraestructure.view.common

import com.badlogic.gdx.graphics.OrthographicCamera
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.metrics.PositionPixels

class Camera(

    val cameraPixel: OrthographicCamera,
    val cameraBox2D: OrthographicCamera
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

    fun update() {

        cameraPixel.update()
        cameraBox2D.update()
    }

    fun resize(width: Int, height: Int) {

        cameraPixel.setToOrtho(false, width.toFloat(), height.toFloat())
        cameraBox2D.setToOrtho(false, Pixel(width).toBox2DUnits(), Pixel(height).toBox2DUnits())
    }
}
