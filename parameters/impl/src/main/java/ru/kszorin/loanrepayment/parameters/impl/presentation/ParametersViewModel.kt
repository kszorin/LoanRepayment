package ru.kszorin.loanrepayment.parameters.impl.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kszorin.loanrepayment.loan.api.domain.usecase.CalculatePaymentsUsecase

class ParametersViewModel : ViewModel() {

    private val _state: MutableLiveData<ParametersState> = MutableLiveData(ParametersState.Initial)
    val state: LiveData<ParametersState> = _state

    init {
        _state.value = ParametersState.Content(
            sum = "0.0",
            period = "0",
            rate = "0.0",
            monthAmount = MonthAmountSubState.Gone,
        )
    }

    fun changeSum(sum: String) {
        (_state.value as? ParametersState.Content)?.let { contentState ->
            sum.toPositiveDouble()?.also {
                _state.value = contentState.copy(
                    sum = sum,
                )
            }
        }
    }

    fun changePeriod(period: String) {
        (_state.value as? ParametersState.Content)?.let { contentState ->
            period.toPositiveDouble()?.also {
                _state.value = contentState.copy(
                    period = period,
                )
            }
        }
    }

    fun changeRate(rate: String) {
        (_state.value as? ParametersState.Content)?.let { contentState ->
            rate.toPercents()?.also {
                _state.value = contentState.copy(
                    rate = rate,
                )
            }
        }
    }

    fun calculate() {
        (_state.value as? ParametersState.Content)?.let { contentState ->
            val sum = contentState.sum.toPositiveDouble()
            val period = contentState.period.toPositiveInt()
            val rate = contentState.rate.toPercents()

            if (sum != null && period != null && rate != null) {
                _state.value = ParametersState.Calculation
                val calculatePaymentsUsecase = CalculatePaymentsUsecase()
                val result = calculatePaymentsUsecase(sum, period, rate).amount
                val roundedResultString = String.format("%.2f", result)
                _state.value = contentState.copy(monthAmount = MonthAmountSubState.Visible(roundedResultString))
            }
        }
    }

    private fun String.toPositiveDouble(): Double? {
        val value = this.toDoubleOrNull()
        return value?.let {
            if (value > 0) {
                value
            } else {
                null
            }
        }
    }

    private fun String.toPositiveInt(): Int? {
        val value = this.toIntOrNull()
        return value?.let {
            if (value > 0) {
                value
            } else {
                null
            }
        }
    }

    private fun String.toPercents(): Double? {
        val value = this.toDoubleOrNull()
        return value?.let {
            if (value in 0.0..100.0) {
                value
            } else {
                null
            }
        }
    }
}