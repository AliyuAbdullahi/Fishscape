package com.example.awesomefish.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.awesomefish.R
import com.example.awesomefish.shared.FontManager
import com.example.awesomefish.ui.menu.PAUSE_MENU_TAG
import com.example.awesomefish.ui.menu.PauseMenu
import com.example.awesomefish.utils.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.pause_menu.*

class PauseMenuDialog : RoundedBottomSheetDialogFragment() {

    lateinit var resume: TextView
    lateinit var settings: TextView
    lateinit var about: TextView
    lateinit var quitgame: TextView

    private lateinit var pauseMenuItemClickedListener: PauseMenuDialogItemClickedListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.pause_menu_bottom_sheet, container, false)
        with(view) {
            resume = findViewById(R.id.resume)
            settings = findViewById(R.id.settings)
            about = findViewById(R.id.aboutUs)
            quitgame = findViewById(R.id.quit)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpClickListeners()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            pauseMenuItemClickedListener = context as PauseMenuDialogItemClickedListener
        } catch (error: Throwable) {
            Log.e("ERROR", "Context should implement PauseMenuDialogItemClickedListener")
        }
    }

    private fun setUpClickListeners() {
        resume.setOnClickListener { pauseMenuItemClickedListener.resumeClicked() }

        settings.setOnClickListener { pauseMenuItemClickedListener.settingsClicked() }

        aboutUs.setOnClickListener {
            pauseMenuItemClickedListener.aboutClicked()
        }

        quit.setOnClickListener { pauseMenuItemClickedListener.quitClicked() }
    }

    private fun setUpUI() {
        context?.let {
            val typeFace = FontManager.getTypeForFont(it, FontManager.Font.GLADIATOR_SPORT)
            resume.typeface = typeFace
            settings.typeface = typeFace
            aboutUs.typeface = typeFace
            quitgame.typeface = typeFace
        }
    }

    companion object {
        @Volatile
        private var pauseMenuDialog: PauseMenuDialog? = null

        private fun instance(): PauseMenuDialog? {
            val thePauseMenuDialog = pauseMenuDialog
            if (thePauseMenuDialog != null) {
                thePauseMenuDialog.isCancelable = false
                return thePauseMenuDialog
            }
            synchronized(PauseMenuDialog::class) {
                var possibleNullPauseMenuDialog = pauseMenuDialog
                return if (possibleNullPauseMenuDialog == null) {
                    possibleNullPauseMenuDialog = PauseMenuDialog()
                    possibleNullPauseMenuDialog.isCancelable = false
                    possibleNullPauseMenuDialog
                } else {
                    possibleNullPauseMenuDialog.isCancelable = false
                    possibleNullPauseMenuDialog
                }
            }
        }

        fun show(fragmentManager: FragmentManager) {
            instance()?.show(fragmentManager, PAUSE_MENU_TAG)
        }

        fun hide(fragmentManager: FragmentManager) {
            (fragmentManager.findFragmentByTag(PAUSE_MENU_TAG) as PauseMenuDialog).dismiss()
        }
    }


    interface PauseMenuDialogItemClickedListener {
        fun resumeClicked()
        fun aboutClicked()
        fun settingsClicked()
        fun quitClicked()
    }
}
