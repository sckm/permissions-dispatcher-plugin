package com.github.shiraji.permissionsdispatcherplugin.actions

import com.github.shiraji.permissionsdispatcherplugin.models.GeneratePMCodeModel
import com.intellij.codeInsight.CodeInsightActionHandler
import com.intellij.codeInsight.actions.CodeInsightAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.impl.source.tree.java.PsiAnnotationImpl

class GeneratePMCodeAction : CodeInsightAction() {
    override fun getHandler(): CodeInsightActionHandler {
        return Handler()
    }

    override fun update(e: AnActionEvent?) {
        e ?: return
        super.update(e)

        val file = e.getData(CommonDataKeys.PSI_FILE) ?: return
        if (file !is PsiJavaFile) return

        val project = e.getData(CommonDataKeys.PROJECT) ?: return
        val model = GeneratePMCodeModel(project)

        e.presentation.isEnabledAndVisible = model.isActivity(file.classes[0])
                || model.isSupportFragment(file.classes[0])
                || model.isFragment(file.classes[0])
    }
}

class Handler : CodeInsightActionHandler {
    override fun startInWriteAction() = false

    override fun invoke(project: Project, editor: Editor, file: PsiFile) {
        if(file !is PsiJavaFile) return

        val model = GeneratePMCodeModel(project)
        if (!model.isActivityOrFragment(file.classes[0])) return


        val psiAnnotation = file.classes[0].modifierList?.findAnnotation("permissions.dispatcher.RuntimePermissions")

        if(psiAnnotation == null || true) {

            val psiClass = model.createPsiClass("permissions.dispatcher.RuntimePermissions", project)

//            val psiFacade = JavaPsiFacade.getInstance(project);

//            System.out.println(psiFacade.parserFacade.createAnnotationFromText("permissions.dispatcher.RuntimePermissions", psiClass))


//            file.classes[0].add()
        }

//        editor.document.replaceString(1,1, "FOOO!!!")
    }

}