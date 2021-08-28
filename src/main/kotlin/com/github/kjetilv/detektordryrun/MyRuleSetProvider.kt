package com.github.kjetilv.detektordryrun

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class MyRuleSetProvider : RuleSetProvider {
  override val ruleSetId: String = "KjetilsRuleset"

  override fun instance(config: Config): RuleSet {
    return RuleSet(
      ruleSetId,
      listOf(
        ParameterListAverageSize(config),
      ),
    )
  }
}
