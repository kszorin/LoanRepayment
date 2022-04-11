package ru.kszorin.loanrepayment.parameters.impl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.kszorin.loanrepayment.parameters.impl.R
import ru.kszorin.loanrepayment.parameters.impl.presentation.MonthAmountSubState
import ru.kszorin.loanrepayment.parameters.impl.presentation.ParametersState
import ru.kszorin.loanrepayment.parameters.impl.presentation.ParametersViewModel

internal val PROGRESSBAR_SIZE = 50.dp

@Composable
internal fun ParametersScreen(viewModel: ParametersViewModel) {

    val currentState: ParametersState by viewModel.state.observeAsState(ParametersState.Initial)

    when (val state = currentState) {
        is ParametersState.Initial -> Unit

        is ParametersState.Content -> {
            Content(
                state,
                viewModel::changeSum,
                viewModel::changePeriod,
                viewModel::changeRate,
                viewModel::calculate,
            )
        }

        is ParametersState.Calculation -> {
            RenderProgress()
        }
    }
}

@Composable
private fun Content(
    state: ParametersState.Content,
    changeSum: (String) -> Unit,
    changePeriod: (String) -> Unit,
    changeRate: (String) -> Unit,
    calculate: () -> Unit,
) {
    Column {
        TopAppBar(
            title = { Text(text = stringResource(R.string.parameters_title)) }
        )

        TextField(
            value = state.sum,
            onValueChange = {
                changeSum(it)
            },
            label = { Text(text = stringResource(R.string.sum)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
        )

        TextField(
            value = state.period,
            onValueChange = changePeriod,
            label = { Text(text = stringResource(R.string.period)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
        )

        TextField(
            value = state.rate,
            onValueChange = changeRate,
            label = { Text(text = stringResource(R.string.rate)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
        )

        Button(
            onClick = { calculate() }
        ) {
            Text(text = stringResource(R.string.calculate))

        }

        if (state.monthAmount is MonthAmountSubState.Visible) {
            TextField(
                value = state.monthAmount.value,
                onValueChange = { },
                label = { Text(text = stringResource(R.string.monthAmount)) },
                singleLine = true,
            )
        }
    }
}

@Composable
private fun RenderProgress() {
    CircularProgressIndicator(
        modifier = Modifier.requiredSize(PROGRESSBAR_SIZE)
    )
}