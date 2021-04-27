package com.indiahealthstack.providerapp.receiver

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.telephony.SmsManager
import android.util.TypedValue
import android.view.Window
import android.view.WindowManager
import android.widget.RadioGroup
import android.widget.Toast
import com.indiahealthstack.providerapp.Constants
import com.indiahealthstack.providerapp.R
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.dialogue_incoming_call.*

class IncomingCallCovidInfoDialogue : Activity() {
    var phoneNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setFinishOnTouchOutside(false)
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialogue_incoming_call)
            initializeContent()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    private fun initializeContent() {
        val display =
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val width = display.width
        val height = display.height

        val  params: WindowManager.LayoutParams  = window.attributes
        params.height = dpToPx(300.0f, this)
        params.width = width
        params.y = height/2

        this.window.attributes = params

        intent.extras?.getString(Constants.PHONE_NUMBER)?.let {
            phoneNumber = it
            subtitle.text = "Send availability to $it"
        }

        close_button.setOnClickListener {
            this.finish()
        }

        send_sms.setOnClickListener {
            sendSms()
            this.finish()
        }

        edit_status.setOnClickListener {
            Toast.makeText(this, "Opening app", Toast.LENGTH_LONG).show()
        }

        initAutoReplyView()
    }

    private fun initAutoReplyView() {
        if (Prefs.getBoolean(Constants.ENABLE_AUTO_REPLY, false)) {
            auto_reply_toggle.check(auto_reply_toggle.getChildAt(0).id)
            Toast.makeText(this, "Auto Reply is enabled. Sending availability to $phoneNumber", Toast.LENGTH_LONG).show()
            Handler().postDelayed({ sendSms(); this.finish() }, 2000)
        } else {
            auto_reply_toggle.check(auto_reply_toggle.getChildAt(1).id)
        }
        auto_reply_toggle.setOnCheckedChangeListener(changeListener)
    }

    private fun sendSms() {
        val sm: SmsManager = SmsManager.getDefault()
        val message = "No. of Beds (with Oxygen) - 15\n" +
                "No. of beds (No Oxygen) - 12\n" +
                "\n" +
                "for more information, click on http.www.indiahealthstack.com/bedsavailability"
        sm.sendTextMessage(phoneNumber, null, message, null, null)

        Toast.makeText(this, "Sent availability to $phoneNumber", Toast.LENGTH_LONG).show()
    }

    private val changeListener =
        RadioGroup.OnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
            when (checkedId) {
                R.id.enable -> {
                    Prefs.putBoolean(Constants.ENABLE_AUTO_REPLY, true)
                    Toast.makeText(this, "Enabled Auto Reply", Toast.LENGTH_LONG).show()
                }
                R.id.disable -> {
                    Prefs.putBoolean(Constants.ENABLE_AUTO_REPLY, false)
                    Toast.makeText(this, "Disabled Auto Reply", Toast.LENGTH_LONG).show()
                }
            }
        }
}