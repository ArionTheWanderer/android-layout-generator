package services

import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import ComponentType
import LayoutType

class XMLGenerator(private val project: Project) {

    private val root_def = "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "    android:orientation=\"vertical\" android:layout_width=\"match_parent\"\n" +
            "    android:layout_height=\"match_parent\"\n" +
            "    android:padding=\"10dp\"\n" +
            "    android:gravity=\"center\">\n</LinearLayout>"

    private fun rootHead() = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "    android:orientation=\"vertical\" android:layout_width=\"match_parent\"\n" +
            "    android:layout_height=\"match_parent\"\n" +
            "    android:padding=\"10dp\"\n" +
            "    android:gravity=\"center\">\n"

    private fun rootTail() = "</LinearLayout>"

    private fun editText(hint: String): String {
        return "<EditText\n" +
                "        android:layout_marginTop=\"5dp\"\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:hint=\"$hint\"\n" +
                "        android:textSize=\"16sp\"/>\n"
    }

    private fun editTextPassword(hint: String): String {
        return "<EditText\n" +
                "        android:layout_marginTop=\"5dp\"\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:inputType=\"textPassword\"\n" +
                "        android:hint=\"$hint\"\n" +
                "        android:textSize=\"16sp\"/>\n"
    }

    private fun textField(text: String): String {
        return "<TextView\n" +
                "        android:layout_marginTop=\"5dp\"\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:text=\"$text\"\n" +
                "        android:textSize=\"16sp\"/>\n"
    }

    private fun button(text: String): String {
        return "<Button\n" +
                "            android:layout_width=\"wrap_content\"\n" +
                "            android:layout_height=\"wrap_content\"\n" +
                "            android:text=\"$text\"/>\n"
    }

    private fun horizLinLayout_head() = "<LinearLayout\n" +
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:padding=\"10dp\"\n" +
            "        android:gravity=\"center\"\n" +
            "        android:orientation=\"horizontal\">\n"

    private fun horizLinLayout_tail() = "</LinearLayout>\n"

    fun createXMLFile(fileName: String, layoutType: LayoutType, addedComponentsMap: MutableMap<ComponentType, MutableList<String>>) {

        val layoutText = when (layoutType) {
            is LayoutType.Login -> {
                generateLoginLayout(addedComponentsMap)
            }
            is LayoutType.Register -> {
                generateRegisterLayout(addedComponentsMap)
            }
        }

        val virtualFiles: List<VirtualFile> =
            ProjectRootManager.getInstance(project).contentSourceRoots.filter { it.name.contains("res") }
        val directory: PsiDirectory =
            PsiManager.getInstance(project).findDirectory(virtualFiles[0])!!
        val subdirectory: PsiDirectory =
            directory.findSubdirectory("layout") ?: directory.createSubdirectory("layout")
        val newXMLFile = PsiFileFactory.getInstance(project).createFileFromText(fileName, XMLLanguage.INSTANCE, layoutText)

        val runnable = Runnable {
            subdirectory.add(newXMLFile)
        }
        ApplicationManager.getApplication().runWriteAction(runnable)

    }

