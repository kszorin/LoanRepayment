package ru.kszorin.loanrepayment.loan.impl.domain.entity

data class Loan(
	val id: Int,
	val sum: Double,
	val period: Int,
	val rate: Double,
)