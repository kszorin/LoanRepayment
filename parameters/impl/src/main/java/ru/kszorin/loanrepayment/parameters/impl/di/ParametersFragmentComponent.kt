package ru.kszorin.loanrepayment.parameters.impl.di

import dagger.Subcomponent
import ru.kszorin.loanrepayment.parameters.impl.ui.ParametersFragment

@Subcomponent
interface ParametersFragmentComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): ParametersFragmentComponent
    }

    fun inject(parametersFragment: ParametersFragment)
}