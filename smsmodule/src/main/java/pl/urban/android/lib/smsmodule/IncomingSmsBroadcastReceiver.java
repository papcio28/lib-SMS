package pl.urban.android.lib.smsmodule;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

public abstract class IncomingSmsBroadcastReceiver extends BroadcastReceiver {
    private static final String SMS_PDUS = "pdus";
    private static final String SMS_FORMAT = "format";

    protected abstract void onCreate(final Context context);

    protected abstract void handleSMS(final Context context, final SMSMessage message);

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public final void onReceive(final Context context, final Intent intent) {
        onCreate(context);

        final Bundle params = intent.getExtras();

        if (params != null && params.containsKey(SMS_PDUS)) {
            Object[] pdus = (Object[]) params.get(SMS_PDUS);

            if (pdus == null) {
                return;
            }
            for (Object pdu : pdus) {
                SmsMessage smsMessage = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                        SmsMessage.createFromPdu((byte[]) pdu, params.getString(SMS_FORMAT)) :
                        SmsMessage.createFromPdu((byte[]) pdu);

                handleSMS(context, new SMSMessage(smsMessage));
            }
        }
    }
}
