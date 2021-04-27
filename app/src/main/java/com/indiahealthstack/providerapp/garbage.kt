
//private fun showCustomPopupMenu() {
//    windowManager = getSystemService(WINDOW_SERVICE) as WindowManager?
//    // LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    // View view = layoutInflater.inflate(R.layout.dummy_layout, null);
//    val valetModeWindow = View.inflate(this, R.layout.dummy_layout, null) as ViewGroup
//    val LAYOUT_FLAG: Int
//    LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//    } else {
//        WindowManager.LayoutParams.TYPE_PHONE
//    }
//    val params = WindowManager.LayoutParams(
//        WindowManager.LayoutParams.WRAP_CONTENT,
//        WindowManager.LayoutParams.WRAP_CONTENT,
//        LAYOUT_FLAG,
//        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//        PixelFormat.TRANSLUCENT
//    )
//    params.gravity = Gravity.CENTER or Gravity.CENTER
//    params.x = 0
//    params.y = 0
//    windowManager.addView(valetModeWindow, params)
//}

//                if (TelephonyManager.CALL_STATE_RINGING == state) {
//                    // phone ringing
//                    Log.i(TAG, "RINGING, number: $incomingNumber")
//                }
//
//                if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
//                    // active
//                    Log.i(TAG, "OFFHOOK")
//                    isPhoneCalling = true
//                }
//
//                if (TelephonyManager.CALL_STATE_IDLE == state) {
//                    // run when class initial and phone call ended, need detect flag
//                    // from CALL_STATE_OFFHOOK
//                    Log.i(TAG, "IDLE number")
//                    if (isPhoneCalling) {
//                        val handler = Handler()
//
//                        //Put in delay because call log is not updated immediately when state changed
//                        // The dialler takes a little bit of time to write to it 500ms seems to be enough
//                        handler.postDelayed({ // get start of cursor
//                            Log.i("CallLogDetailsActivity", "Getting Log activity...")
//                            val projection = arrayOf(CallLog.Calls.NUMBER)
//                            val cur: Cursor? = context?.contentResolver?.query(
//                                CallLog.Calls.CONTENT_URI,
//                                projection,
//                                null,
//                                null,
//                                CallLog.Calls.DATE + " desc"
//                            )
//                            cur?.moveToFirst()
//                            val lastCallnumber: String? = cur?.getString(0)
//
//                            Log.e(TAG, "IDLE number = $lastCallnumber")
//
//                        }, 500)
//                        isPhoneCalling = false
//                    }
//                }
//openOverlayDialogue(context, incomingNumber)





//        val LAYOUT_FLAG: Int
//        LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        } else {
//            WindowManager.LayoutParams.TYPE_PHONE
//        }
//
//        val params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            LAYOUT_FLAG,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            PixelFormat.TRANSLUCENT
//        )
//
//        val wm = ctx?.getSystemService(WINDOW_SERVICE) as WindowManager?
//        val inflater = ctx?.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater?
//        val myView: View? = inflater?.inflate(R.layout.dialogue_incoming_call, null)
//        myView?.setOnTouchListener { v, event ->
//            //Log.d(TAG, "touch me")
//            true
//        }
//
//        // Add layout to window manager
//
//        // Add layout to window manager
//        wm!!.addView(myView, params)