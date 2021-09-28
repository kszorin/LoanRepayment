package ru.kszorin.loanrepayment.feature_parameters.presentation

sealed class ParametersState {

	object Initial : ParametersState()

	data class Content(
		val sum: String,
		val period: String,
		val rate: String,
		val monthAmount: MonthAmountSubState,
	) : ParametersState()

	object Calculation : ParametersState()
}

sealed class MonthAmountSubState {
	object Gone : MonthAmountSubState()

	data class Visible(
		val value: String
	) : MonthAmountSubState()
}