    private fun generateLoginLayout(addedComponentsMap: MutableMap<ComponentType, MutableList<String>>): String {
        var layoutText = rootHead()

        val textFieldsContents = addedComponentsMap[ComponentType.TextField]
        if (textFieldsContents?.size ?: 0 > 0) {
            textFieldsContents?.forEach {
                layoutText = layoutText.plus(textField(it))
            }
        }

        layoutText = layoutText.plus(editText("Login")).plus(editTextPassword("Password"))

        val editFieldsContents = addedComponentsMap[ComponentType.EditTextField]
        if (editFieldsContents?.size ?: 0 > 0) {
            editFieldsContents?.forEach {
                layoutText = layoutText.plus(editText(it))
            }
        }

        val buttonsContents = addedComponentsMap[ComponentType.Button]
        if (buttonsContents != null && buttonsContents.size > 3)
            // Если нечетное число кнопок
            if (buttonsContents.size % 2 == 1) {
                // Если число кнопок является степенью 3
                if (isPowerOf(buttonsContents.size, 3)) {
                    var numberRemaining = buttonsContents.size
                    while (numberRemaining != 0) {
                        layoutText = layoutText.plus(horizLinLayout_head())
                        repeat(3) {
                            val buttonContent = buttonsContents[numberRemaining - 1]
                            layoutText = layoutText.plus(button(buttonContent))
                            numberRemaining -= 1
                        }
                        layoutText = layoutText.plus(horizLinLayout_tail())
                    }
                } else {
                    var numberRemaining = buttonsContents.size
                    while (numberRemaining > 2 ) {
                        layoutText = layoutText.plus(horizLinLayout_head())
                        repeat(3) {
                            val buttonContent = buttonsContents[numberRemaining - 1]
                            layoutText = layoutText.plus(button(buttonContent))
                            numberRemaining -= 1
                        }
                        layoutText = layoutText.plus(horizLinLayout_tail())
                    }
                    if (numberRemaining != 0) {
                        layoutText = layoutText.plus(horizLinLayout_head())
                        while (numberRemaining != 0) {
                            val buttonContent = buttonsContents[numberRemaining - 1]
                            layoutText = layoutText.plus(button(buttonContent))
                            numberRemaining -= 1
                        }
                        layoutText = layoutText.plus(horizLinLayout_tail())
                    }
                }
            // Если четное число кнопок
            } else {
                // Если число кнопок является степенью 2
                if (isPowerOf(buttonsContents.size, 2)) {
                    var numberRemaining = buttonsContents.size
                    while (numberRemaining != 0) {
                        layoutText = layoutText.plus(horizLinLayout_head())
                        repeat(2) {
                            val buttonContent = buttonsContents[numberRemaining - 1]
                            layoutText = layoutText.plus(button(buttonContent))
                            numberRemaining -= 1
                        }
                        layoutText = layoutText.plus(horizLinLayout_tail())
                    }
                } else {
                    var numberRemaining = buttonsContents.size
                    while (numberRemaining > 2 ) {
                        layoutText = layoutText.plus(horizLinLayout_head())
                        repeat(3) {
                            val buttonContent = buttonsContents[numberRemaining - 1]
                            layoutText = layoutText.plus(button(buttonContent))
                            numberRemaining -= 1
                        }
                        layoutText = layoutText.plus(horizLinLayout_tail())
                    }


                    if (numberRemaining != 0) {
                        layoutText = layoutText.plus(horizLinLayout_head())
                        while (numberRemaining != 0) {
                            val buttonContent = buttonsContents[numberRemaining - 1]
                            layoutText = layoutText.plus(button(buttonContent))
                            numberRemaining -= 1
                        }
                        layoutText = layoutText.plus(horizLinLayout_tail())
                    }
                }
            }
        // число кнопок <= 3
        else if (buttonsContents != null && buttonsContents.size > 0){
            var numberRemaining = buttonsContents.size
            if (numberRemaining != 0) {
                layoutText = layoutText.plus(horizLinLayout_head())
                while (numberRemaining != 0) {
                    val buttonContent = buttonsContents[numberRemaining - 1]
                    layoutText = layoutText.plus(button(buttonContent))
                    numberRemaining -= 1
                }
                layoutText = layoutText.plus(horizLinLayout_tail())
            }
        }

        layoutText = layoutText.plus(button("Login"))

        layoutText = layoutText.plus(rootTail())
        return layoutText
    }

    private fun generateRegisterLayout(addedComponentsMap: MutableMap<ComponentType, MutableList<String>>):String {
        return ""
    }

    private fun isPowerOf(x: Int, y: Int): Boolean {
        var num = x
        while (num > 1 && num % y == 0) {
            num /= y
        }
        return num == 1
    }
}
