package ru.kszorin.loanrepayment.loan.impl.domain.usecase

import ru.kszorin.loanrepayment.loan.impl.domain.model.Payment
import kotlin.math.pow

class CalculatePaymentsUsecase {

	operator fun invoke(sum: Double, period: Int, rate: Double): Payment {
		val p = rate / 100 / 12
		val x = sum * (p + (p / ((1 + p).pow(period) - 1)))
		return Payment(amount = x)
	}
}