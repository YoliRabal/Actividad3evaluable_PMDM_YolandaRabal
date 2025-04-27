package com.example.actividad3evaluable_pmdm_yolandarabal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.actividad3evaluable_pmdm_yolandarabal.R
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()

        val editTextEmail = view.findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = view.findViewById<EditText>(R.id.editTextPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val btnGoToLogin = view.findViewById<Button>(R.id.btnGoToLogin)

        btnRegister.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 🔥 Crear NavOptions para limpiar el backstack
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.registerFragment, true)
                            .build()

                        findNavController().navigate(R.id.homeFragment, null, navOptions)
                    } else {
                        Toast.makeText(requireContext(), "Error al registrarse: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        btnGoToLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        return view
    }
}
