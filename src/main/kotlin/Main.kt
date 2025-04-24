package org.example

import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.example.Componentes.Layout.Layout
import org.example.Launcher.Launcher

class Main : Application() {
    private var xOffset = 0.0
    private var yOffset = 0.0
    private var isCustomMaximized = false
    private var originalBounds: Rectangle2D? = null

    override fun start(primaryStage: Stage) {
        // Cargar el icono desde resources/img/logo.png
        val icono = Image("img/logo.png") // La ruta desde resources

        // Establecer el icono en la ventana
        primaryStage.icons.add(icono)

        // Configurar la ventana sin decoración
        primaryStage.initStyle(StageStyle.TRANSPARENT)

        val layout = Layout(primaryStage, this::toggleMaximize)
        val launcher = Launcher()
        layout.setContenidoCentral(launcher)

        val scene = Scene(layout, 1000.0, 700.0)
        scene.fill = Color.TRANSPARENT

        // Ya no necesitamos configurar los eventos de arrastre aquí
        // porque lo haremos en el Navbar

        // Configurar eventos para redimensionar la ventana
        val resizeBorder = 5.0

        scene.setOnMouseMoved { event ->
            if (!isCustomMaximized) {
                if (event.sceneX < resizeBorder) {
                    scene.cursor = javafx.scene.Cursor.W_RESIZE
                } else if (event.sceneX > scene.width - resizeBorder) {
                    scene.cursor = javafx.scene.Cursor.E_RESIZE
                } else if (event.sceneY < resizeBorder) {
                    scene.cursor = javafx.scene.Cursor.N_RESIZE
                } else if (event.sceneY > scene.height - resizeBorder) {
                    scene.cursor = javafx.scene.Cursor.S_RESIZE
                } else {
                    scene.cursor = javafx.scene.Cursor.DEFAULT
                }
            }
        }

        scene.setOnMouseDragged { event ->
            if (!isCustomMaximized) {
                if (scene.cursor == javafx.scene.Cursor.W_RESIZE) {
                    val newWidth = primaryStage.width - (event.screenX - primaryStage.x)
                    if (newWidth > primaryStage.minWidth) {
                        primaryStage.width = newWidth
                        primaryStage.x = event.screenX
                    }
                } else if (scene.cursor == javafx.scene.Cursor.E_RESIZE) {
                    primaryStage.width = event.screenX - primaryStage.x
                } else if (scene.cursor == javafx.scene.Cursor.N_RESIZE) {
                    val newHeight = primaryStage.height - (event.screenY - primaryStage.y)
                    if (newHeight > primaryStage.minHeight) {
                        primaryStage.height = newHeight
                        primaryStage.y = event.screenY
                    }
                } else if (scene.cursor == javafx.scene.Cursor.S_RESIZE) {
                    primaryStage.height = event.screenY - primaryStage.y
                }
            }
        }

        primaryStage.title = "Minecraft Custom Launcher"
        primaryStage.scene = scene
        primaryStage.isResizable = true
        primaryStage.minWidth = 800.0
        primaryStage.minHeight = 600.0
        primaryStage.show()
    }

    // Función personalizada para maximizar/restaurar la ventana
    fun toggleMaximize(stage: Stage) {
        if (!isCustomMaximized) {
            // Guardar las dimensiones originales antes de maximizar
            originalBounds = Rectangle2D(stage.x, stage.y, stage.width, stage.height)

            // Obtener los límites visuales de la pantalla (respetando la barra de tareas)
            val screen = Screen.getPrimary()
            val visualBounds = screen.visualBounds

            // Establecer el tamaño para dejar un borde de 1px
            stage.x = visualBounds.minX + 1
            stage.y = visualBounds.minY + 1
            stage.width = visualBounds.width - 2
            stage.height = visualBounds.height - 2

            isCustomMaximized = true
        } else {
            // Restaurar a las dimensiones originales
            originalBounds?.let {
                stage.x = it.minX
                stage.y = it.minY
                stage.width = it.width
                stage.height = it.height
            }

            isCustomMaximized = false
        }
    }

    // Método para comprobar si la ventana está maximizada
    fun isMaximized(): Boolean {
        return isCustomMaximized
    }
}

fun ejecutarLauncherPython(nombreUsuario: String, version: String) {
    try {
        val processBuilder = ProcessBuilder(
            "python", "src/main/kotlin/Main.py", nombreUsuario, version
        )
        processBuilder.inheritIO()  // Esto hace que se impriman los logs del .py en consola
        val process = processBuilder.start()
        process.waitFor()
    } catch (e: Exception) {
        println("Error al ejecutar el launcher Python: ${e.message}")
    }
}

fun main() {
    Application.launch(Main::class.java)
}
