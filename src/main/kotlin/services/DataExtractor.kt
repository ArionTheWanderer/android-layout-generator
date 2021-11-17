package services

import model.AddedComponentInfo
import ComponentType
import javax.swing.*

class DataExtractor {

    fun extractDataFromUI(addedComponentsMap: MutableMap<JButton, JPanel>): MutableMap<ComponentType, MutableList<String>> {
        val dataToPass: MutableMap<ComponentType, MutableList<String>> = mutableMapOf()

        val buttonsContentsList: MutableList<String> = mutableListOf()
        val editTextFieldContentsList: MutableList<String> = mutableListOf()
        val textFieldContentsList: MutableList<String> = mutableListOf()

        addedComponentsMap.forEach {
            val elementPanel = it.value.components[0] as JPanel

            val componentData = AddedComponentInfo()

            elementPanel.components.forEach { component ->

                when (component) {
                    is JLabel -> {
                        componentData.type = component.text
                    }
                    is JTextField -> {
                        componentData.text = component.text
                    }
                    is JCheckBox -> {
                        if (component.isSelected)
                            componentData.isCheckboxSelected = true
                    }
                    else -> {}
                }

            }

            if (componentData.type.contains("Text")) {
                if (componentData.isCheckboxSelected)
                    editTextFieldContentsList.add(componentData.text)
                else
                    textFieldContentsList.add(componentData.text)
            } else if (componentData.type.contains("Button"))
                buttonsContentsList.add(componentData.text)
        }

        dataToPass[ComponentType.Button] = buttonsContentsList
        dataToPass[ComponentType.TextField] = textFieldContentsList
        dataToPass[ComponentType.EditTextField] = editTextFieldContentsList

        return dataToPass
    }
}
