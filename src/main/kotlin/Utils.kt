import astminer.common.getNormalizedToken
import psi.PsiNode

enum class GranularityLevel {
    File,
    Method
}

enum class Dataset(val folderName: String) {
    Train("train"),
    Val("val"),
    Test("test")
}

fun printTree(root: PsiNode, withTypes: Boolean, indent: Int = 0, delimiter: String = "--", indentStep: Int = 2) {
    print(delimiter.repeat(indent))
    print("${root.getTypeLabel()}: ${root.getNormalizedToken()}")
    if (withTypes) {
        print(" / ${root.resolvedType}")
    }
    print("\n")
    root.getChildren().forEach {
        printTree(it, withTypes, indent + indentStep, delimiter, indentStep)
    }
}
