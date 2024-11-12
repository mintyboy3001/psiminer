import com.intellij.openapi.application.ReadAction
import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import psi.language.JavaHandler
import psi.language.KotlinHandler
import psi.language.LanguageHandler
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BasePsiRequiredTest(private val psiSourceFile: File) : BasePlatformTestCase() {

    abstract val handler: LanguageHandler
    private val methods = mutableMapOf<String, PsiElement>()
    private class ResourceException(resourceRoot: String) : RuntimeException("Can't find resources in $resourceRoot")

    // We should define the root resources folder
    @Throws(ResourceException::class)
    override fun getTestDataPath(): String =
        BasePsiRequiredTest::class.java.getResource(resourcesRoot)?.path ?: throw ResourceException(resourcesRoot)

    @BeforeAll
    override fun setUp() {
        super.setUp()
        val methodsFile = File(testDataPath).resolve(psiSourceFile)
        myFixture.configureByFile(methodsFile.path).let {
            ReadAction.run<Exception> {
                handler
                    .splitByGranularity(it, GranularityLevel.Method)
                    .forEach {
                        val methodName = handler.methodProvider.getNameNode(it).text
                        methods[methodName] = it
                    }
            }
        }
    }

    @AfterAll
    override fun tearDown() {
        super.tearDown()
    }

    private class UnknownMethodException(methodName: String, fileName: String) :
        RuntimeException("Can't find method $methodName in $fileName")

    fun getMethod(methodName: String): PsiElement =
        methods[methodName] ?: throw UnknownMethodException(methodName, psiSourceFile.name)

    companion object {
        // We cannot get the root of the class resources automatically.
        private const val resourcesRoot: String = "data"
    }
}

open class JavaPsiRequiredTest(source: String) : BasePsiRequiredTest(dataFolder.resolve("$source.$ext")) {
    override val handler: LanguageHandler = JavaHandler()

    companion object {
        val dataFolder = File("java")
        const val ext = "java"
    }
}

open class KotlinPsiRequiredTest(source: String) : BasePsiRequiredTest(dataFolder.resolve("$source.$ext")) {
    override val handler: LanguageHandler = KotlinHandler()

    companion object {
        val dataFolder = File("kotlin")
        const val ext = "kt"
    }
}


