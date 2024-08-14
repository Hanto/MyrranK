@file:JvmName("Lwjgl3Launcher")

package com.myrran.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.myrran.infraestructure.view.screens.Main

/** Launches the desktop (LWJGL3) application. */
fun main() {

    // This handles macOS support and helps on Windows.
    if (StartupHelper.startNewJvmIfRequired())
      return

    Lwjgl3Application(Main(), Lwjgl3ApplicationConfiguration().apply {

        setTitle("Myrran")
        setWindowedMode(2500, 900)
        //setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
        setWindowIcon("myrran.png")
    })
}
