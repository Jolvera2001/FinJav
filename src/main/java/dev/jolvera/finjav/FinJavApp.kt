package dev.jolvera.finjav

import dev.jolvera.finjav.controllers.MainPageController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.GlobalContext.startKoin

class FinJavApp : Application(), KoinComponent {
    override fun init() {
        startKoin {
            modules(FinJavKoinModule)
        }
    }

    override fun start(primaryStage: Stage?) {
        try {
            val loader = FXMLLoader(FinJavApp::class.java.getResource("main-page-view.fxml"))
            val controller = get<MainPageController>()
            loader.setController(controller)

            val root = loader.load() as Parent
            val scene = Scene(root, 800.0, 600.0)
            primaryStage?.scene = scene
            primaryStage?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}