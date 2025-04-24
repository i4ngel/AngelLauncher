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
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.paint.CycleMethod
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.geometry.Insets
import javafx.stage.Stage
import org.example.Componentes.Navbar.Navbar
import org.example.Componentes.Sidebar.Sidebar

class Layout(private val stage: Stage, private val toggleMaximize: (Stage) -> Unit) : BorderPane() {
    private val navbar = Navbar(stage, toggleMaximize)
    private val sidebar = Sidebar()
    private val contentContainer = VBox()

    init {
        // Establecer el fondo del BorderPane con bordes redondeados
        this.background = Background(BackgroundFill(Color.web("#0A0A0A"), CornerRadii(10.0), Insets.EMPTY))
        this.style = """
            -fx-border-color: #1A1A1A;
            -fx-border-width: 1;
            -fx-border-radius: 10;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);
        """

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

        // Configurar el contenedor de contenido para que sea responsivo
        contentContainer.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE)
        VBox.setVgrow(contentContainer, Priority.ALWAYS)

        // Quitar el estilo de fondo gris del contentContainer para que se vea la imagen
        contentContainer.style = "-fx-padding: 15;"

        // Si la imagen no se carga correctamente, establecer un fondo con gradiente
        if (bgImage == null || bgImage.isError) {
            println("No se pudo cargar la imagen de fondo. Estableciendo fondo con gradiente.")
            contentContainer.background = Background(
                BackgroundFill(
                    LinearGradientBuilder()
                        .startX(0.0).startY(0.0)
                        .endX(1.0).endY(1.0)
                        .proportional(true)
                        .stops(
                            Stop(0.0, Color.web("#0A0A0A")),
                            Stop(1.0, Color.web("#1A1A1A"))
                        )
                        .build(),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
                )
            )
        } else {
            // Si la imagen se carga correctamente, establecerla como fondo responsivo
            val background = Background(
                BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false,
                        false,
                        true,
                        true
                    )
                )
            )
            contentContainer.background = background
        }

        // Asignar los componentes al layout
        top = navbar
        left = sidebar
        center = contentContainer

        // Hacer que el layout sea responsivo
        widthProperty().addListener { _, _, _ -> adjustForScreenSize() }
        heightProperty().addListener { _, _, _ -> adjustForScreenSize() }
    }

    // Ajustar el layout según el tamaño de la pantalla
    private fun adjustForScreenSize() {
        val width = width
        val height = height

        // Ajustar el tamaño del contenido según el tamaño de la ventana
        if (width > 1200) {
            // Pantalla grande
            contentContainer.padding = Insets(20.0)
        } else if (width > 800) {
            // Pantalla mediana
            contentContainer.padding = Insets(15.0)
        } else {
            // Pantalla pequeña
            contentContainer.padding = Insets(10.0)
        }
    }

    // Getter para acceder a la sidebar desde fuera
    fun getSidebar(): Sidebar = sidebar

    fun setContenidoCentral(nodo: javafx.scene.Node) {
        contentContainer.children.clear()
        contentContainer.children.add(nodo)
        VBox.setVgrow(nodo, Priority.ALWAYS)
    }

    // Clase auxiliar para crear gradientes lineales
    private class LinearGradientBuilder {
        private var startX = 0.0
        private var startY = 0.0
        private var endX = 1.0
        private var endY = 1.0
        private var proportional = true
        private var cycleMethod = CycleMethod.NO_CYCLE
        private var stops = mutableListOf<Stop>()

        fun startX(value: Double): LinearGradientBuilder {
            startX = value
            return this
        }

        fun startY(value: Double): LinearGradientBuilder {
            startY = value
            return this
        }

        fun endX(value: Double): LinearGradientBuilder {
            endX = value
            return this
        }

        fun endY(value: Double): LinearGradientBuilder {
            endY = value
            return this
        }

        fun proportional(value: Boolean): LinearGradientBuilder {
            proportional = value
            return this
        }

        fun cycleMethod(value: CycleMethod): LinearGradientBuilder {
            cycleMethod = value
            return this
        }

        fun stops(vararg stopsArray: Stop): LinearGradientBuilder {
            stops.addAll(stopsArray)
            return this
        }

        fun build(): LinearGradient {
            return LinearGradient(
                startX, startY, endX, endY, proportional, cycleMethod, *stops.toTypedArray()
            )
        }
    }
}
