package pl.urban.android.lib.smsmodule;

import android.content.Context;
import android.support.annotation.NonNull;

public final class SmsUtil {
    private SmsUtil() {
    }

    public static void sendSMS(@NonNull Context context, @NonNull IncomingSmsBroadcastReceiver.SMSMessage message) {
        // TODO: Wyslanie SMSa (multipart)
    }
}
