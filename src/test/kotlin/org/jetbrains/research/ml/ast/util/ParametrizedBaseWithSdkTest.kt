package org.jetbrains.research.ml.ast.util

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.impl.ProjectJdkImpl
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.testFramework.TestApplicationManager
import com.intellij.testFramework.fixtures.TempDirTestFixture
import com.intellij.testFramework.fixtures.impl.CodeInsightTestFixtureImpl
import com.intellij.testFramework.fixtures.impl.LightTempDirTestFixtureImpl
import org.junit.Ignore

@Ignore
open class ParametrizedBaseWithSdkTest(testDataRoot: String) : ParametrizedBaseTest(testDataRoot) {
    private lateinit var sdk: Sdk

    /**
     * @return fixture to be used as temporary dir.
     */
    protected open fun createTempDirFixture(): TempDirTestFixture {
        return LightTempDirTestFixtureImpl(true) // "tmp://" dir by default
    }

    override fun setUp() {
        super.setUp()
        setupSdk()
    }

    override fun tearDown() {
        ApplicationManager.getApplication().invokeLater {
            ApplicationManager.getApplication().runWriteAction {
                ProjectJdkTable.getInstance().removeJdk(sdk)
            }
        }
        super.tearDown()
    }
//
//    override fun mySetUp() {
//        TestApplicationManager.getInstance()
//        super.setUp()
//        setupSdk()
//    }

    private fun setupSdk() {
        val project = myFixture.project
        val projectManager = ProjectRootManager.getInstance(project)
        val sdkConfigurer = SdkConfigurer(project, projectManager)
        sdkConfigurer.setProjectSdk(createMockSdk())
    }

    private fun createMockSdk(): Sdk {
        sdk = PythonMockSdk(testDataPath).create("3.8")
        ApplicationManager.getApplication().invokeLater {
            ApplicationManager.getApplication().runWriteAction {
                ProjectJdkTable.getInstance().addJdk(
                    sdk,
                    CodeInsightTestFixtureImpl(myFixture, myFixture.tempDirFixture).testRootDisposable
                )
            }
        }
        return sdk
    }
}
