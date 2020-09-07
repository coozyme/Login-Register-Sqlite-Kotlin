package apps.programmeranak.loreg.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import apps.programmeranak.loreg.R
import apps.programmeranak.loreg.helpers.InputValidation
import apps.programmeranak.loreg.sql.DatabaseHelper
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener  {


    private lateinit var Email : EditText
    private lateinit var password : EditText

    private lateinit var btnLogin : Button
    private lateinit var btnReg : Button
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//
//        //hiding the views
        initViews()
//
//        // initializing the listeners
        initListeners()
//
//        // initializing the objects
        initObjects()

    }


    private fun initViews () {
        Email = edt_emailLogin
        password = edt_passwordLogin
        btnLogin = btn_Login
        btnReg = btn_register
    }
//
    private fun initListeners(){
        btnLogin!!.setOnClickListener(this)
        btnReg!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_Login -> verifyFromSQLite()
            R.id.btn_register -> {
                   val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
            }
        }
    }
//
//
//    //this methode is to initialize objects to be used
    private fun initObjects() {
        databaseHelper = DatabaseHelper(this)
        inputValidation = InputValidation(this)
    }

private fun verifyFromSQLite() {
    if (!inputValidation!!.isInputEditTextFilled(Email!!, "Enter Valid Email")) {
        return
    }
    if (!inputValidation!!.isInputEditTextEmail(Email!!, "Enter Valid Email")) {
        return
    }
    if (!inputValidation!!.isInputEditTextFilled(password!!, "Enter Valid Email")) {
        return
    }
    if (databaseHelper.checkUser(Email!!.text.toString().trim { it <= ' ' })) {
        val accountsIntent = Intent(this, MainActivity::class.java)
        accountsIntent.putExtra("EMAIL", Email!!.text.toString().trim { it <= ' ' })
        emptyInputEditText()
        startActivity(accountsIntent)
    } else {
        // Snack Bar to show success message that record is wrong
        Snackbar.make(nestedScrollViewForlog!!, "Wrong Email or Password", Snackbar.LENGTH_LONG)
            .show()
    }
}

    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        Email!!.text = null
        password!!.text = null
    }
}

