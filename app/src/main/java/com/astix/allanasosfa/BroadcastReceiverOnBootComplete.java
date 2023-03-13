package com.astix.allanasosfa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.astix.sancussosfa.database.AppDataSource;

/**
 * Created by ALOK on 9/7/2017.
 */

public class BroadcastReceiverOnBootComplete extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            AppDataSource helperDb= AppDataSource.getInstance(context);
           String prvsStoreId=helperDb.getPreviousShownPopUpStoreId();

            if(!TextUtils.isEmpty(prvsStoreId))
            {

                helperDb.updateisRestartDoneByDSR(prvsStoreId);
               /* Intent intentTemp=new Intent(context,Splash_Activity.class);
                intentTemp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentTemp);*/
            }
        }

    }
}
