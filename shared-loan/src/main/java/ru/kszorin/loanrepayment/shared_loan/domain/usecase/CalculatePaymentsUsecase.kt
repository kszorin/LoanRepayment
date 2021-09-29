package ru.kszorin.loanrepayment.shared_loan.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.kszorin.loanrepayment.shared_loan.domain.entity.Payment
import kotlin.math.pow

class CalculatePaymentsUsecase {

	suspend operator fun invoke(sum: Double, period: Int, rate: Double): Payment {
		return withContext(Dispatchers.Default)  {
			val p = rate / 100 / 12
			val x = sum * (p + (p / ((1 + p).pow(period) - 1)))
			delay(5000)
			Payment(amount = x)
		}
	}
}