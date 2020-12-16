/*
 * Copyright (c) 2020.  Anastasiia Birillo, Elena Lyulina
 */

package org.jetbrains.research.ml.ast.transformations.util

import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.CodeStyleManager
import org.jetbrains.research.ml.ast.util.ParametrizedBaseTest
import org.junit.Before
import org.junit.Ignore
import org.junit.runners.Parameterized
import java.io.File

@Ignore
open class TransformationsTest(testDataRoot: String) :
    ParametrizedBaseTest(testDataRoot),
    IBaseTransformationsTestHelper by BaseTransformationsTestHelper(),
    IBaseTransformationsTest {

    @JvmField
    @Parameterized.Parameter(0)
    var inFile: File? = null

    @JvmField
    @Parameterized.Parameter(1)
    var outFile: File? = null

    @Before
    override fun mySetUp() {
        super.mySetUp()
        codeStyleManager = CodeStyleManager.getInstance(project)
    }

    override fun assertCodeTransformation(
        inFile: File,
        outFile: File,
        transformation: (PsiElement, Boolean) -> Unit
    ) {
        assertCodeTransformation(
            inFile,
            outFile,
            transformation,
            myFixture,
            project,
            logger = LOG,
            toCheckFileStructure = true
        )
    }
}
