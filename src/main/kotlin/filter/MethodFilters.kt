package filter

import com.intellij.psi.PsiMethod
import psi.PsiNode


class ClassConstructorFilter : Filter {
    override fun checkTree(root: PsiNode) = root.wrappedNode is PsiMethod && root.wrappedNode.isConstructor
    companion object {
        const val name = "constructor"
    }
}

class AbstractMethodFilter : Filter {
    override fun checkTree(root: PsiNode): Boolean =
        root.wrappedNode is PsiMethod && root.wrappedNode.modifierList.hasModifierProperty("abstract")
    companion object {
        const val name = "abstract method"
    }
}

class OverrideMethodFilter : Filter {
    override fun checkTree(root: PsiNode): Boolean =
        root.wrappedNode is PsiMethod && root.wrappedNode.modifierList.hasAnnotation("Override")
    companion object {
        const val name = "override method"
    }
}