package ru.kszorin.loanrepayment.loan.api.domain.usecase

import ru.kszorin.loanrepayment.loan.api.domain.model.Payment

interface CalculatePaymentsUseCase {

	suspend operator fun invoke(sum: Double, period: Int, rate: Double): Payment
}