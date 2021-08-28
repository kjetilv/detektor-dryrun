package com.github.kjetilv.detektordryrun

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtParameterList

class ParameterListAverageSize(config: Config) : Rule(config) {

    var listsFound = 0

    var paramtersCounted = 0

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Style,
        "Parameter trend counter",
        Debt.FIVE_MINS,
    )

    override fun visitParameterList(list: KtParameterList) {
        super.visitParameterList(list)
        listsFound += 1
        paramtersCounted += list.parameters.size
        println("Did count $this stuff")
    }

    override fun visitClass(klass: KtClass) {
        super.visitClass(klass)

      val d = paramtersCounted.toDouble() / listsFound
      if (d > 6) {
          val i = 100 * paramtersCounted / listsFound
          report(
                ThresholdedCodeSmell(issue,
                    entity = Entity.from(klass),
                    metric = Metric(type = "SIZE", value = i, 6, true, DEFAULT_FLOAT_CONVERSION_FACTOR),
                    message = "The class ${klass.name} has average of $i parameters. " +
                            "Threshold is specified with 6.",
                    references = emptyList()))
        }
        println("Did $this sthuff")
    }
}
