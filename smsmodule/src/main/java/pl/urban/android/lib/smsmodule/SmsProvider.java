package pl.urban.android.lib.smsmodule;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmsProvider {
    private final SmsManager mSmsManager;
    private final ContentResolver mContentResolver;

    public SmsProvider(@NonNull final SmsManager smsManager,
                       @NonNull final ContentResolver contentResolver) {
        mSmsManager = smsManager;
        mContentResolver = contentResolver;
    }

    public List<SMSMessage> getSmsList() {
        return getSmsList(0);
    }

    public List<SMSMessage> getSmsList(final long sinceFilter) {
        try (final Cursor cursor = mContentResolver.query(Telephony.Sms.CONTENT_URI, null,
                Telephony.TextBasedSmsColumns.DATE + " >= ?",
                new String[]{Long.toString(sinceFilter)}, Telephony.Sms.DEFAULT_SORT_ORDER)) {
            if (cursor == null || cursor.getCount() == 0) return Collections.emptyList();

            final List<SMSMessage> smsMessageList = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                final SMSMessage smsMessage = mapSmsMessageFromCursor(cursor);
                smsMessageList.add(smsMessage);
            }

            return smsMessageList;
        }
    }

    public int deleteAllSms() {
        int count = 0;
        try (Cursor c = mContentResolver.query(Uri.parse("content://sms/"), null, null, null, null)) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                count += mContentResolver.delete(Uri.parse("content://sms/" + id), null, null);
            }
        } catch (Exception e) {
            Log.e(this.toString(), "Error deleting sms", e);
        }
        return count;
    }

    private SMSMessage mapSmsMessageFromCursor(final Cursor cursor) {
        final SMSMessage msg = new SMSMessage();

        int smsIdIndex = cursor.getColumnIndex(Telephony.Sms._ID);
        if (smsIdIndex > -1) {
            msg.setId(cursor.getInt(smsIdIndex));
        }
        msg.setNumber(cursor.getString(cursor.getColumnIndex(Telephony.TextBasedSmsColumns.ADDRESS)));
        msg.setBody(cursor.getString(cursor.getColumnIndex(Telephony.TextBasedSmsColumns.BODY)));
        msg.setDate(cursor.getLong(cursor.getColumnIndex(Telephony.TextBasedSmsColumns.DATE)));
        msg.setDateSent(cursor.getLong(cursor.getColumnIndex(Telephony.TextBasedSmsColumns.DATE_SENT)));
        msg.setIsReaded(cursor.getInt(cursor.getColumnIndex(Telephony.TextBasedSmsColumns.READ)) > 0);
        msg.setType(cursor.getInt(cursor.getColumnIndex(Telephony.TextBasedSmsColumns.TYPE)));

        return msg;
    }

    public void sendSms(@NonNull final SMSMessage message) {
        mSmsManager.sendMultipartTextMessage(message.getNumber(), null,
                mSmsManager.divideMessage(message.getBody()), null, null);
    }
}
