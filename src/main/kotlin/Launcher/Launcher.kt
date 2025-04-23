package org.example.Launcher

import javafx.scene.Node
import javafx.scene.layout.StackPane
import org.example.Screens.Inicio.PantallaInicio
import java.io.IOException

class Launcher : StackPane() {

    var nombreUsuario: String? = null
    var versionSeleccionada: String? = null
    var rutaMinecraft: String? = null  // Campo para almacenar la ruta de Minecraft

    init {
        mostrarPantallaInicio()
    }

    private fun mostrarPantallaInicio() {
        val pantalla = PantallaInicio(this)
        cambiarContenido(pantalla)
    }

    fun cambiarContenido(nuevoContenido: Node) {
        children.clear()
        children.add(nuevoContenido)
    }

    // Método para ejecutar el script Python, ahora con la rutaMinecraft
    fun ejecutarLauncherPython(nombre: String, version: String) {
        try {
            // Aquí utilizamos la rutaMinecraft cuando ejecutamos el script
            if (rutaMinecraft == null) {
                println("❌ No se ha seleccionado la ruta de Minecraft.")
                return
            }

            val command = listOf(
                "python",
                "src/main/kotlin/Main.py",  // Ajusta esta ruta si tu Main.py está en otro lugar
                nombre,
                version,
                rutaMinecraft!!  // Pasamos la ruta de Minecraft como parámetro
            )

            val processBuilder = ProcessBuilder(command)
            processBuilder.inheritIO() // Muestra salida de consola si quieres depurar

            val process = processBuilder.start()
            process.waitFor()

            println("✅ Se ejecutó el script Python con éxito.")

        } catch (e: IOException) {
            println("❌ Error al ejecutar el script Python: ${e.message}")
        } catch (e: InterruptedException) {
            println("❌ El proceso fue interrumpido: ${e.message}")
        }
    }
}
