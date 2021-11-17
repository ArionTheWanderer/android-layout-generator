package ui

import com.intellij.openapi.project.Project
import services.Container
import services.XMLGenerator
import javax.swing.JFrame
import javax.swing.JPanel
import java.awt.event.WindowEvent
import ComponentType
import LayoutType


class Frame(project: Project): JFrame("Create screen"), Container {


    var currentScreen: JPanel = FirstScreen(this)
    val xmlGenerator = XMLGenerator(project)

    init {
        setSize(500,500)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        contentPane = currentScreen
        isVisible = true
    }

    override fun navToLogin() {
        currentScreen = LoginScreen(this)
        contentPane = currentScreen
    }

    override fun navToRegister() {}

    override fun passDataAndCloseWindow(fileName: String, layoutType: LayoutType, addedComponentsMap: MutableMap<ComponentType, MutableList<String>>) {
        xmlGenerator.createXMLFile(fileName, layoutType, addedComponentsMap)
        dispatchEvent(WindowEvent(this, WindowEvent.WINDOW_CLOSING))
    }
}
