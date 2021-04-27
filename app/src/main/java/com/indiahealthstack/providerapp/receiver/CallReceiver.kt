package com.indiahealthstack.providerapp.receiver

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Handler
import android.provider.CallLog
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.indiahealthstack.providerapp.Constants
import java.util.*


class CallReceiver : PhoneCallReceiver() {
    private val TAG = CallReceiver::class.java.simpleName
    var alreadySend = false

    override fun onIncomingCallStarted(context: Context?, number: String?, start: Date?) {
       // Toast.makeText(ctx, "Sumit Incoming Call$number", Toast.LENGTH_LONG).show()

        val telephony = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephony.listen(object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String) {
                super.onCallStateChanged(state, incomingNumber)
                incomingNumber?.let {
                    openOverlayDialogue(context, incomingNumber)
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
    }

    override fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {

    }

    private fun openOverlayDialogue(context: Context?, number: String?) {
        if (!alreadySend) {
            alreadySend = true

            val projection = arrayOf(CallLog.Calls.NUMBER)
            val cur: Cursor? = context?.contentResolver?.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                CallLog.Calls.DATE + " desc"
            )
            cur?.moveToFirst()
            val lastCallnumber: String? = cur?.getString(0)

            Log.e(TAG, "IDLE number = $lastCallnumber")

            val intent = Intent(context, IncomingCallCovidInfoDialogue::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra(Constants.PHONE_NUMBER, lastCallnumber)
            Handler().postDelayed({ context!!.startActivity(intent) }, 2000)
        }
    }
}


