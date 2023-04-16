package ru.kszorin.loanrepayment.parameters.impl.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ParametersViewModelTest {

    @Rule
    @JvmField
    val testExecutor = InstantTaskExecutorRule()

    private lateinit var viewModel: ParametersViewModel
    private val stateObserver: Observer<ParametersState> = mock()

    private val contentStata = ParametersState.Content(
        sum = "0.0",
        period = "0",
        rate = "0.0",
        monthAmount = MonthAmountSubState.Gone
    )

    @Before
    fun setUp() {
        viewModel = ParametersViewModel()
    }

    @Test
    fun `WHEN change sum with correct value EXPECT change sum in state`() {
        val expectedState = contentStata.copy(sum = "20000")
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
        val expectedState = contentStata.copy(period = "24")
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
        val expectedState = contentStata.copy(rate = "10.5")
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
}