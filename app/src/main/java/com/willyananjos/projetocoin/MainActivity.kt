package com.willyananjos.projetocoin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.willyananjos.projetocoin.databinding.ActivityMainBinding
import com.willyananjos.projetocoin.state.ScreenState
import com.willyananjos.projetocoin.viewModel.CryptoViewModel
import com.willyananjos.projetocoin.viewModel.CryptoViewModelFactory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var b : ActivityMainBinding

    private val textBitCoin: TextView by lazy { findViewById(R.id.textViewBitcoin) }
    private val textViewDate: TextView by lazy { findViewById(R.id.textViewData) }
    private val buttonRefresh: Button by lazy { findViewById(R.id.buttonRefresh) }
    private val progressBar: ProgressBar by lazy {findViewById(R.id.progressBar)}

    private val viewModel: CryptoViewModel by viewModels {
        CryptoViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.tickerLiveData.observe(this) { state ->
            when (state) {

                is ScreenState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    buttonRefresh.visibility = View.GONE
                }

                is ScreenState.Error -> {
                    progressBar.visibility = View.GONE
                    buttonRefresh.visibility = View.VISIBLE
                   Toast.makeText(this, "Erro ao carregar os dados", Toast.LENGTH_SHORT).show()
                }

                is ScreenState.Success -> {
                    progressBar.visibility = View.GONE
                    buttonRefresh.visibility = View.VISIBLE
                    textBitCoin.text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).let {
                        it.format(state.data.last.toBigDecimal())
                    }
                    textViewDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date())
                }

            }
        }

        buttonRefresh.setOnClickListener {
            viewModel.refresh()
        }
    }
}