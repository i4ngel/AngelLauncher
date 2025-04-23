// Navbar Corregido
package org.example.Componentes.Navbar

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

class Navbar : HBox() {
    init {
        padding = Insets(10.0)
        spacing = 15.0
        alignment = Pos.CENTER_LEFT
        style = "-fx-background-color: #000000;"

        // Logo Minecraft - Manejo seguro de recursos
        try {
            val inputStream = javaClass.getClassLoader().getResourceAsStream("img/logo.png")
            if (inputStream != null) {
                val logoImageView = ImageView(Image(inputStream))
                logoImageView.fitHeight = 28.0
                logoImageView.fitWidth = 28.0
                logoImageView.isPreserveRatio = true
                children.add(logoImageView)
            } else {
                // Placeholder para el logo
                val logoPlaceholder = Region()
                logoPlaceholder.minWidth = 28.0
                logoPlaceholder.minHeight = 28.0
                logoPlaceholder.style = "-fx-background-color: white;"
                children.add(logoPlaceholder)
            }
        } catch (e: Exception) {
            println("Error cargando logo: ${e.message}")
            // Placeholder para el logo
            val logoPlaceholder = Region()
            logoPlaceholder.minWidth = 28.0
            logoPlaceholder.minHeight = 28.0
            logoPlaceholder.style = "-fx-background-color: white;"
            children.add(logoPlaceholder)
        }

        // Título
        val title = Label("MINECRAFT LAUNCHER")
        title.textFill = Color.WHITE
        title.font = Font.font("System", FontWeight.BOLD, 18.0)

        children.add(title)

        // Espaciador flexible
        val spacer = Region()
        setHgrow(spacer, Priority.ALWAYS)
        children.add(spacer)

        // Indicador de usuario (sin usar FontAwesome)
        val userInfo = Label("Steve")
        userInfo.textFill = Color.WHITE
        userInfo.font = Font.font("System", 14.0)

        children.add(userInfo)
    }
}