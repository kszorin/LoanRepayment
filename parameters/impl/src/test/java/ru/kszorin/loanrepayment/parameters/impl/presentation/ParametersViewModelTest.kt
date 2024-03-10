package ru.kszorin.loanrepayment.parameters.impl.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.kszorin.loanrepayment.loan.api.domain.model.Payment
import ru.kszorin.loanrepayment.loan.api.domain.usecase.CalculatePaymentsUseCase

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ParametersViewModelTest {

    @Rule
    @JvmField
    val testExecutor = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    private val calculatePaymentsUseCase: CalculatePaymentsUseCase = mock()
    private lateinit var viewModel: ParametersViewModel
    private val stateObserver: Observer<ParametersState> = mock()

    private val contentState = ParametersState.Content(
        sum = "0.0",
        period = "0",
        rate = "0.0",
        monthAmount = MonthAmountSubState.Gone
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = ParametersViewModel(calculatePaymentsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN change sum with correct value EXPECT change sum in state`() {
        val expectedState = contentState.copy(sum = "20000")
        viewModel.state.observeForever(stateObserver)

        viewModel.changeSum("20000")

        verify(stateObserver).onChanged(expectedState)
    }

    @Test
    fun `WHEN change sum with incorrect value EXPECT dont change sum in state`() {
        viewModel.state.observeForever(stateObserver)
        clearInvocations(stateObserver)

        viewModel.changeSum("abc")

        verify(stateObserver, never()).onChanged(any())
    }

    @Test
    fun `WHEN change sum with not positive value EXPECT dont change sum in state`() {
        viewModel.state.observeForever(stateObserver)
        clearInvocations(stateObserver)

        viewModel.changeSum("-20000")

        verify(stateObserver, never()).onChanged(any())
    }

    @Test
    fun `WHEN change period with correct value EXPECT change period in state`() {
        val expectedState = contentState.copy(period = "24")
        viewModel.state.observeForever(stateObserver)

        viewModel.changePeriod("24")

        verify(stateObserver).onChanged(expectedState)
    }

    @Test
    fun `WHEN change period with incorrect value EXPECT dont change period in state`() {
        viewModel.state.observeForever(stateObserver)
        clearInvocations(stateObserver)

        viewModel.changePeriod("abc")

        verify(stateObserver, never()).onChanged(any())
    }

    @Test
    fun `WHEN change period with not positive value EXPECT dont change period in state`() {
        viewModel.state.observeForever(stateObserver)
        clearInvocations(stateObserver)

        viewModel.changePeriod("-10")

        verify(stateObserver, never()).onChanged(any())
    }

    @Test
    fun `WHEN change rate with correct value EXPECT change rate in state`() {
        val expectedState = contentState.copy(rate = "10.5")
        viewModel.state.observeForever(stateObserver)

        viewModel.changeRate("10.5")

        verify(stateObserver).onChanged(expectedState)
    }

    @Test
    fun `WHEN change rate with value more then 100 EXPECT dont change rate in state`() {
        viewModel.state.observeForever(stateObserver)
        clearInvocations(stateObserver)

        viewModel.changeRate("101")

        verify(stateObserver, never()).onChanged(any())
    }

    @Test
    fun `WHEN change rate with not positive value EXPECT dont change rate in state`() {
        viewModel.state.observeForever(stateObserver)
        clearInvocations(stateObserver)

        viewModel.changeRate("-10")

        verify(stateObserver, never()).onChanged(any())
    }

    @Test
    fun `WHEN calculate EXPECT state with month amount`() = runTest {
        val sum = 100000.0
        val period = 24
        val rate = 10.0
        val monthAmount = 4614.49
        whenever(calculatePaymentsUseCase.invoke(sum, period, rate)).then { Payment(monthAmount) }

        val expectedState = contentState.copy(
            sum = "100000.0",
            period = "24",
            rate = "10.0",
            monthAmount = MonthAmountSubState.Visible(value = "4614,49"),
        )

        viewModel.changeSum(sum.toString())
        viewModel.changePeriod(period.toString())
        viewModel.changeRate(rate.toString())

        viewModel.state.observeForever(stateObserver)
        clearInvocations(stateObserver)
        val inOrder = inOrder(stateObserver)

        viewModel.calculate()

        inOrder.verify(stateObserver).onChanged(ParametersState.Calculation)
        inOrder.verify(stateObserver).onChanged(expectedState)
    }
}