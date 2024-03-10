package ru.kszorin.loanrepayment.loan.impl.di

import dagger.Binds
import dagger.Module
import ru.kszorin.loanrepayment.loan.api.domain.usecase.CalculatePaymentsUseCase
import ru.kszorin.loanrepayment.loan.impl.domain.usecase.CalculatePaymentsUseCaseImpl

@Module
interface LoanModule {

    @Binds
    fun bindCalculatePaymentsUsecase(impl: CalculatePaymentsUseCaseImpl): CalculatePaymentsUseCase
}