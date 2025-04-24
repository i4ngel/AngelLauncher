package org.example.Screens.Inicio

import javafx.animation.*
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.effect.DropShadow
import javafx.scene.effect.Glow
import javafx.geometry.Pos
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.paint.CycleMethod
import javafx.util.Duration
import org.example.Launcher.Launcher
import java.io.File

class PantallaInicio(private val launcher: Launcher) : BorderPane() {

    init {
        // Establecer el fondo
        background = Background(BackgroundFill(Color.web("#0A0A0A"), CornerRadii.EMPTY, Insets.EMPTY))
        padding = Insets(20.0)

        // Contenedor principal dividido en dos columnas
        val mainContainer = HBox(20.0)
        mainContainer.alignment = Pos.CENTER
        mainContainer.opacity = 0.0

        // Panel izquierdo (área de juego)
        val gamePanel = VBox(15.0)
        gamePanel.alignment = Pos.CENTER
        gamePanel.prefWidthProperty().bind(widthProperty().multiply(0.7))
        gamePanel.style = """
            -fx-background-color: rgba(0,0,0,0.4);
            -fx-background-radius: 10px;
            -fx-padding: 20px;
            -fx-border-color: rgba(255,255,255,0.05);
            -fx-border-width: 1px;
            -fx-border-radius: 10px;
        """

        // Área de lanzamiento del juego
        val launchArea = VBox(15.0)
        launchArea.alignment = Pos.CENTER
        launchArea.prefWidthProperty().bind(gamePanel.widthProperty().multiply(0.9))

        // Imagen de fondo del juego (placeholder)
        val gameBgPane = StackPane()
        gameBgPane.prefHeight = 300.0
        gameBgPane.prefWidthProperty().bind(launchArea.widthProperty())
        gameBgPane.style = """
            -fx-background-color: linear-gradient(to bottom, #111111, #0A0A0A);
            -fx-background-radius: 8px;
            -fx-border-color: rgba(255,255,255,0.05);
            -fx-border-width: 1px;
            -fx-border-radius: 8px;
        """

        // Configuración de versión
        val versionContainer = HBox(10.0)
        versionContainer.alignment = Pos.CENTER
        versionContainer.prefWidthProperty().bind(launchArea.widthProperty())

        val comboBoxVersiones = ComboBox<String>().apply {
            prefWidthProperty().bind(versionContainer.widthProperty().multiply(0.6))
            promptText = "Selecciona una versión"
            style = """
                -fx-background-color: rgba(255,255,255,0.05);
                -fx-text-fill: white;
                -fx-background-radius: 5px;
                -fx-padding: 8px;
                -fx-font-size: 14px;
            """
        }

        // Cargar versiones instaladas
        cargarVersionesInstaladas(comboBoxVersiones)

        // Campo para ingresar una versión nueva
        val inputVersionNueva = TextField().apply {
            promptText = "O ingresa una versión"
            prefWidthProperty().bind(versionContainer.widthProperty().multiply(0.4))
            style = """
                -fx-background-color: rgba(255,255,255,0.05);
                -fx-text-fill: white;
                -fx-background-radius: 5px;
                -fx-padding: 8px;
                -fx-font-size: 14px;
            """
        }

        versionContainer.children.addAll(comboBoxVersiones, inputVersionNueva)

        // Botón de lanzamiento grande
        val btnJugar = Button("LAUNCH GAME").apply {
            prefWidthProperty().bind(launchArea.widthProperty().multiply(0.8))
            prefHeight = 60.0
            font = Font.font("System", FontWeight.BOLD, 18.0)

            // Gradiente para el botón
            val gradient = LinearGradient(0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                Stop(0.0, Color.web("#4BD78F")),
                Stop(1.0, Color.web("#43B581"))
            )

            style = """
                -fx-background-color: #43B581;
                -fx-text-fill: white;
                -fx-background-radius: 8px;
                -fx-cursor: hand;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0, 0, 2);
            """

            // Efectos de hover
            setOnMouseEntered {
                style = """
                    -fx-background-color: #4BD78F;
                    -fx-text-fill: white;
                    -fx-background-radius: 8px;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 7, 0, 0, 3);
                """

                // Añadir efecto de brillo
                val glow = Glow(0.3)
                effect = glow

                // Animación de pulso
                val pulseTransition = ScaleTransition(Duration.millis(300.0), this)
                pulseTransition.toX = 1.03
                pulseTransition.toY = 1.03
                pulseTransition.play()
            }

            setOnMouseExited {
                style = """
                    -fx-background-color: #43B581;
                    -fx-text-fill: white;
                    -fx-background-radius: 8px;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0, 0, 2);
                """

                // Quitar efecto de brillo
                effect = DropShadow(5.0, Color.rgb(0, 0, 0, 0.5))

                // Animación de vuelta al tamaño normal
                val pulseTransition = ScaleTransition(Duration.millis(300.0), this)
                pulseTransition.toX = 1.0
                pulseTransition.toY = 1.0
                pulseTransition.play()
            }

            // Animación al hacer clic
            setOnMousePressed {
                style = """
                    -fx-background-color: #3AA572;
                    -fx-text-fill: white;
                    -fx-background-radius: 8px;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 1);
                """

                // Animación de presión
                val pressTransition = ScaleTransition(Duration.millis(100.0), this)
                pressTransition.toX = 0.97
                pressTransition.toY = 0.97
                pressTransition.play()
            }

            setOnMouseReleased {
                style = """
                    -fx-background-color: #43B581;
                    -fx-text-fill: white;
                    -fx-background-radius: 8px;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0, 0, 2);
                """

                // Animación de liberación
                val releaseTransition = ScaleTransition(Duration.millis(100.0), this)
                releaseTransition.toX = 1.0
                releaseTransition.toY = 1.0
                releaseTransition.play()
            }
        }

        // Información del usuario
        val userContainer = HBox(10.0)
        userContainer.alignment = Pos.CENTER
        userContainer.prefWidthProperty().bind(launchArea.widthProperty())

        val userIcon = Label("👤")
        userIcon.style = "-fx-font-size: 18px;"

        val inputUsuario = TextField("Steve").apply {
            prefWidthProperty().bind(userContainer.widthProperty().multiply(0.8))
            style = """
                -fx-background-color: rgba(255,255,255,0.05);
                -fx-text-fill: white;
                -fx-background-radius: 5px;
                -fx-padding: 8px;
                -fx-font-size: 14px;
            """
        }

        userContainer.children.addAll(userIcon, inputUsuario)

        // Añadir elementos al área de lanzamiento
        launchArea.children.addAll(gameBgPane, versionContainer, btnJugar, userContainer)

        // Añadir área de lanzamiento al panel de juego
        gamePanel.children.add(launchArea)

        // Panel derecho (amigos y noticias)
        val sidePanel = VBox(15.0)
        sidePanel.prefWidthProperty().bind(widthProperty().multiply(0.3))

        // Panel de amigos
        val friendsPanel = VBox(10.0)
        friendsPanel.style = """
            -fx-background-color: rgba(0,0,0,0.4);
            -fx-background-radius: 10px;
            -fx-padding: 15px;
            -fx-border-color: rgba(255,255,255,0.05);
            -fx-border-width: 1px;
            -fx-border-radius: 10px;
        """

        // Título de amigos
        val friendsTitle = HBox()
        friendsTitle.alignment = Pos.CENTER_LEFT

        val friendsLabel = Label("AMIGOS")
        friendsLabel.textFill = Color.WHITE
        friendsLabel.font = Font.font("System", FontWeight.BOLD, 16.0)

        val spacer = Region()
        HBox.setHgrow(spacer, Priority.ALWAYS)

        val verAmigosLink = Hyperlink("Ver Lista de Amigos")
        verAmigosLink.textFill = Color.web("#43B581")
        verAmigosLink.style = "-fx-border-color: transparent;"

        friendsTitle.children.addAll(friendsLabel, spacer, verAmigosLink)

        // Lista de amigos (placeholder)
        val friendsList = VBox(8.0)

        // Crear algunos amigos de ejemplo
        for (i in 1..4) {
            val friendItem = createFriendItem("Jugador$i", "Hace ${i*2} días")
            friendsList.children.add(friendItem)
        }

        friendsPanel.children.addAll(friendsTitle, friendsList)

        // Panel de noticias
        val newsPanel = VBox(10.0)
        newsPanel.style = """
            -fx-background-color: rgba(0,0,0,0.4);
            -fx-background-radius: 10px;
            -fx-padding: 15px;
            -fx-border-color: rgba(255,255,255,0.05);
            -fx-border-width: 1px;
            -fx-border-radius: 10px;
        """

        // Título de noticias
        val newsTitle = HBox()
        newsTitle.alignment = Pos.CENTER_LEFT

        val newsLabel = Label("ÚLTIMAS NOTICIAS")
        newsLabel.textFill = Color.WHITE
        newsLabel.font = Font.font("System", FontWeight.BOLD, 16.0)

        val newsSpacer = Region()
        HBox.setHgrow(newsSpacer, Priority.ALWAYS)

        val verNoticiasLink = Hyperlink("Ver Todas las Noticias")
        verNoticiasLink.textFill = Color.web("#43B581")
        verNoticiasLink.style = "-fx-border-color: transparent;"

        newsTitle.children.addAll(newsLabel, newsSpacer, verNoticiasLink)

        // Contenido de noticias (placeholder)
        val newsContent = VBox(10.0)

        // Crear algunas noticias de ejemplo
        val newsItem = createNewsItem("¡Nueva actualización disponible!", "Descubre las nuevas características")
        newsContent.children.add(newsItem)

        newsPanel.children.addAll(newsTitle, newsContent)

        // Añadir paneles al panel lateral
        sidePanel.children.addAll(friendsPanel, newsPanel)
        VBox.setVgrow(friendsPanel, Priority.ALWAYS)

        // Añadir paneles al contenedor principal
        mainContainer.children.addAll(gamePanel, sidePanel)

        // Añadir el contenedor principal al BorderPane
        center = mainContainer

        // Hacer que el layout sea responsivo
        widthProperty().addListener { _, _, _ -> adjustForScreenSize() }
        heightProperty().addListener { _, _, _ -> adjustForScreenSize() }

        // Configurar acción del botón jugar
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
                    // Animación de lanzamiento
                    val scaleDown = ScaleTransition(Duration.millis(200.0), btnJugar)
                    scaleDown.toX = 0.9
                    scaleDown.toY = 0.9

                    val fadeOut = FadeTransition(Duration.millis(300.0), btnJugar)
                    fadeOut.toValue = 0.7

                    val parallel = ParallelTransition(scaleDown, fadeOut)
                    parallel.setOnFinished {
                        launcher.versionSeleccionada = version
                        launcher.rutaMinecraft = "C:/Users/${System.getProperty("user.name")}/AppData/Roaming/.LauncherAngel"
                        launcher.ejecutarLauncherPython(nombreUsuario, version)

                        // Restaurar el botón después de lanzar
                        val scaleUp = ScaleTransition(Duration.millis(200.0), btnJugar)
                        scaleUp.toX = 1.0
                        scaleUp.toY = 1.0

                        val fadeIn = FadeTransition(Duration.millis(300.0), btnJugar)
                        fadeIn.toValue = 1.0

                        val restoreParallel = ParallelTransition(scaleUp, fadeIn)
                        restoreParallel.play()
                    }
                    parallel.play()
                } else {
                    mostrarAlerta("Por favor, ingresa o selecciona una versión.")
                }
            } else {
                mostrarAlerta("Por favor, ingresa tu nombre de usuario.")
            }
        }

        // Animar la aparición del contenido
        val fadeIn = FadeTransition(Duration.millis(800.0), mainContainer)
        fadeIn.fromValue = 0.0
        fadeIn.toValue = 1.0
        fadeIn.delay = Duration.millis(200.0)
        fadeIn.play()
    }

    // Ajustar el layout según el tamaño de la pantalla
    private fun adjustForScreenSize() {
        val width = width
        val height = height

        // Ajustar el tamaño del contenido según el tamaño de la ventana
        if (width > 1200) {
            // Pantalla grande
            padding = Insets(25.0)
        } else if (width > 800) {
            // Pantalla mediana
            padding = Insets(20.0)
        } else {
            // Pantalla pequeña
            padding = Insets(15.0)
        }
    }

    private fun createFriendItem(name: String, lastSeen: String): HBox {
        val friendItem = HBox(10.0)
        friendItem.alignment = Pos.CENTER_LEFT
        friendItem.style = """
            -fx-background-color: rgba(255,255,255,0.05);
            -fx-background-radius: 5px;
            -fx-padding: 8px;
            -fx-border-color: rgba(255,255,255,0.02);
            -fx-border-width: 1px;
            -fx-border-radius: 5px;
        """

        // Avatar (placeholder)
        val avatar = StackPane()
        avatar.prefWidth = 32.0
        avatar.prefHeight = 32.0
        avatar.style = """
            -fx-background-color: linear-gradient(to bottom right, #333333, #222222);
            -fx-background-radius: 16px;
            -fx-border-color: rgba(255,255,255,0.1);
            -fx-border-width: 1px;
            -fx-border-radius: 16px;
        """

        val avatarText = Label(name.substring(0, 1).uppercase())
        avatarText.textFill = Color.WHITE

        avatar.children.add(avatarText)

        // Información del amigo
        val friendInfo = VBox(2.0)

        val nameLabel = Label(name)
        nameLabel.textFill = Color.WHITE
        nameLabel.font = Font.font("System", FontWeight.BOLD, 12.0)

        val statusLabel = Label("Visto por última vez: $lastSeen")
        statusLabel.textFill = Color.web("#AAAAAA")
        statusLabel.font = Font.font("System", 10.0)

        friendInfo.children.addAll(nameLabel, statusLabel)

        friendItem.children.addAll(avatar, friendInfo)

        // Añadir animación de hover
        friendItem.setOnMouseEntered {
            val transition = TranslateTransition(Duration.millis(150.0), friendItem)
            transition.toX = 3.0
            transition.play()

            friendItem.style = """
                -fx-background-color: rgba(255,255,255,0.08);
                -fx-background-radius: 5px;
                -fx-padding: 8px;
                -fx-border-color: rgba(255,255,255,0.05);
                -fx-border-width: 1px;
                -fx-border-radius: 5px;
            """
        }

        friendItem.setOnMouseExited {
            val transition = TranslateTransition(Duration.millis(150.0), friendItem)
            transition.toX = 0.0
            transition.play()

            friendItem.style = """
                -fx-background-color: rgba(255,255,255,0.05);
                -fx-background-radius: 5px;
                -fx-padding: 8px;
                -fx-border-color: rgba(255,255,255,0.02);
                -fx-border-width: 1px;
                -fx-border-radius: 5px;
            """
        }

        return friendItem
    }

    private fun createNewsItem(title: String, description: String): VBox {
        val newsItem = VBox(5.0)
        newsItem.style = """
            -fx-background-color: rgba(255,255,255,0.05);
            -fx-background-radius: 5px;
            -fx-padding: 10px;
            -fx-border-color: rgba(255,255,255,0.02);
            -fx-border-width: 1px;
            -fx-border-radius: 5px;
        """

        // Imagen de noticia (placeholder)
        val newsImage = Region()
        newsImage.prefHeight = 120.0
        newsImage.style = """
            -fx-background-color: linear-gradient(to bottom right, #222222, #1A1A1A);
            -fx-background-radius: 5px;
            -fx-border-color: rgba(255,255,255,0.05);
            -fx-border-width: 1px;
            -fx-border-radius: 5px;
        """

        // Título de la noticia
        val titleLabel = Label(title)
        titleLabel.textFill = Color.WHITE
        titleLabel.font = Font.font("System", FontWeight.BOLD, 14.0)

        // Descripción de la noticia
        val descLabel = Label(description)
        descLabel.textFill = Color.web("#AAAAAA")
        descLabel.font = Font.font("System", 12.0)

        newsItem.children.addAll(newsImage, titleLabel, descLabel)

        // Añadir animación de hover
        newsItem.setOnMouseEntered {
            val transition = TranslateTransition(Duration.millis(150.0), newsItem)
            transition.toY = -3.0
            transition.play()

            newsItem.style = """
                -fx-background-color: rgba(255,255,255,0.08);
                -fx-background-radius: 5px;
                -fx-padding: 10px;
                -fx-border-color: rgba(255,255,255,0.05);
                -fx-border-width: 1px;
                -fx-border-radius: 5px;
            """
        }

        newsItem.setOnMouseExited {
            val transition = TranslateTransition(Duration.millis(150.0), newsItem)
            transition.toY = 0.0
            transition.play()

            newsItem.style = """
                -fx-background-color: rgba(255,255,255,0.05);
                -fx-background-radius: 5px;
                -fx-padding: 10px;
                -fx-border-color: rgba(255,255,255,0.02);
                -fx-border-width: 1px;
                -fx-border-radius: 5px;
            """
        }

        return newsItem
    }

    private fun mostrarAlerta(mensaje: String) {
        val alerta = Alert(Alert.AlertType.WARNING)
        alerta.title = "Campos incompletos"
        alerta.headerText = null
        alerta.contentText = mensaje

        // Estilizar el diálogo
        val dialogPane = alerta.dialogPane
        dialogPane.style = """
            -fx-background-color: #0A0A0A;
            -fx-text-fill: white;
        """

        // Estilizar los botones
        dialogPane.lookupButton(ButtonType.OK).style = """
            -fx-background-color: #43B581;
            -fx-text-fill: white;
        """

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
