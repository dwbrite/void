@file:JvmName("Main")

package main

import com.badlogic.gdx.Files.FileType
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

fun main(args: Array<String>) {
LwjglApplication(Application(), LwjglApplicationConfiguration().apply {
  title = "fghjklsadasdads"
  width = 600
  height = 600
  resizable = false
  /*intArrayOf(128, 64, 32, 16).forEach {
    addIcon("libgdx$it.png", FileType.Internal)
  }*/
})
}
