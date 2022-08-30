package com.roger.dlgandroidtest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.roger.dlgandroidtest.databinding.ActivityCreateAccountBinding

class CreateAccount : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var reenterPassword: EditText
    private var correctEmail: Boolean = false
    private var correctPassword: Boolean = false
    private var correctReenterPassword: Boolean = false
    private var emailList: Array<String> =
        arrayOf("rogershen.sr@gmail.com","iamfull@yahoo.com","yourmama@gmail.com",
        "whattheactualf@gmail.com","hellothisisme@yahoo.com")


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nextButton.isClickable = false
        binding.returnButton.setOnClickListener {
            onBackPressed()
        }
        email = findViewById(R.id.editTextTextEmailAddress)
        password = findViewById(R.id.editTextTextPassword)
        reenterPassword = findViewById(R.id.editTextTextPassword3)

        binding.editTextTextEmailAddress.setOnClickListener {

            if(invalidEmail(email.text)){
                getDrawable(R.drawable.textview_border)
                    .also { binding.editTextTextEmailAddress.background = it }

                binding.emailExist.text = getString(R.string.email_invalid)
                binding.emailExist.visibility = View.VISIBLE
                binding.emailError.visibility = View.VISIBLE
                binding.emailRight.visibility = View.GONE
            }

            else if(accountExist(email.text)){
                getDrawable(R.drawable.textview_border)
                    .also { binding.editTextTextEmailAddress.background = it }
                binding.emailExist.text = getString(R.string.email_exist)
                binding.emailExist.visibility = View.VISIBLE
                binding.emailError.visibility = View.VISIBLE
                binding.emailRight.visibility = View.GONE
            }

            else {
                correctEmail = true
                binding.editTextTextEmailAddress.background =
                    getDrawable(R.drawable.correct_border)
                binding.emailRight.visibility = View.VISIBLE
                binding.emailExist.visibility = View.GONE
                binding.emailError.visibility = View.GONE
                allChecked(correctEmail,correctPassword,correctReenterPassword)
            }

        }

        binding.editTextTextPassword.setOnClickListener {
            if(invalidPassword(password.text)) {
                binding.editTextTextPassword.background =
                    getDrawable(R.drawable.textview_border)
                binding.passwordRight.visibility = View.GONE
            }
            else {
                correctPassword = true
                binding.passwordRight.visibility = View.VISIBLE
                binding.editTextTextPassword.background =
                    getDrawable(R.drawable.correct_border)
                allChecked(correctEmail,correctPassword,correctReenterPassword)
            }
        }

        binding.editTextTextPassword3.setOnClickListener {

            if(invalidPassword(reenterPassword.text)) {
                binding.editTextTextPassword3.background =
                    getDrawable(R.drawable.textview_border)
                binding.passwordNotMatch.visibility = View.VISIBLE
                binding.passwordError.visibility = View.VISIBLE
                binding.passwordReenterRight.visibility = View.GONE
            }

            else if(invalidReenterPassword(reenterPassword.text.toString(), password.text.toString())){
                binding.passwordNotMatch.visibility = View.VISIBLE
                binding.passwordError.visibility = View.VISIBLE
                binding.passwordNotMatch.text = getString(R.string.password_not_match)
                binding.passwordReenterRight.visibility = View.GONE
            }

            else {
                correctReenterPassword = true
                binding.passwordReenterRight.visibility = View.VISIBLE
                binding.editTextTextPassword3.background =
                    getDrawable(R.drawable.correct_border)
                binding.passwordError.visibility = View.GONE
                binding.passwordNotMatch.visibility = View.GONE
                allChecked(correctEmail,correctPassword,correctReenterPassword)

            }
        }

    }

    private fun allChecked(correctEmail: Boolean, correctPassword: Boolean, correctReenterPassword: Boolean) {
        if(correctEmail and correctPassword and correctReenterPassword)
            binding.nextButton.alpha = 1F
        else
            binding.nextButton.alpha = 0.4F
    }

    private fun accountExist(email: Editable): Boolean {
        for(each_email in emailList){
            if(email.toString() == each_email)
                return true
        }
        return false
    }

    private fun invalidReenterPassword(reenterPassword: String, password: String): Boolean {
        return reenterPassword!=password
    }

    private fun invalidPassword(password: Editable): Boolean {
        return if(password.length < 8)
            true
        else if(password.firstOrNull { it.isDigit() } == null)
            true
        else if(password.filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null)
            true
        else if(password.filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null)
            true
        else password.firstOrNull { !it.isLetterOrDigit() } != null

    }

    private fun invalidEmail(email: Editable): Boolean {
       if(TextUtils.isEmpty(email))
           return true
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}