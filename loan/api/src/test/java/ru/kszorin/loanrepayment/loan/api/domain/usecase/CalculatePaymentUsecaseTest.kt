package ru.kszorin.loanrepayment.loan.api.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import ru.kszorin.loanrepayment.loan.api.domain.model.Payment
import kotlin.math.pow

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class CalculatePaymentUsecaseTest {

	private val usecase = CalculatePaymentsUsecase()

	@Test
	fun `WHEN calculate payment EXPECT loan payment`() = runTest {
		val s = 200000.0
		val rate = 12.0
		val p = rate / 100 / 12
		val n = 24
		val x = s * (p + p / ((1 + p).pow(n) - 1))
		val xStr = "%.2f".format(x)
		println("Expected:\ns = $s\np = $p\nn=$n\nx=$xStr")

		val actual = usecase(
			sum = 200000.0,
			period = 24,
			rate = 12.0,
		)
		assertEquals(Payment(x), actual)
	}
}