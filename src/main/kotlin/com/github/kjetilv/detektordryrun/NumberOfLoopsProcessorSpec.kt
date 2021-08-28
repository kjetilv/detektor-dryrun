package com.github.kjetilv.detektordryrun

import io.github.detekt.metrics.processors.AbstractProjectMetricProcessor
import io.gitlab.arturbosch.detekt.api.DetektVisitor
import org.jetbrains.kotlin.com.intellij.openapi.util.Key
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.BindingContext

class NumberOfLoopsProcessor(
    override val key: Key<Int> = Key<Int>("number of declarations"),
    override val visitor: DeclarationsVisitor = DeclarationsVisitor(key)
    ) : AbstractProjectMetricProcessor() {

    override fun onProcess(file: KtFile, bindingContext: BindingContext) {
        super.onProcess(file, bindingContext)
        file.putUserData(numberOfEnumsKey, visitor.enumCount)
    }

    companion object {
        val numberOfEnumsKey = Key<Int>("number of loops")
    }

     class DeclarationsVisitor(val key: Key<Int>) : DetektVisitor() {

        internal var enumCount = 0

        override fun visitKtFile(file: KtFile) {
            super.visitKtFile(file)
            file.putUserData(key, enumCount)
        }

        override fun visitDeclaration(dcl: KtDeclaration) {
            super.visitDeclaration(dcl)
            enumCount++
        }
    }

}
