package ru.kszorin.loanrepayment.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kszorin.loanrepayment.R
import ru.kszorin.loanrepayment.parameters.impl.ui.ParametersFragment

class MainActivity: AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)

		supportFragmentManager
			.beginTransaction()
			.add(R.id.fragment_container, ParametersFragment())
			.commit()
	}
}