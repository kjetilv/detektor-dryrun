package com.github.kjetilv.detektordryrun

import io.gitlab.arturbosch.detekt.api.*
import io.gitlab.arturbosch.detekt.rules.isAbstract
import org.jetbrains.kotlin.com.intellij.openapi.util.Key
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.BindingContext

class SlightlyMoreSeriousProcessor() : FileProcessListener {

    private val allKey: Key<Int> = Key<Int>("number of declarations")

    private val absKey: Key<Int> = Key<Int>("number of abstract declarations")

    private val visitor: DeclarationsVisitor = DeclarationsVisitor()

    override fun onFinish(files: List<KtFile>, result: Detektion, bindingContext: BindingContext) {
        val count = files
            .mapNotNull { it.getUserData(allKey) }
            .sum()
        val absCount = files
            .mapNotNull { it.getUserData(absKey) }
            .sum()
        result.add(ProjectMetric(allKey.toString(), count))
        result.add(ProjectMetric(absKey.toString(), absCount))
        result.add(ProjectMetric("abstract declarations per 1000", visitor.perMille, isDouble = true, conversionFactor = 1000))
    }

    override fun onProcess(file: KtFile, bindingContext: BindingContext) {
        file.accept(visitor)
        file.putUserData(allKey, visitor.declarationsCount)
        file.putUserData(absKey, visitor.abstractDeclarationsCount)
    }

    inner class DeclarationsVisitor : DetektVisitor() {

        internal var abstractDeclarationsCount = 0

        internal var declarationsCount = 0

        val perMille get() = if (declarationsCount > 0) 1000 * abstractDeclarationsCount / declarationsCount else 0

        override fun visitKtFile(file: KtFile) {
            super.visitKtFile(file)
            file.putUserData(allKey, declarationsCount)
            file.putUserData(absKey, abstractDeclarationsCount)
        }

        override fun visitDeclaration(dcl: KtDeclaration) {
            super.visitDeclaration(dcl)
            if (dcl.isAbstract()) {
                abstractDeclarationsCount++
            }
            declarationsCount++
        }
    }

}
