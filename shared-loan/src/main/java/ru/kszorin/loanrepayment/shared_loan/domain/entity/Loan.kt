package ru.kszorin.loanrepayment.shared_loan.domain.entity

data class Loan(
	val sum: Double,
	val period: Int,
	val rate: Double,
)
