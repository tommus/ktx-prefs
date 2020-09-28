package co.windly.ktxprefs.sample.kotlin.presentation.menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import co.windly.ktxprefs.sample.kotlin.R
import co.windly.ktxprefs.sample.kotlin.databinding.ActivityMenuBinding
import co.windly.ktxprefs.sample.kotlin.presentation.coroutine.CoroutineActivity
import co.windly.ktxprefs.sample.kotlin.presentation.reactive.ReactiveActivity
import co.windly.ktxprefs.sample.kotlin.presentation.simple.SimpleActivity

class MenuActivity : ComponentActivity() {

  //region View Model

  private val viewModel: MenuViewModel by viewModels()

  //endregion

  //region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Inflate binding.
    val binding =
      DataBindingUtil.setContentView<ActivityMenuBinding>(
        this, R.layout.activity_menu, null)

    // Configure lifecycle.
    binding.lifecycleOwner = this

    // Bind variables.
    binding.viewModel = viewModel

    // Observe coroutine clicked.
    viewModel
      .coroutineClicked
      .observe(this, Observer { startActivity<CoroutineActivity>() })

    // Observe reactive clicked.
    viewModel
      .reactiveClicked
      .observe(this, Observer { startActivity<ReactiveActivity>() })

    // Observe simple clicked.
    viewModel
      .simpleClicked
      .observe(this, Observer { startActivity<SimpleActivity>() })
  }

  //endregion

  //region Extension

  private inline fun <reified T : Activity> Context.startActivity() {
    Intent(this, T::class.java)
      .also { startActivity(it) }
  }

  //endregion
}
