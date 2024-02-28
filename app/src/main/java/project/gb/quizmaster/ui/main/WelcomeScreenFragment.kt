package project.gb.quizmaster.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import project.gb.quizmaster.R
import project.gb.quizmaster.databinding.FragmentWelcomeScreenBinding

class WelcomeScreenFragment : Fragment() {
    private var _binding: FragmentWelcomeScreenBinding? = null
    private val binding get()  = _binding!!
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Добавляем обратный вызов в обработчик нажатия кнопки "Back"
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)


        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeScreenFragment_to_surveyScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Перенаправляем на главный экран при нажатии кнопки "Back"
            navigateToMainFragment()
        }
    }

    private fun navigateToMainFragment() {
        // Переходим на главный экран без добавления текущего фрагмента в стек навигации
        findNavController().popBackStack(R.id.mainFragment, false)
    }

}