package ru.kszorin.loanrepayment.feature_parameters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.kszorin.loanrepayment.feature_parameters.R
import ru.kszorin.loanrepayment.feature_parameters.presentation.MonthAmountSubState
import ru.kszorin.loanrepayment.feature_parameters.presentation.ParametersState
import ru.kszorin.loanrepayment.feature_parameters.presentation.ParametersViewModel

class ParametersFragment : Fragment() {

	private companion object {
		val PROGRESSBAR_SIZE = 50.dp
	}

	private val parametersViewModel: ParametersViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return ComposeView(requireContext()).apply {
			setContent {
				val currentState: ParametersState by parametersViewModel.state.observeAsState(ParametersState.Initial)
				RenderState(currentState)
			}
		}
	}

	@Composable
	private fun RenderState(state: ParametersState) {
		when (state) {
			is ParametersState.Initial -> Unit

			is ParametersState.Content -> {
				RenderContent(state)
			}

			is ParametersState.Calculation -> {
				RenderProgress()
			}
		}
	}

	@Composable
	private fun RenderContent(
		state: ParametersState.Content,
	) {
		Column {
			TopAppBar(
				title = { Text(text = getString(R.string.parameters_title)) }
			)

			TextField(
				value = state.sum,
				onValueChange = {
					parametersViewModel.changeSum(it)
				},
				label = { Text(text = getString(R.string.sum)) },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				singleLine = true,
			)

			TextField(
				value = state.period,
				onValueChange = parametersViewModel::changePeriod,
				label = { Text(text = getString(R.string.period)) },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				singleLine = true,
			)

			TextField(
				value = state.rate,
				onValueChange = parametersViewModel::changeRate,
				label = { Text(text = getString(R.string.rate)) },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				singleLine = true,
			)

			Button(
				onClick = { parametersViewModel.calculate() }
			) {
				Text(text = getString(R.string.calculate))

			}

			if (state.monthAmount is MonthAmountSubState.Visible) {
				TextField(
					value = state.monthAmount.value,
					onValueChange = { },
					label = { Text(text = getString(R.string.monthAmount)) },
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
}