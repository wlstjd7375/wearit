package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-03-03.
 */
public class Settle implements Parcelable {
    private int key;
    private String bank;
    private String account_owner;
    private String account;

    public Settle() {

    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount_owner() {
        return account_owner;
    }

    public void setAccount_owner(String account_owner) {
        this.account_owner = account_owner;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeString(bank);
        dest.writeString(account_owner);
        dest.writeString(account);
    }

    private Settle(Parcel in) {
        key = in.readInt();
        bank = in.readString();
        account_owner = in.readString();
        account = in.readString();
    }

    public static final Creator<Settle> CREATOR = new Creator<Settle>() {

        @Override
        public Settle createFromParcel(Parcel in) {
            return new Settle(in);
        }

        @Override
        public Settle[] newArray(int size) {
            return new Settle[size];
        }
    };
}