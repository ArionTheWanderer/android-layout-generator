package services

import ComponentType
import LayoutType

interface Container {
    fun navToLogin()
    fun navToRegister()
    fun passDataAndCloseWindow(fileName: String, layoutType: LayoutType, addedComponentsMap: MutableMap<ComponentType, MutableList<String>>)
}
