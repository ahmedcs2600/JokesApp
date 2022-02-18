package com.example.lillydooassignment.components.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.lillydooassignment.R
import com.example.lillydooassignment.databinding.LayoutJokeDetailDialogBinding

class JokeDetailDialog : DialogFragment() {

    private val args: JokeDetailDialogArgs by navArgs()

    private var _binding: LayoutJokeDetailDialogBinding? = null
    private val binding: LayoutJokeDetailDialogBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutJokeDetailDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
        bindListener()
    }

    private fun setUpData() {
        with(binding) {
            jokeId.text = getString(R.string.joke_id_text,args.jokeItem.id.toString())
            jokeCategory.text = args.jokeItem.type
            jokeTitle.text = args.jokeItem.setup
            jokeDescription.text = args.jokeItem.delivery
        }
    }

    private fun bindListener() {
        with(binding) {
            closeButton.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        params?.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}