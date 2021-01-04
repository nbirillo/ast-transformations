package org.jetbrains.research.ml.ast.transformations.comparisonUnification

import com.jetbrains.python.PyTokenTypes
import com.jetbrains.python.psi.PyBinaryExpression
import com.jetbrains.python.psi.PyElementGenerator
import com.jetbrains.python.psi.PyElementType
import com.jetbrains.python.psi.PyElementVisitor
import org.jetbrains.research.ml.ast.transformations.PerformedCommandStorage
import org.jetbrains.research.ml.ast.transformations.safePerformCommand

internal class ComparisonUnificationVisitor(private val commandsStorage: PerformedCommandStorage?) :
    PyElementVisitor() {
    override fun visitPyBinaryExpression(node: PyBinaryExpression?) {
        if (node != null) {
            handleBinaryExpression(node)
        }
        super.visitPyBinaryExpression(node)
    }

    private fun handleBinaryExpression(node: PyBinaryExpression) {
        if (!comparisonTokenMap.containsKey(node.operator)) {
            return
        }

        node.swapExpressions()
    }

    private fun PyBinaryExpression.swapExpressions() {
        val binOperator = comparisonTokenMap[operator] ?: return
        val left = leftExpression
        val right = rightExpression ?: return
        val generator = PyElementGenerator.getInstance(project)
        val newBinaryExpression = generator.createBinaryExpression(binOperator, right, left)
        commandsStorage.safePerformCommand({ replace(newBinaryExpression) }, "Replace binary expression")
    }

    companion object {
        private val comparisonTokenMap: Map<PyElementType, String> = mapOf(
            PyTokenTypes.LT to ">",
            PyTokenTypes.LE to ">="
        )
    }
}
