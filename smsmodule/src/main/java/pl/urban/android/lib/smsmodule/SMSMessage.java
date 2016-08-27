package pl.urban.android.lib.smsmodule;

import android.support.annotation.StringDef;
import android.telephony.SmsMessage;

import java.text.DateFormat;
import java.util.Date;

public class SMSMessage {
    public static final String TYPE_ALL = "all";
    public static final String TYPE_INBOX = "inbox";
    public static final String TYPE_SENT = "sent";
    public static final String TYPE_DRAFT = "draft";
    public static final String TYPE_OUTBOX = "outbox";
    public static final String TYPE_FAILED = "failed";
    public static final String TYPE_QUEUED = "queued";
    public static final String TYPE_UNKNOWN = "unknown";

    @StringDef({TYPE_ALL, TYPE_INBOX, TYPE_SENT, TYPE_DRAFT,
            TYPE_OUTBOX, TYPE_FAILED, TYPE_QUEUED, TYPE_UNKNOWN})
    public @interface MessageType {
    }

    @MessageType
    private static String getMessageType(final int typeID) {
        switch (typeID) {
            case 0:
                return TYPE_ALL;
            case 1:
                return TYPE_INBOX;
            case 2:
                return TYPE_SENT;
            case 3:
                return TYPE_DRAFT;
            case 4:
                return TYPE_OUTBOX;
            case 5:
                return TYPE_FAILED;
            case 6:
                return TYPE_QUEUED;
            default:
                return TYPE_UNKNOWN;
        }
    }

    private Integer mId;
    private String mBody;
    private String mNumber;
    private String mDate;
    private String mDateSent;
    private boolean mIsReaded;
    private String mType;

    public SMSMessage() {
    }

    public SMSMessage(final String body, final String number) {
        mBody = body;
        mNumber = number;
    }

    public SMSMessage(final SmsMessage smsMessage) {
        this(smsMessage.getMessageBody(), smsMessage.getOriginatingAddress());
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = DateFormat.getDateTimeInstance().format(new Date(date));
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDateSent() {
        return mDateSent;
    }

    public void setDateSent(long dateSent) {
        mDateSent = DateFormat.getDateTimeInstance().format(new Date(dateSent));
    }

    public void setDateSent(String dateSent) {
        mDateSent = dateSent;
    }

    public boolean isReaded() {
        return mIsReaded;
    }

    public void setIsReaded(boolean isReaded) {
        mIsReaded = isReaded;
    }

    @MessageType
    public String getType() {
        return mType;
    }

    public void setType(int typeId) {
        mType = getMessageType(typeId);
    }

    public void setType(@MessageType String type) {
        mType = type;
    }
}
