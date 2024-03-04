package project.gb.quizmaster.model.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.gb.quizmaster.R
import project.gb.quizmaster.quiz.Question


class QuestionsAdapter(private val questions: List<Question>) :
    RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_question, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {

        val question = questions[position]
        holder.questionTextView.text = question.question

        // Применяем анимацию к каждому элементу списка
        val animation: Animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation)
        holder.itemView.startAnimation(animation)

        // Очищаем группу RadioButton перед добавлением новых кнопок
        holder.answersRadioGroup.removeAllViews()

        // Добавляем RadioButton для каждого ответа
        question.answers.forEachIndexed { index, answer ->
            val radioButton = RadioButton(holder.itemView.context)
            radioButton.text = answer
            radioButton.id = index
            holder.answersRadioGroup.addView(radioButton)
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val answersRadioGroup: RadioGroup = itemView.findViewById(R.id.answersRadioGroup)
    }
}