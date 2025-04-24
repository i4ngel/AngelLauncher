package org.example.Componentes.Sidebar

import javafx.animation.TranslateTransition
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.paint.CycleMethod
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.scene.effect.DropShadow
import javafx.scene.effect.Glow
import javafx.util.Duration

class Sidebar : VBox() {

    // Declaramos los botones
    val btnInicio: Button
    val btnPerfil: Button
    val btnServers: Button
    val btnMods: Button
    val btnSettings: Button

    init {
        padding = Insets(15.0, 0.0, 15.0, 0.0)
        spacing = 15.0
        alignment = Pos.TOP_CENTER
        style = """
            -fx-background-color: linear-gradient(to bottom, #0A0A0A, #121212);
            -fx-border-color: linear-gradient(to bottom, #1A1A1A, #252525);
            -fx-border-width: 0 1 0 0;
        """
        prefWidth = 65.0
        minWidth = 65.0

        // Crear botones con iconos
        btnInicio = createSidebarButton("🏠", "Inicio", "Pantalla principal del launcher")
        btnPerfil = createSidebarButton("👤", "Perfil", "Gestiona tu perfil de Minecraft")
        btnServers = createSidebarButton("🌐", "Servidores", "Explora y conecta a servidores")
        btnMods = createSidebarButton("🧩", "Mods", "Administra tus mods")

        // Separador
        val separator = Region()
        separator.prefHeight = 20.0
        separator.style = """
            -fx-background-color: linear-gradient(to right, transparent, #333333, transparent);
            -fx-min-height: 1px;
            -fx-pref-height: 1px;
            -fx-max-height: 1px;
            -fx-min-width: 30px;
        """

        btnSettings = createSidebarButton("⚙", "Ajustes", "Configura el launcher")

        // Añadir botones al sidebar
        children.addAll(
            createLogoArea(),
            btnInicio,
            btnPerfil,
            btnServers,
            btnMods,
            separator,
            btnSettings
        )
    }

    private fun createLogoArea(): StackPane {
        val logoArea = StackPane()
        logoArea.prefHeight = 60.0
        logoArea.alignment = Pos.CENTER

        try {
            // Intentar cargar el logo desde los recursos
            val inputStream = javaClass.getClassLoader().getResourceAsStream("img/logo.png")
            if (inputStream != null) {
                val logoImage = Image(inputStream)
                val logoImageView = ImageView(logoImage)
                logoImageView.fitHeight = 40.0
                logoImageView.fitWidth = 40.0
                logoImageView.isPreserveRatio = true

                // Añadir efecto de brillo al logo
                val glow = Glow(0.3)
                logoImageView.effect = glow

                // Crear un fondo circular para el logo
                val circle = Circle(22.0)
                circle.fill = LinearGradient(0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.web("#1A1A1A")),
                    Stop(1.0, Color.web("#0A0A0A"))
                )
                circle.stroke = Color.web("#333333")
                circle.strokeWidth = 1.0

                // Añadir sombra al círculo
                val dropShadow = DropShadow()
                dropShadow.radius = 5.0
                dropShadow.color = Color.web("#000000")
                circle.effect = dropShadow

                logoArea.children.addAll(circle, logoImageView)
            } else {
                // Fallback si no se encuentra el logo
                val logoCircle = Circle(20.0)
                logoCircle.fill = LinearGradient(0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.web("#1A1A1A")),
                    Stop(1.0, Color.web("#0A0A0A"))
                )
                logoCircle.stroke = Color.web("#333333")
                logoCircle.strokeWidth = 1.0

                val logoLabel = Label("MC")
                logoLabel.textFill = Color.WHITE
                logoLabel.font = javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 14.0)

                logoArea.children.addAll(logoCircle, logoLabel)
            }
        } catch (e: Exception) {
            println("Error creando área de logo: ${e.message}")
            // Fallback si hay error
            val logoCircle = Circle(20.0)
            logoCircle.fill = Color.web("#222222")
            logoCircle.stroke = Color.web("#333333")
            logoCircle.strokeWidth = 1.0

            val logoLabel = Label("MC")
            logoLabel.textFill = Color.WHITE
            logoLabel.font = javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 14.0)

            logoArea.children.addAll(logoCircle, logoLabel)
        }

        return logoArea
    }

    private fun createSidebarButton(iconText: String, tooltipText: String, tooltipDescription: String): Button {
        val button = Button()

        // Contenedor para el icono con indicador
        val iconContainer = StackPane()

        // Icono
        val iconLabel = Label(iconText)
        iconLabel.textFill = Color.WHITE
        iconLabel.style = "-fx-font-size: 18px;"

        iconContainer.children.add(iconLabel)

        // Configurar el botón
        button.graphic = iconContainer
        button.prefWidth = 45.0
        button.prefHeight = 45.0
        button.style = """
            -fx-background-color: transparent;
            -fx-background-radius: 10px;
            -fx-padding: 8px;
        """

        // Crear un rectángulo para el indicador activo
        val activeIndicator = Rectangle(3.0, 25.0)
        activeIndicator.arcWidth = 2.0
        activeIndicator.arcHeight = 2.0
        activeIndicator.fill = Color.web("#43B581")
        activeIndicator.translateX = -20.0
        activeIndicator.opacity = 0.0

        iconContainer.children.add(activeIndicator)

        // Hover effect con animación
        button.setOnMouseEntered {
            button.style = """
                -fx-background-color: linear-gradient(to right, rgba(67, 181, 129, 0.2), rgba(255,255,255,0.1));
                -fx-background-radius: 10px;
                -fx-padding: 8px;
            """

            // Animar el indicador - FIXED: Ensure all values are Doubles
            val translateTransition = TranslateTransition(Duration.millis(150.0), activeIndicator)
            translateTransition.toX = -15.0
            translateTransition.play()

            activeIndicator.opacity = 1.0
        }

        button.setOnMouseExited {
            button.style = """
                -fx-background-color: transparent;
                -fx-background-radius: 10px;
                -fx-padding: 8px;
            """

            // Animar el indicador de vuelta - FIXED: Ensure all values are Doubles
            val translateTransition = TranslateTransition(Duration.millis(150.0), activeIndicator)
            translateTransition.toX = -20.0
            translateTransition.play()

            activeIndicator.opacity = 0.0
        }

        // Tooltip mejorado
        val tooltip = Tooltip("$tooltipText\n$tooltipDescription")
        tooltip.style = """
            -fx-background-color: #1E1E1E;
            -fx-text-fill: white;
            -fx-font-size: 12px;
            -fx-padding: 8px;
            -fx-background-radius: 5px;
            -fx-border-color: #333333;
            -fx-border-width: 1px;
            -fx-border-radius: 5px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 3);
        """
        Tooltip.install(button, tooltip)

        return button
    }
}
