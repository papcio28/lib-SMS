package pl.urban.android.lib.smsmodule;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Objects;

public abstract class IncomingSmsBroadcastReceiver extends BroadcastReceiver {
    private static final String SMS_PDUS = "pdus";
    private static final String SMS_FORMAT = "format";

    protected abstract void onCreate();

    protected abstract void handleSMS(final SMSMessage message);

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public final void onReceive(final Context context, final Intent intent) {
        onCreate();

        final Bundle params = intent.getExtras();

        if (params != null && params.containsKey(SMS_PDUS)) {
            Object[] pdus = (Object[]) params.get(SMS_PDUS);

            Objects.requireNonNull(pdus);
            for (Object pdu : pdus) {
                SmsMessage smsMessage = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                        SmsMessage.createFromPdu((byte[]) pdu, params.getString(SMS_FORMAT)) :
                        SmsMessage.createFromPdu((byte[]) pdu);

                handleSMS(new SMSMessage(smsMessage));
            }
        }
    }
}
