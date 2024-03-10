package ru.kszorin.loanrepayment.app

import android.app.Application
import ru.kszorin.loanrepayment.app.di.DaggerApplicationComponent
import ru.kszorin.loanrepayment.parameters.impl.di.ParametersFragmentComponent
import ru.kszorin.loanrepayment.parameters.impl.di.ParametersFragmentComponentProvider

class LoanRepaymentApplication : Application(), ParametersFragmentComponentProvider {

    val applicationComponent = DaggerApplicationComponent.create()

    override fun provideParametersFragmentComponent(): ParametersFragmentComponent =
        applicationComponent.parametersFragmentComponent().create()
}