package com.emefar.tmdb.ui.login

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.emefar.tmdb.base.MainActivity
import com.emefar.tmdb.databinding.ActivityLoginBinding
import com.emefar.tmdb.model.LoginModel
import com.emefar.tmdb.utils.LDR
import timber.log.Timber

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val factory = LoginViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        viewModel.searchLiveData.observe(this, this.dataObserver)
        binding.viewModel = viewModel



        makeGradientBg()


    }



    private val dataObserver = Observer<LDR<LoginModel>> { result -> // Need add real login response
        when (result?.status) {
            LDR.Status.ERROR -> {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()


             //   showDismissDialog(result.err!!.localizedMessage!!) // This area is hided because real login API is not exist!
             //   result.response
            }

            LDR.Status.SUCCESS -> {

                Timber.d("Success!!")
            }

            else -> {
                Timber.i("Else!!")

            }
        }
    }


    private fun makeGradientBg() {
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                Color.parseColor("#0fd9f5"),
                Color.parseColor("#0963af")
            )
        );
        gradientDrawable.cornerRadius = 0f;

        binding.root.background = gradientDrawable
    }


}