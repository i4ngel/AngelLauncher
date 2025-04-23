package org.example.Screens.Inicio

import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.effect.GaussianBlur
import org.example.Launcher.Launcher
import java.io.File

class PantallaInicio(private val launcher: Launcher) : StackPane() {

    init {
        val fondo = Region().apply {
            prefWidth = 800.0
            prefHeight = 600.0
            background = Background(BackgroundFill(Color.rgb(255, 255, 255, 0.1), CornerRadii.EMPTY, Insets.EMPTY))
            effect = GaussianBlur(20.0)
        }

        val contenido = VBox(10.0).apply {
            padding = Insets(20.0)

            // Campo para nombre de usuario
            val labelUsuario = crearLabel("Ingresa tu nombre de usuario:")
            val inputUsuario = TextField()

            // Campo para ingresar una versión nueva de Minecraft
            val labelVersionNueva = crearLabel("Ingresa la versión de Minecraft:")
            val inputVersionNueva = TextField()

            // ComboBox para seleccionar una versión ya instalada
            val labelVersionInstalada = crearLabel("Selecciona una versión instalada:")
            val comboBoxVersiones = ComboBox<String>()
            cargarVersionesInstaladas(comboBoxVersiones)

            // Botón para ejecutar el juego
            val btnJugar = Button("Jugar").apply {
                style = "-fx-text-fill: white; -fx-font-weight: bold;"
            }

            // Acción del botón Jugar
            btnJugar.setOnAction {
                val nombreUsuario = inputUsuario.text.trim()
                val versionNueva = inputVersionNueva.text.trim()
                val versionInstalada = comboBoxVersiones.value

                if (nombreUsuario.isNotEmpty()) {
                    launcher.nombreUsuario = nombreUsuario

                    // Si se seleccionó una versión instalada, usamos esa.
                    // Si no, usamos la versión nueva que el usuario ingresó.
                    val version = if (versionInstalada.isNullOrEmpty()) versionNueva else versionInstalada

                    if (version.isNotEmpty()) {
                        launcher.versionSeleccionada = version
                        launcher.rutaMinecraft = "C:/Users/${System.getProperty("user.name")}/AppData/Roaming/.LauncherAngel"  // Ajusta la ruta según sea necesario
                        launcher.ejecutarLauncherPython(nombreUsuario, version)
                    } else {
                        mostrarAlerta("Por favor, ingresa o selecciona una versión.")
                    }
                } else {
                    mostrarAlerta("Por favor, ingresa tu nombre de usuario.")
                }
            }

            children.addAll(
                labelUsuario, inputUsuario,
                labelVersionNueva, inputVersionNueva,
                labelVersionInstalada, comboBoxVersiones,
                btnJugar
            )
        }

        children.addAll(fondo, contenido)
    }

    private fun crearLabel(texto: String): Label {
        return Label(texto).apply {
            textFill = Color.WHITE
            font = Font.font("System", FontWeight.BOLD, 14.0)
        }
    }

    private fun mostrarAlerta(mensaje: String) {
        val alerta = Alert(Alert.AlertType.WARNING)
        alerta.title = "Campos incompletos"
        alerta.headerText = null
        alerta.contentText = mensaje
        alerta.showAndWait()
    }

    private fun cargarVersionesInstaladas(comboBox: ComboBox<String>) {
        // Simula la carga de las versiones instaladas desde una carpeta
        val carpetaVersiones = File("C:/Users/${System.getProperty("user.name")}/AppData/Roaming/.LauncherAngel/versions")
        if (carpetaVersiones.exists() && carpetaVersiones.isDirectory) {
            val versiones = carpetaVersiones.listFiles { file -> file.isDirectory }?.map { it.name } ?: emptyList()
            comboBox.items.addAll(versiones)
        }
    }
}
