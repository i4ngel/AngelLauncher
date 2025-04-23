// Sidebar Colapsable sin FontAwesome
package org.example.Componentes.Sidebar

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.VBox
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.application.Platform

class Sidebar : VBox() {

    // Declaramos los botones y dimensiones
    val btnInicio: Button
    val btnPerfil: Button

    private val collapsedWidth = 50.0
    private val expandedWidth = 180.0
    private var isExpanded = false

    init {
        padding = Insets(10.0)
        spacing = 15.0
        alignment = Pos.TOP_CENTER
        style = "-fx-background-color: #121212;"
        prefWidth = collapsedWidth

        // Crear botones con iconos simples (sin FontAwesome)
        btnInicio = createIconButton("Inicio", "H")
        btnPerfil = createIconButton("Perfil", "P")

        // Añadir botones al sidebar
        children.addAll(btnInicio, btnPerfil)

        // Configurar eventos de expansión/colapso
        setOnMouseEntered {
            if (!isExpanded) {
                expandSidebar()
            }
        }

        setOnMouseExited {
            if (isExpanded) {
                collapseSidebar()
            }
        }
    }

    private fun createIconButton(text: String, iconText: String): Button {
        // Crear el icono simple
        val iconLabel = Label(iconText)
        iconLabel.textFill = Color.WHITE
        iconLabel.style = "-fx-font-weight: bold;"

        // Crear el botón
        val button = Button()
        button.graphic = iconLabel
        button.contentDisplay = javafx.scene.control.ContentDisplay.LEFT
        button.alignment = Pos.CENTER_LEFT
        button.graphicTextGap = 15.0
        button.prefWidth = collapsedWidth
        button.prefHeight = 40.0
        button.style = """
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 8px 12px 8px 12px;
            -fx-cursor: hand;
        """

        // Tooltip para estado colapsado
        val tooltip = Tooltip(text)
        Tooltip.install(button, tooltip)

        // Configurar el texto (inicialmente oculto)
        button.text = ""

        return button
    }

    private fun expandSidebar() {
        isExpanded = true

        // Cambiar ancho directamente (sin animación por ahora)
        prefWidth = expandedWidth

        // Mostrar textos en los botones
        Platform.runLater {
            btnInicio.text = "Inicio"
            btnPerfil.text = "Perfil"
            btnInicio.prefWidth = expandedWidth
            btnPerfil.prefWidth = expandedWidth
        }
    }

    private fun collapseSidebar() {
        isExpanded = false

        // Cambiar ancho directamente (sin animación por ahora)
        prefWidth = collapsedWidth

        // Ocultar textos en los botones
        Platform.runLater {
            btnInicio.text = ""
            btnPerfil.text = ""
            btnInicio.prefWidth = collapsedWidth
            btnPerfil.prefWidth = collapsedWidth
        }
    }
}
