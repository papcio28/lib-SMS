package pl.urban.android.lib.smsmodule;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

public abstract class IncomingSmsBroadcastReceiver extends BroadcastReceiver {
    private static final String _SMS_PDUS = "pdus";
    private static final String _SMS_FORMAT = "format";

    protected abstract void onCreate();

    protected abstract void handleSMS(final SmsMessage message);

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public final void onReceive(Context context, Intent intent) {
        onCreate();

        Bundle params = intent.getExtras();

        if (params != null && params.containsKey(_SMS_PDUS)) {
            Object[] pdus = (Object[]) params.get(_SMS_PDUS);

            assert pdus != null;
            for (Object pdu : pdus) {
                SmsMessage smsMessage = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                        SmsMessage.createFromPdu((byte[]) pdu, params.getString(_SMS_FORMAT)) : SmsMessage.createFromPdu((byte[]) pdu);

                handleSMS(smsMessage);
            }
        }
    }
}
