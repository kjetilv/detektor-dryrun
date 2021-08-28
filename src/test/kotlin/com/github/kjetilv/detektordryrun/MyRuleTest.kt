package com.github.kjetilv.detektordryrun

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

internal class CustomRuleSpec {

    @Ignore
    @Test
    fun `reports inner classes`() {
        val code = """
      class A {
        inner class B
      }
      """
        val findings = ParameterListAverageSize(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }

    @Test
    fun `doesn't report inner classes`() {
        val code = """
      class A {
        class B
      }
      """
        val findings = ParameterListAverageSize(Config.empty).compileAndLint(code)
        assertThat(findings).isEmpty()
    }
}
