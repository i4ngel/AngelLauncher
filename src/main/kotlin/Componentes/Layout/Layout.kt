package org.example.Componentes.Layout

import javafx.scene.layout.BorderPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundImage
import javafx.scene.layout.BackgroundRepeat
import javafx.scene.layout.BackgroundSize
import javafx.scene.layout.BackgroundPosition
import javafx.scene.layout.VBox
import javafx.scene.layout.Priority
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.geometry.Insets
import org.example.Componentes.Navbar.Navbar
import org.example.Componentes.Sidebar.Sidebar

class Layout : BorderPane() {
    private val navbar = Navbar()
    private val sidebar = Sidebar()
    private val contentContainer = VBox()

    init {
        // Por defecto, establecer el fondo del BorderPane como gris
        this.background = Background(BackgroundFill(Color.web("#1E1E1E"), CornerRadii.EMPTY, Insets.EMPTY))

        // Intentar cargar la imagen de fondo desde los recursos
        val bgImage = try {
            val resourceStream = javaClass.classLoader.getResourceAsStream("img/bg.png")
            if (resourceStream != null) {
                Image(resourceStream)
            } else {
                println("No se encontró el recurso de imagen")
                null
            }
        } catch (e: Exception) {
            println("Error al cargar la imagen de fondo: ${e.message}")
            null
        }

        // Configurar el contenedor de contenido
        contentContainer.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE)
        VBox.setVgrow(contentContainer, Priority.ALWAYS)

        // Quitar el estilo de fondo gris del contentContainer para que se vea la imagen
        contentContainer.style = "-fx-padding: 20;"

        // Si la imagen no se carga correctamente, establecer un fondo blanco
        if (bgImage == null || bgImage.isError) {
            println("No se pudo cargar la imagen de fondo. Estableciendo fondo blanco.")
            contentContainer.background = Background(
                BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)
            )
        } else {
            // Si la imagen se carga correctamente, establecerla como fondo
            val background = Background(
                BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
                )
            )
            contentContainer.background = background
        }

        // Asignar los componentes al layout
        top = navbar
        left = sidebar
        center = contentContainer
    }

    // Getter para acceder a la sidebar desde fuera
    fun getSidebar(): Sidebar = sidebar

    fun setContenidoCentral(nodo: javafx.scene.Node) {
        contentContainer.children.clear()
        contentContainer.children.add(nodo)
    }
}