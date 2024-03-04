package project.gb.quizmaster.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.gb.quizmaster.R
import project.gb.quizmaster.databinding.FragmentSurveyScreenBinding
import project.gb.quizmaster.model.adapter.QuestionsAdapter
import project.gb.quizmaster.quiz.QuizStorage

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SurveyScreenFragment : Fragment() {
    private var _binding: FragmentSurveyScreenBinding? = null
    private val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null
    private var choiceUser = mutableListOf<String>()

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
        _binding = FragmentSurveyScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locale = QuizStorage.Locale.Ru
        val quiz = QuizStorage.getQuiz(locale)
        val questions = quiz.questions

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = QuestionsAdapter(questions)

        // Добавляем анимацию к кнопкам
        val slideFromLeft = AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_from_left)
        val slideFromRight = AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_from_right)
        binding.backButton.startAnimation(slideFromLeft)
        binding.sendButton.startAnimation(slideFromRight)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_surveyScreenFragment_to_welcomeScreenFragment)
        }

        binding.sendButton.setOnClickListener {
            if (checkAnswers()) {
                // создаем Bundle и передаем ответы
                val bundle = Bundle().apply {
                    val stringResult = "Ваши ответы: " + choiceUser.toString()
                        .replace("[", "")
                        .replace("]", "")
                    putString("param1", stringResult)
                }

                findNavController().navigate(R.id.action_surveyScreenFragment_to_resultsScreenFragment, bundle)
            }
        }


    }

    /**
     * Метод проверяет на все ли вопросы ответил пользователь
     */
    private fun checkAnswers() : Boolean {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.let { rv ->
            for (i in 0 until rv.childCount) {
                val itemView = rv.getChildAt(i)
                if (itemView is ViewGroup) {
                    val radioGroup = itemView.findViewById<RadioGroup>(R.id.answersRadioGroup)
                    val checkedRadioButtonId = radioGroup.checkedRadioButtonId
                    if (checkedRadioButtonId == -1) {
                        Toast.makeText(requireContext(), "Ответьте на все вопросы!",
                            Toast.LENGTH_SHORT).show()
                        return false
                    } else {
                        // Получаем выбранный ответ и добавляем его в список с ответами
                        val radioButton = itemView.findViewById<RadioButton>(checkedRadioButtonId)
                        val selectedAnswer = radioButton.text.toString()
                        choiceUser.add(selectedAnswer)
                    }
                }
            }
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SurveyScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}