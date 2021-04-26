package com.metrobike.indiaHealthStack

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		if (!Settings.canDrawOverlays(this)) {
			val intent =
				Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
			startActivityForResult(intent, 1234)
		}


	}

	fun enableAutoStart() {
		try {
			val intent = Intent()
			val manufacturer = Build.MANUFACTURER
			if ("xiaomi".equals(manufacturer, ignoreCase = true)) {
				intent.component = ComponentName(
					"com.miui.securitycenter",
					"com.miui.permcenter.autostart.AutoStartManagementActivity"
				)
			} else if ("oppo".equals(manufacturer, ignoreCase = true)) {
				intent.component = ComponentName(
					"com.coloros.safecenter",
					"com.coloros.safecenter.permission.startup.StartupAppListActivity"
				)
			} else if ("vivo".equals(manufacturer, ignoreCase = true)) {
				intent.component = ComponentName(
					"com.vivo.permissionmanager",
					"com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
				)
			} else if ("oneplus".equals(manufacturer, ignoreCase = true)) {
				intent.component = ComponentName(
					"com.oneplus.security",
					"com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity"
				)
			}
			val list: List<ResolveInfo> = this.packageManager
				.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
			if (list.size > 0) {
				this.startActivity(intent)
			}
		} catch (e: Exception) {
			e.printStackTrace()
			//Crashlytics.logException(e)
		}
	}
}