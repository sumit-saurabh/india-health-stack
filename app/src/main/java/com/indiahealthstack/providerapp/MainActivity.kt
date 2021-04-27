package com.indiahealthstack.providerapp

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import java.util.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		if (!Settings.canDrawOverlays(this)) {
			Toast.makeText(this, "Please allow display over the apps", Toast.LENGTH_SHORT).show()
			val intent =
				Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
			startActivityForResult(intent, 1234)
		}

		grant_permission.setOnClickListener {
			showCamera.launch()
		}

		if ("xiaomi" == Build.MANUFACTURER.toLowerCase(Locale.ROOT)) {
			grant_permission_oem.visibility = View.VISIBLE
			grant_permission_oem.setOnClickListener {
				val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
				intent.setClassName("com.miui.securitycenter",
					"com.miui.permcenter.permissions.PermissionsEditorActivity")
				intent.putExtra("extra_pkgname", getPackageName())
				startActivity(intent)
			}
		}
		else {
			grant_permission_oem.visibility = View.GONE
		}
	}


	val showCamera = constructPermissionsRequest(
		Manifest.permission.READ_PHONE_STATE,
		Manifest.permission.SEND_SMS,
		Manifest.permission.READ_CALL_LOG,
		onShowRationale = ::onCameraShowRationale,
		onPermissionDenied = ::onCameraDenied,
		onNeverAskAgain = ::onCameraNeverAskAgain
	) {
		// do something here
		enableAutoStart()
	}

	fun onCameraShowRationale(request: PermissionRequest) {
		showRationaleDialog(R.string.generic_permission_message, request)
	}

	fun onCameraDenied() {
		Toast.makeText(this, "We need all permissions for the call assistant", Toast.LENGTH_SHORT).show()
	}

	fun onCameraNeverAskAgain() {
		Toast.makeText(
			this,
			"Please open the app settings and grant the permission",
			Toast.LENGTH_SHORT
		).show()
		startActivity(
			Intent(
				Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.fromParts("package", packageName, null)
			)
		)
	}

	private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
		AlertDialog.Builder(this)
			.setPositiveButton(R.string.allow) { _, _ -> request.proceed() }
			.setNegativeButton(R.string.deny) { _, _ -> request.cancel() }
			.setCancelable(false)
			.setMessage(messageResId)
			.show()
	}

	private fun enableAutoStart() {
		val manufacturer = Build.MANUFACTURER
		if ("xiaomi".equals(manufacturer, ignoreCase = true) || "oppo".equals(manufacturer, ignoreCase = true) ||
			"vivo".equals(manufacturer, ignoreCase = true) || "oneplus".equals(manufacturer, ignoreCase = true)) {
			Toast.makeText(this, "Please enable Auto Start", Toast.LENGTH_SHORT).show()
			startActivity(
				Intent(
					Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
					Uri.fromParts("package", packageName, null)
				)
			)
		}
//		try {
//			val intent = Intent()
//			val manufacturer = Build.MANUFACTURER
//			if ("xiaomi".equals(manufacturer, ignoreCase = true)) {
//				intent.component = ComponentName(
//					"com.miui.securitycenter",
//					"com.miui.permcenter.autostart.AutoStartManagementActivity"
//				)
//			} else if ("oppo".equals(manufacturer, ignoreCase = true)) {
//				intent.component = ComponentName(
//					"com.coloros.safecenter",
//					"com.coloros.safecenter.permission.startup.StartupAppListActivity"
//				)
//			} else if ("vivo".equals(manufacturer, ignoreCase = true)) {
//				intent.component = ComponentName(
//					"com.vivo.permissionmanager",
//					"com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
//				)
//			} else if ("oneplus".equals(manufacturer, ignoreCase = true)) {
//				intent.component = ComponentName(
//					"com.oneplus.security",
//					"com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity"
//				)
//			}
//			val list: List<ResolveInfo> = this.packageManager
//				.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//			if (list.size > 0) {
//				this.startActivity(intent)
//			}
//		} catch (e: Exception) {
//			e.printStackTrace()
//			//Crashlytics.logException(e)
//		}
	}

//	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//		// NOTE: delegate the permission handling to generated method
//		onRequestPermissionsResult(requestCode, grantResults)
//	}

}