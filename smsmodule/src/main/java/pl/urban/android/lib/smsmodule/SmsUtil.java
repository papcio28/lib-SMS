package pl.urban.android.lib.smsmodule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;

public final class SmsUtil {
    private SmsUtil() {
    }

    public static void sendSMS(@NonNull final Context context, @NonNull final IncomingSmsBroadcastReceiver.SMSMessage message) {
        final SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendMultipartTextMessage(message.getNumber(), null, smsManager.divideMessage(message.getMessage()), null, null);
    }
}
