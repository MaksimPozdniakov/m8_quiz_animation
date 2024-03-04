package project.gb.quizmaster.ui.main

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.navigation.fragment.findNavController
import project.gb.quizmaster.R
import project.gb.quizmaster.databinding.FragmentResultsScreenBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ResultsScreenFragment : Fragment() {
    private var _binding: FragmentResultsScreenBinding? = null
    private val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // получаем ответы и выводи в TextView
        val result: String? = arguments?.getString(ARG_PARAM1)
        if (!result.isNullOrEmpty()) {
            binding.textViewResult.text = result
        } else {
            binding.textViewResult.text = "Ничего нет!"
        }

        // Добавляем анимацию к кнопке
        ObjectAnimator.ofFloat(
            binding.startOver,
            View.ROTATION,
            0f,360f
        ).apply {
            duration = 1500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        binding.startOver.setOnClickListener {
            findNavController().navigate(R.id.action_resultsScreenFragment_to_welcomeScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultsScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}