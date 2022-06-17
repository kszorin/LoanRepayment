package ru.kszorin.loanrepayment.parameters.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.kszorin.loanrepayment.parameters.impl.presentation.ParametersViewModel

class ParametersFragment : Fragment() {

    private val parametersViewModel: ParametersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =

        ComposeView(requireContext()).apply {
            setContent {
                ParametersScreen(parametersViewModel)
            }
        }
}