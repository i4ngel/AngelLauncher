package org.example.Componentes.Navbar

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.Button
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import javafx.scene.effect.DropShadow
import javafx.scene.input.MouseEvent

class Navbar(private val stage: Stage, private val toggleMaximize: (Stage) -> Unit) : HBox() {

    // Variables para el arrastre de la ventana
    private var xOffset = 0.0
    private var yOffset = 0.0

    init {
        padding = Insets(10.0, 15.0, 10.0, 15.0)
        spacing = 15.0
        alignment = Pos.CENTER_LEFT
        style = """
            -fx-background-color: linear-gradient(to right, #0A0A0A, #121212);
            -fx-border-color: #1A1A1A;
            -fx-border-width: 0 0 1 0;
        """

        // Efecto de sombra para la barra de navegación
        val dropShadow = DropShadow()
        dropShadow.color = Color.rgb(0, 0, 0, 0.5)
        dropShadow.radius = 5.0
        dropShadow.spread = 0.1
        effect = dropShadow

        // Logo Minecraft - Manejo seguro de recursos
        try {
            val inputStream = javaClass.getClassLoader().getResourceAsStream("img/logo.png")
            if (inputStream != null) {
                val logoImageView = ImageView(Image(inputStream))
                logoImageView.fitHeight = 24.0
                logoImageView.fitWidth = 24.0
                logoImageView.isPreserveRatio = true
                children.add(logoImageView)
            } else {
                // Placeholder para el logo
                val logoPlaceholder = Region()
                logoPlaceholder.minWidth = 24.0
                logoPlaceholder.minHeight = 24.0
                logoPlaceholder.style = "-fx-background-color: white; -fx-background-radius: 4px;"
                children.add(logoPlaceholder)
            }
        } catch (e: Exception) {
            println("Error cargando logo: ${e.message}")
            // Placeholder para el logo
            val logoPlaceholder = Region()
            logoPlaceholder.minWidth = 24.0
            logoPlaceholder.minHeight = 24.0
            logoPlaceholder.style = "-fx-background-color: white; -fx-background-radius: 4px;"
            children.add(logoPlaceholder)
        }

        // Título
        val title = Label("MINECRAFT LAUNCHER")
        title.textFill = Color.WHITE
        title.font = Font.font("System", FontWeight.BOLD, 14.0)

        children.add(title)

        // Versión del launcher
        val versionLabel = Label("v1.0.0")
        versionLabel.textFill = Color.web("#AAAAAA")
        versionLabel.font = Font.font("System", 12.0)
        versionLabel.style = """
            -fx-background-color: rgba(255,255,255,0.05);
            -fx-background-radius: 4px;
            -fx-padding: 2px 8px;
        """

        children.add(versionLabel)

        // Espaciador flexible
        val spacer = Region()
        setHgrow(spacer, Priority.ALWAYS)
        children.add(spacer)

        // Botones de la derecha
        val settingsButton = createNavButton("⚙", "Configuración")
        val minimizeButton = createNavButton("—", "Minimizar")
        val maximizeButton = createNavButton("□", "Maximizar/Restaurar")
        val closeButton = createNavButton("✕", "Cerrar")

        // Configurar acciones para los botones de control de ventana
        minimizeButton.setOnAction {
            stage.isIconified = true
        }

        maximizeButton.setOnAction {
            toggleMaximize(stage)
        }

        closeButton.setOnAction {
            Platform.exit()
        }

        children.addAll(settingsButton, minimizeButton, maximizeButton, closeButton)

        // Configurar eventos para mover la ventana desde la barra de navegación
        setOnMousePressed { event ->
            xOffset = event.sceneX
            yOffset = event.sceneY
            event.consume()
        }

        setOnMouseDragged { event ->
            // Mover la ventana solo si no está maximizada
            if (!stage.isMaximized) {
                stage.x = event.screenX - xOffset
                stage.y = event.screenY - yOffset
                event.consume()
            }
        }

        // Doble clic en la barra para maximizar/restaurar
        setOnMouseClicked { event ->
            if (event.clickCount == 2) {
                toggleMaximize(stage)
                event.consume()
            }
        }
    }

    private fun createNavButton(text: String, tooltipText: String): Button {
        val button = Button(text)
        button.style = """
            -fx-background-color: transparent;
            -fx-text-fill: #AAAAAA;
            -fx-font-size: 12px;
            -fx-min-width: 28px;
            -fx-min-height: 28px;
            -fx-padding: 0;
            -fx-background-radius: 4px;
        """

        // Tooltip mejorado
        val tooltip = Tooltip(tooltipText)
        tooltip.style = """
            -fx-background-color: #1E1E1E;
            -fx-text-fill: white;
            -fx-font-size: 12px;
            -fx-padding: 5px 10px;
            -fx-background-radius: 3px;
            -fx-border-color: #333333;
            -fx-border-width: 1px;
            -fx-border-radius: 3px;
        """
        Tooltip.install(button, tooltip)

        button.setOnMouseEntered {
            button.style = """
                -fx-background-color: rgba(255,255,255,0.1);
                -fx-text-fill: white;
                -fx-font-size: 12px;
                -fx-min-width: 28px;
                -fx-min-height: 28px;
                -fx-padding: 0;
                -fx-background-radius: 4px;
            """

            // Si es el botón de cerrar, cambiar a rojo al hacer hover
            if (text == "✕") {
                button.style = """
                    -fx-background-color: #E81123;
                    -fx-text-fill: white;
                    -fx-font-size: 12px;
                    -fx-min-width: 28px;
                    -fx-min-height: 28px;
                    -fx-padding: 0;
                    -fx-background-radius: 4px;
                """
            }
        }

        button.setOnMouseExited {
            button.style = """
                -fx-background-color: transparent;
                -fx-text-fill: #AAAAAA;
                -fx-font-size: 12px;
                -fx-min-width: 28px;
                -fx-min-height: 28px;
                -fx-padding: 0;
                -fx-background-radius: 4px;
            """
        }

        return button
    }
}
