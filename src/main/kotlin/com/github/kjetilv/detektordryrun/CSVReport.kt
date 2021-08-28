package com.github.kjetilv.detektordryrun

import io.gitlab.arturbosch.detekt.api.Detektion
import io.gitlab.arturbosch.detekt.api.OutputReport

class CSVReport : OutputReport() {

    override val ending: String = "csv"

    override fun render(detektion: Detektion): String? =
        detektion.metrics.map { key -> key.value }.joinToString(",") { i -> "$i" }
}