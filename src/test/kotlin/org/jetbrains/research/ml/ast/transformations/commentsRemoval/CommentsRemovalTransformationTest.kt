package org.jetbrains.research.ml.ast.transformations.commentsRemoval

import org.jetbrains.research.ml.ast.transformations.util.TransformationsTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CommentsRemovalTransformationTest : TransformationsTest(getResourcesRootPath(::TransformationsTest)) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: ({0}, {1})")
        fun getTestData() = getInAndOutArray(::CommentsRemovalTransformationTest)
    }

    @Test
    fun testForwardTransformation() {
        assertForwardTransformation(inFile!!, outFile!!, CommentsRemovalTransformation::forwardApply)
    }

    @Test
    fun testBackwardTransformation() {
        assertBackwardTransformation(
            inFile!!,
            CommentsRemovalTransformation::forwardApply,
            CommentsRemovalTransformation::backwardApply
        )
    }
}
