package pl.urban.android.lib.smsmodule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class IncomingSmsBroadcastReceiver extends BroadcastReceiver {

    protected abstract void onCreate();

    protected abstract void handleSMS(final SMSMessage message);

    @Override
    public final void onReceive(Context context, Intent intent) {
        onCreate();

        // TODO: Odczytanie SMSa, obranie w obiekt SMSMessage i przekazanie do handleSMS();
    }

    public static class SMSMessage {
        private final String mNumber;
        private final String mMessage;

        public SMSMessage(String mNumber, String mMessage) {
            this.mNumber = mNumber;
            this.mMessage = mMessage;
        }

        public String getNumber() {
            return mNumber;
        }

        public String getMessage() {
            return mMessage;
        }
    }
}
