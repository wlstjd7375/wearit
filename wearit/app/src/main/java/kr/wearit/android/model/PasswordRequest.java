package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IISLab on 2016-01-19.
 */
public class PasswordRequest implements Parcelable {


    private String userid;
    private String name;
    private String userphone;
    private String code;
    private String password;

    public PasswordRequest(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(userid);
        dest.writeString(userphone);
        dest.writeString(code);
        dest.writeString(password);
    }


    private PasswordRequest(Parcel in) {

        name = in.readString();
        userid = in.readString();
        userphone = in.readString();
        code = in.readString();
        password = in.readString();

    }

    public static final Creator<PasswordRequest> CREATOR = new Creator<PasswordRequest>() {

        @Override
        public PasswordRequest createFromParcel(Parcel in) {
            return new PasswordRequest(in);
        }

        @Override
        public PasswordRequest[] newArray(int size) {
            return new PasswordRequest[size];
        }
    };
}
