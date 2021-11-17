package ui

import javax.swing.JPanel
import services.Container
import java.awt.Color
import javax.swing.JButton
import javax.swing.BoxLayout
import java.awt.FlowLayout
import java.awt.Font
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.border.EmptyBorder
import javax.swing.border.MatteBorder
import javax.swing.SwingConstants
import javax.swing.JCheckBox
import javax.swing.JTextField
import LayoutType
import services.DataExtractor

private const val maxNumberOfButtons = 10
private const val maxNumberOfTextFields = 4

class LoginScreen(private val container: Container): JPanel() {

    private var numberOfButtons = 1

    private var numberOfTextFields = 2

    private var addedComponentsMap: MutableMap<JButton, JPanel> = mutableMapOf()

    private val dataExtractor = DataExtractor()

    init {
        border = EmptyBorder(10, 10, 10, 10)
        setBounds(0, 0, 450, 450)
        layout = BoxLayout(this, BoxLayout.Y_AXIS)

        val preinstalled = JPanel()
        preinstalled.border = MatteBorder(1, 1, 1, 1, Color(0, 0, 0))
        add(preinstalled)
        preinstalled.layout = BoxLayout(preinstalled, BoxLayout.Y_AXIS)

        val panel4 = JPanel()
        panel4.border = EmptyBorder(0, 5, 10, 5)
        preinstalled.add(panel4)
        panel4.layout = BoxLayout(panel4, BoxLayout.X_AXIS)

        val lblNewLabel3 = JLabel("Pre-installed components")
        lblNewLabel3.font = Font("Tahoma", Font.PLAIN, 14)
        panel4.add(lblNewLabel3)

        val panel1 = JPanel()
        panel1.border = EmptyBorder(0, 0, 5, 0)
        preinstalled.add(panel1)
        panel1.layout = BoxLayout(panel1, BoxLayout.X_AXIS)

        val lblNewLabel = JLabel("Login textField")
        panel1.add(lblNewLabel)

        val panel2 = JPanel()
        panel2.border = EmptyBorder(0, 0, 5, 0)
        preinstalled.add(panel2)
        panel2.layout = BoxLayout(panel2, BoxLayout.X_AXIS)

        val lblNewLabel1 = JLabel("Password textField")
        panel2.add(lblNewLabel1)

        val panel3 = JPanel()
        preinstalled.add(panel3)
        panel3.layout = BoxLayout(panel3, BoxLayout.X_AXIS)

        val lblNewLabel2 = JLabel("Login button")
        panel3.add(lblNewLabel2)



        val panel = JPanel()
        panel.border = EmptyBorder(5, 0, 5, 0)
        add(panel)
        panel.layout = FlowLayout(FlowLayout.CENTER, 5, 5)

        val panel5 = JPanel()
        panel.add(panel5)
        panel5.layout = GridLayout(1, 2, 0, 0)

        val btnNewButton = JButton("New button")
        panel5.add(btnNewButton)
        btnNewButton.addActionListener {
            if (numberOfButtons < maxNumberOfButtons)
                addButton()
        }

        val btnNewTextField = JButton("New textField")
        panel5.add(btnNewTextField)
        btnNewTextField.addActionListener {
            if (numberOfTextFields < maxNumberOfTextFields)
                addTextField()
        }

        val panel11 = JPanel()
        add(panel11)
        panel11.layout = null

        val panel10 = JPanel()
        panel10.setBounds(0, 0, 450, 30)
        panel11.add(panel10)

        val lblNewLabel6 = JLabel("File name: ")
        panel10.add(lblNewLabel6)

        val textField3 = JTextField()
        panel10.add(textField3)
        textField3.columns = 15

        val btnGenerate = JButton("Generate")
        panel10.add(btnGenerate)

        btnGenerate.addActionListener {
            val dataToPass = dataExtractor.extractDataFromUI(addedComponentsMap)
            if (textField3.text.isNotEmpty())
                container.passDataAndCloseWindow(textField3.text.plus(".xml"), LayoutType.Login, dataToPass)
            else
                container.passDataAndCloseWindow("new_layout.xml", LayoutType.Login, dataToPass)
        }

    }


    private fun addTextField() {
        val panel6 = JPanel()
        add(panel6, 1)
        panel6.layout = null

        val panel7 = JPanel()
        panel7.setBounds(0, 0, 450, 29)
        panel6.add(panel7)
        panel7.layout = FlowLayout(FlowLayout.CENTER, 5, 5)

        val lblNewLabel4 = JLabel("Text: ")
        panel7.add(lblNewLabel4)

        val textField = JTextField(15)
        panel7.add(textField)

        val chckbxNewCheckBox = JCheckBox("Editable")
        chckbxNewCheckBox.horizontalAlignment = SwingConstants.CENTER
        panel7.add(chckbxNewCheckBox)

        val btnNewButton2 = JButton("Delete")
        panel7.add(btnNewButton2)
        btnNewButton2.addActionListener {
            deleteElementFromMap(btnNewButton2)
            numberOfTextFields -= 1
        }

        addElementToMap(btnNewButton2, panel6)
        numberOfTextFields += 1
        validate()
    }

    private fun addButton() {
        val panel8 = JPanel()
        add(panel8, 1)
        panel8.layout = null

        val panel9 = JPanel()
        panel9.setBounds(0, 0, 450, 29)
        panel8.add(panel9)

        val lblNewLabel5 = JLabel("Button: ")
        panel9.add(lblNewLabel5)

        val textField2 = JTextField()
        panel9.add(textField2)
        textField2.columns = 10

        val btnNewButton3 = JButton("Delete")
        panel9.add(btnNewButton3)
        btnNewButton3.addActionListener {
            deleteElementFromMap(btnNewButton3)
            numberOfButtons -= 1
        }
        addElementToMap(btnNewButton3, panel8)

        numberOfButtons += 1

        validate()
    }

    private fun addElementToMap(button: JButton, panel: JPanel) {
        addedComponentsMap[button] = panel
    }

    private fun deleteElementFromMap(button: JButton) {
        val panelToRemove = addedComponentsMap[button]
        addedComponentsMap.remove(button)
        remove(panelToRemove)
        validate()
    }
}
