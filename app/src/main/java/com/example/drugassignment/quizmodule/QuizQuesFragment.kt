package com.example.drugassignment.quizmodule


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentQuizQuesBinding

/**
 * A simple [Fragment] subclass.
 */
class QuizQuesFragment : Fragment() {

    data class Question(
        val text: String,
        val answers: List<String>)

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (Or better yet, don't define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "Who is Kuek Yong Boon?",
            answers = listOf("All of these", "Leng Zai", "Fei Fei", "Others")
        ),
        Question(
            text = "What is the base class for layouts?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")
        ),
        Question(
            text = "What layout do you use for complex screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")
        ),
        Question(
            text = "What do you use to push structured data into a layout?",
            answers = listOf("Data binding", "Data pushing", "Set text", "An OnClick method")
        ),
        Question(
            text = "What method do you use to inflate layouts in fragments?",
            answers = listOf(
                "onCreateView()",
                "onActivityCreated()",
                "onCreateLayout()",
                "onInflateLayout()"
            )
        ),
        Question(
            text = "What's the build system for Android?",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")
        ),
        Question(
            text = "Which class do you use to create a vector drawable?",
            answers = listOf(
                "VectorDrawable",
                "AndroidVectorDrawable",
                "DrawableVector",
                "AndroidVector"
            )
        ),
        Question(
            text = "Which one of these is an Android navigation component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")
        ),
        Question(
            text = "Which XML element lets you register an activity with the launcher activity?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")
        ),
        Question(
            text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")
        )
    )



    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = 1
    private lateinit var binding: FragmentQuizQuesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate<FragmentQuizQuesBinding>(
            inflater, R.layout.fragment_quiz_ques, container, false)

        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout
//        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // We've won!  Navigate to the gameWonFragment.
                        view.findNavController()
                            .navigate(
                                R.id.action_quizQuesFragment_to_gameWonFragment)
                    }
                } else {
                    // Game over! A wrong answer sends us to the gameOverFragment.
                    view.findNavController()
                        .navigate(R.id.action_quizQuesFragment_to_gameOverFragment22)
                }
            }
        }
        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()

        binding.questionText.text = currentQuestion.text
        binding.firstAnswerRadioButton.text = currentQuestion.answers[0]
        binding.secondAnswerRadioButton.text = currentQuestion.answers[1]
        binding.thirdAnswerRadioButton.text = currentQuestion.answers[2]
        binding.fourthAnswerRadioButton.text = currentQuestion.answers[3]

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.quiz, questionIndex + 1, numQuestions)
    }


}
