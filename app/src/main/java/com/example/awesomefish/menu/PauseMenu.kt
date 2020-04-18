package com.example.awesomefish.menu

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.awesomefish.R
import com.example.awesomefish.shared.FontManager
import kotlinx.android.synthetic.main.pause_menu.*

class PauseMenu : DialogFragment() {

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
        dialog.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    companion object {
        fun instance(): PauseMenu {
            return PauseMenu()
        }
    }
}
