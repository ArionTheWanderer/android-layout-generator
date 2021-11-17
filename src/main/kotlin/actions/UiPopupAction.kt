package actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ui.Frame

class UiPopupAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        Frame(e.project!!)
    }
}
