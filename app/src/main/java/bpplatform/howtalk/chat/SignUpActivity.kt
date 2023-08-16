package bpplatform.howtalk.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import bpplatform.howtalk.chat.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인증 초기화
        mAuth = FirebaseAuth.getInstance()

        //db 초기화
        mDbRef = Firebase.database.reference

        //회원가입 기능
        binding.SignupBtn.setOnClickListener {

            val name = binding.nameEdit.text.toString().trim()
            val email = binding.emailEdit.text.toString().trim()
            val password = binding.passwordEdit.text.toString().trim()

            SignUp(name, email, password)
        }
    }

    private fun SignUp(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    if (user != null) {
                        addUserToDatebase(name, email, user.uid) // 추가된 부분
                    }
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    val errorMessage = task.exception?.message
                    Toast.makeText(this, "회원가입 실패: $errorMessage", Toast.LENGTH_SHORT).show()
                    Log.e("SignUp", "Error: $errorMessage")
                }
            }
    }

    private fun addUserToDatebase(name:String, email:String, uId:String) {
        val user = User(name, email, uId)
        mDbRef.child("user").child(uId).setValue(user)
    }

}
