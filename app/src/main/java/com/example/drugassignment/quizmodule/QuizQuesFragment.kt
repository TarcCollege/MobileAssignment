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
            text = "Ecstasy will provide side effect except",
            answers = listOf("Slowed Reaction", "Anxiety", "Stroke", "Seizures")
        ),
        Question(
            text = "Which of the following is not drug",
            answers = listOf("Saliva", "Glue", "LSD", "Paint")
        ),
        Question(
            text = "Salvia is also refered as _________ by the Mazatec Indians",
            answers = listOf("Herb of Mary", "Herb of Happiness", "Herb of June", "Herb of Zenith")
        ),
        Question(
            text = "What is the side effect of Morphine?",
            answers = listOf("Nausea", "Depression", "Heart Failure", "Anxiety")
        ),
        Question(
            text = "Which of the following is not a type of drug?",
            answers = listOf(
                "Chocolate",
                "Opioids",
                "Stimulants",
                "All"
            )
        ),
        Question(
            text = "What drug is a central nervous system (CNS) stimulant of the methylxanthine class?",
            answers = listOf("Caffeine", "Rohypnol", "Heroin", "Aerosol sprays")
        ),
        Question(
            text = "Which one is NOT a side effect of Dexedrine?",
            answers = listOf(
                "Heart failure",
                "Unpleasant taste",
                "Dry mouth",
                "Insomnia"
            )
        ),
        Question(
            text = "Drug interactions may occur with caffeine, except.",
            answers = listOf("Lexapro ", "Duloxetine", "Rasagiline", "Quinolones(ie. ciprofloxacin)")
        ),
        Question(
            text = "Which of the following drugs does it belong to Depressants drug type?",
            answers = listOf("Adderall ", "Rohypnol", "Valium", "Xanax")
        ),
        Question(
            text = "LSD, Peyote, Psilocybin and Salvia are belongs to what drug type?",
            answers = listOf("Hallucinogens ", "Dissociatives", "Cannabis", "Inhalants ")
        )
    )



    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = 4
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
        binding.firstAnswerRadioButton.text = answers[0]
        binding.secondAnswerRadioButton.text = answers[1]
        binding.thirdAnswerRadioButton.text = answers[2]
        binding.fourthAnswerRadioButton.text = answers[3]



        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.quiz, questionIndex + 1, numQuestions)
    }


}
