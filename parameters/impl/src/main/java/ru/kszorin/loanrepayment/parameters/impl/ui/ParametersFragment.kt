package ru.kszorin.loanrepayment.parameters.impl.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ru.kszorin.loanrepayment.parameters.impl.di.ParametersFragmentComponentProvider
import ru.kszorin.loanrepayment.parameters.impl.presentation.ParametersViewModel
import javax.inject.Inject

class ParametersFragment : Fragment() {

    @Inject
    lateinit var parametersViewModel: ParametersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =

        ComposeView(requireContext()).apply {
            setContent {
                ParametersScreen(parametersViewModel)
            }
        }

    override fun onAttach(context: Context) {
        (activity?.applicationContext as ParametersFragmentComponentProvider)
            .provideParametersFragmentComponent()
            .inject(this)

        super.onAttach(context)
    }
}