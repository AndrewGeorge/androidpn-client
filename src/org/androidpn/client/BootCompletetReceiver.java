package org.androidpn.client;

import org.androidpn.demoapp.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class BootCompletetReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		Toast.makeText(context, "Boot completed", Toast.LENGTH_LONG).show();
		SharedPreferences spf = context.getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		if (spf.getBoolean(Constants.SETINGS_AUTO_START, true)) {
			   ServiceManager serviceManager = new ServiceManager(context);
		        serviceManager.setNotificationIcon(R.drawable.notification);
		        serviceManager.startService();
		}
		
		
	}

}
