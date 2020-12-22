package com.example.awesomefish.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomefish.R
import com.example.awesomefish.di.DI
import com.example.awesomefish.ui.lists.HighscoreListAdapter
import com.example.awesomefish.utils.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.highscores.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val HIGH_SCORE_TAG = "highScore"

class HighScoreBottomSheet : RoundedBottomSheetDialogFragment() {

    private val repository = DI.provideScoreRepository()
    private lateinit var emptyText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.highscore_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyText = view.findViewById(R.id.empty_text)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                val scores = repository.getAll().sortedBy { it.scoreValue }.reversed().toMutableList()
                if (scores.isEmpty()) {
                    showEmptyState()
                } else {
                    hideEmptyState()
                    highScoreList.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = HighscoreListAdapter(scores)
                    }

                }
            }
        }
    }

    private fun showEmptyState() {
        emptyText.visibility = View.VISIBLE
    }

    private fun hideEmptyState() {
        emptyText.visibility = View.INVISIBLE
    }

    companion object {
        @Volatile
        private var highScoreDialog: HighScoreBottomSheet? = null

        private fun instance(): HighScoreBottomSheet {
            val theHighScoreDialog = highScoreDialog
            if (theHighScoreDialog != null) {
                return theHighScoreDialog
            }
            synchronized(HighScoreBottomSheet::class) {
                var possibleNullHighScoreMenu = highScoreDialog
                return if (possibleNullHighScoreMenu == null) {
                    possibleNullHighScoreMenu = HighScoreBottomSheet()
                    possibleNullHighScoreMenu
                } else {
                    possibleNullHighScoreMenu
                }
            }
        }

        fun show(fragmentManager: FragmentManager) {
            instance().show(fragmentManager, HIGH_SCORE_TAG)
        }

        fun hide(fragmentManager: FragmentManager) {
            (fragmentManager.findFragmentByTag(HIGH_SCORE_TAG) as HighScoreBottomSheet).dismiss()
        }
    }
}