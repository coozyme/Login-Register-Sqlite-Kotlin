package apps.programmeranak.loreg.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import apps.programmeranak.loreg.R
import apps.programmeranak.loreg.R.layout
import apps.programmeranak.loreg.helpers.InputValidation
import apps.programmeranak.loreg.modal.User
import apps.programmeranak.loreg.sql.DatabaseHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*
import java.nio.channels.InterruptedByTimeoutException


class RegisterActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var namaLengkap :EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var konfirmPassword :EditText
    private lateinit var btnRegist : Button
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_register)


//        // initializing the views
        initViews()
//        // initializing the listeners
        initListeners()
//        // initializing the objects
        initObjects()

        tv_haveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }




    /**
     * This method is to initialize views
     */
    private fun initViews() {
        namaLengkap = edt_namaLengkap as EditText
        email = edt_emailRegist as EditText
        password = edt_passwordRegist as EditText
        konfirmPassword = edt_konfPasswordRegist as EditText
        btnRegist = btn_regist as Button
    }

//    /**
//     * This method is to initialize listeners
//     */
    private fun initListeners() {
        btnRegist.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
        R.id.btn_regist -> postDataToSQLite()
        }
    }

//
//    /**
//     * This method is to initialize objects to be used
//     */
    private fun initObjects() {
        inputValidation = InputValidation(this)
        databaseHelper = DatabaseHelper(this )


}


    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private fun postDataToSQLite() {
        if (!inputValidation!!.isInputEditTextFilled(namaLengkap ,"Enter Full Name")) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(email , "Enter Valid Email")) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(password ,  "Enter Valid Email")) {
            return
        }
        if (!inputValidation!!.isInputEditTextMatches(password ,
                konfirmPassword ,
                "Password Does Not Matches")) {
            return
        }
        if (!databaseHelper!!.checkUser(email!!.text.toString().trim())) {
            var user = User(namaLengkap = namaLengkap!!.text.toString().trim(),
                            email = email!!.text.toString().trim(),
                            password = password!!.text.toString().trim())

            databaseHelper!!.addUser(user)

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView!!, "Registration Successful", Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView!!,"Email Already Exists", Snackbar.LENGTH_LONG).show()
        }
    }
    private fun emptyInputEditText() {
        namaLengkap!!.text = null
        email!!.text = null
        password!!.text = null
        konfirmPassword!!.text = null
    }
}


