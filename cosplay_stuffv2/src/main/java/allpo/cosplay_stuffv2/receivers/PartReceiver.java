package allpo.cosplay_stuffv2.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import allpo.cosplay_stuffv2.models.CosplayPart;

/**
 * Created by Allpo on 19/07/2015.
 */
public class PartReceiver extends BroadcastReceiver {
    private static final String ACTION_CREATE_PART = "PartReceiver.ACTION_CREATE_PART";

    private static final String ARG_PART_ID = "PartReceiver.ARG_PART_ID";

    private static final IntentFilter INTENT_FILTER = new IntentFilter();

    static {
        INTENT_FILTER.addAction(ACTION_CREATE_PART);
    }

    private Context mContext;

    public PartReceiver(Context context) {
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        Bundle bundle;

        switch (action) {
            case ACTION_CREATE_PART :
                bundle = intent.getExtras();
                long partId = bundle.getLong(ARG_PART_ID);
                onCreatePart(partId);
        }
    }

    public static void sendCreatePart(Context context, CosplayPart part) {
        final Intent intent = new Intent(ACTION_CREATE_PART);
        intent.putExtra(ARG_PART_ID, part.getId());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void onCreatePart(long id) {

    }

    public static void register(PartReceiver receiver) {
        LocalBroadcastManager.getInstance(receiver.mContext).registerReceiver(receiver, INTENT_FILTER);
    }


    public static void unregister(PartReceiver receiver) {
        LocalBroadcastManager.getInstance(receiver.mContext).unregisterReceiver(receiver);
    }
}
