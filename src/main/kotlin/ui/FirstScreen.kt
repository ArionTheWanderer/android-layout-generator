package ui

import services.Container
import javax.swing.JPanel
import javax.swing.JButton
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.border.EmptyBorder

class FirstScreen(private val container: Container): JPanel() {

    init {

        setBounds(0, 0, 450, 450)
        layout = BoxLayout(this, BoxLayout.X_AXIS)

        val panel = JPanel()
        panel.border = EmptyBorder(5, 0, 5, 0)
        add(panel)
        panel.layout = FlowLayout(FlowLayout.CENTER, 5, 5)

        val panel2 = JPanel()
        panel.add(panel2)
        panel2.layout = GridLayout(1, 2, 0, 0)

        val btnLogin = JButton("Login")
        panel2.add(btnLogin)
        btnLogin.addActionListener {
            container.navToLogin()
        }

        val btnRegister = JButton("Register")
        panel2.add(btnRegister)
    }
}
