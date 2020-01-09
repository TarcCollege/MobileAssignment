package com.example.drugassignment.Quiz_Module


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentQuizMainBinding

/**
 * A simple [Fragment] subclass.
 */
class QuizMainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentQuizMainBinding>(inflater,
            R.layout.fragment_quiz_main, container, false)

        binding.playButton.setOnClickListener { view : View ->
            view.findNavController()
                .navigate(QuizMainFragmentDirections.actionQuizMainToQuizQuesFragment())
        }

        setHasOptionsMenu(true)

        return binding.root
    }



}
