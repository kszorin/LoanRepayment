package ru.kszorin.loanrepayment.app.di

import dagger.Component
import ru.kszorin.loanrepayment.app.ui.MainActivity
import ru.kszorin.loanrepayment.loan.impl.di.LoanModule
import ru.kszorin.loanrepayment.parameters.impl.di.ParametersFragmentComponent
import ru.kszorin.loanrepayment.parameters.impl.di.ParametersFragmentModule

@Component(
    modules = [
        ParametersFragmentModule::class,
        LoanModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun parametersFragmentComponent(): ParametersFragmentComponent.Factory
}