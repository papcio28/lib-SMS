package pl.urban.android.lib.smsmodule;

import android.support.annotation.NonNull;
import android.telephony.SmsManager;

public final class SmsUtil {
    private final static SmsManager sSmsManager = SmsManager.getDefault();

    private SmsUtil() {
    }

    public static void sendSMS(@NonNull final IncomingSmsBroadcastReceiver.SMSMessage message) {
        sSmsManager.sendMultipartTextMessage(message.getNumber(), null, sSmsManager.divideMessage(message.getMessage()), null, null);
    }
}
