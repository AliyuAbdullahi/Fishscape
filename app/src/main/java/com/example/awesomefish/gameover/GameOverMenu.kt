package com.example.awesomefish.gameover

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.awesomefish.R
import com.example.awesomefish.shared.FontManager
import kotlinx.android.synthetic.main.game_over_view.*

const val GAME_OVER_MENU_TAG = "game_over"

class GameOverMenu : DialogFragment() {
    private lateinit var gameOverListener: GameOverListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            gameOverListener = context as GameOverListener
        } catch (error: Throwable) {
            Log.e("ERROR", "Context should implement GameOverListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_over_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        context?.let {
            setUpUI(it)

            newGameText.setOnClickListener {
                gameOverListener.newGameClicked()
            }

            endGameText.setOnClickListener {
                gameOverListener.endGameClicked()
            }
        }
    }

    companion object {
        @Volatile
        private var gameOverMenu: GameOverMenu? = null

        private fun instance(): GameOverMenu {
            val theGameOverMenu = gameOverMenu
            if (theGameOverMenu != null) {
                return theGameOverMenu
            }
            synchronized(GameOverMenu::class) {
                var possibleNullGameOverMenu = gameOverMenu
                return if (possibleNullGameOverMenu == null) {
                    possibleNullGameOverMenu = GameOverMenu()
                    possibleNullGameOverMenu
                } else {
                    possibleNullGameOverMenu
                }
            }
        }

        fun show(fragmentManager: FragmentManager) {
            instance().show(fragmentManager, GAME_OVER_MENU_TAG)
        }

        fun hide(fragmentManager: FragmentManager) {
            (fragmentManager.findFragmentByTag(GAME_OVER_MENU_TAG) as GameOverMenu).dismiss()
        }
    }

    private fun setUpUI(it: Context) {
        val typeFace = FontManager.getTypeForFont(it, FontManager.Font.GLADIATOR_SPORT)
        gameOverText.typeface = typeFace
        newGameText.typeface = typeFace
        endGameText.typeface = typeFace
    }

    interface GameOverListener {
        fun newGameClicked()
        fun endGameClicked()
    }
}
