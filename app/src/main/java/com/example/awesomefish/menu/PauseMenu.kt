package com.example.awesomefish.menu

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.awesomefish.R
import com.example.awesomefish.shared.FontManager
import kotlinx.android.synthetic.main.pause_menu.*

const val PAUSE_MENU_TAG = "pause_menu"

class PauseMenu : DialogFragment() {

    private lateinit var pauseMenuItemClickedListener: PauseMenuItemClickedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            pauseMenuItemClickedListener = context as PauseMenuItemClickedListener
        } catch (error: Throwable) {
            Log.e("ERROR", "Context should implement PauseMenuItemClickedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pause_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        resume.setOnClickListener { pauseMenuItemClickedListener.resumeClicked() }

        settings.setOnClickListener { pauseMenuItemClickedListener.settingsClicked() }

        aboutUs.setOnClickListener { pauseMenuItemClickedListener.aboutClicked() }

        quit.setOnClickListener { pauseMenuItemClickedListener.quitClicked() }
    }

    private fun setUpUI() {
        context?.let {
            val typeFace = FontManager.getTypeForFont(it, FontManager.Font.GLADIATOR_SPORT)
            resume.typeface = typeFace
            settings.typeface = typeFace
            aboutUs.typeface = typeFace
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    companion object {
        @Volatile
        private var pauseMenu: PauseMenu? = null

        private fun instance(): PauseMenu {
            val thePauseMenu = pauseMenu
            if (thePauseMenu != null) {
                return thePauseMenu
            }
            synchronized(PauseMenu::class) {
                var possibleNullPauseMenu = pauseMenu
                return if (possibleNullPauseMenu == null) {
                    possibleNullPauseMenu = PauseMenu()
                    possibleNullPauseMenu
                } else {
                    possibleNullPauseMenu
                }
            }
        }

        fun show(fragmentManager: FragmentManager) {
            instance().show(fragmentManager, PAUSE_MENU_TAG)
        }

        fun hide(fragmentManager: FragmentManager) {
            (fragmentManager.findFragmentByTag(PAUSE_MENU_TAG) as PauseMenu).dismiss()
        }
    }

    interface PauseMenuItemClickedListener {
        fun resumeClicked()
        fun aboutClicked()
        fun settingsClicked()
        fun quitClicked()
    }
}
