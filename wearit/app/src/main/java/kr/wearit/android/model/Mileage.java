package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cho on 2016-01-02.
 */
public class Mileage implements Parcelable {


    private String id;
    private String name;
    private Integer key;
    private Integer user;
    private Integer mileage;
    private String update_mileage;
    private String update_mileage_date;
    private Integer total_mileage_sub;
    private Integer total_mileage_add;
    private String description;
    private String add;
    private String sub;
    private String date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getUpdate_mileage() {
        return update_mileage;
    }

    public void setUpdate_mileage(String update_mileage) {
        this.update_mileage = update_mileage;
    }

    public String getUpdate_mileage_date() {
        return update_mileage_date;
    }

    public void setUpdate_mileage_date(String update_mileage_date) {
        this.update_mileage_date = update_mileage_date;
    }

    public Integer getTotal_mileage_sub() {
        return total_mileage_sub;
    }

    public void setTotal_mileage_sub(Integer total_mileage_sub) {
        this.total_mileage_sub = total_mileage_sub;
    }

    public Integer getTotal_mileage_add() {
        return total_mileage_add;
    }

    public void setTotal_mileage_add(Integer total_mileage_add) {
        this.total_mileage_add = total_mileage_add;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Mileage() {

    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(key);
        dest.writeInt(user);
        dest.writeInt(mileage);
        dest.writeString(update_mileage);
        dest.writeString(update_mileage_date);
        dest.writeInt(total_mileage_sub);
        dest.writeInt(total_mileage_add);
        dest.writeString(description);
        dest.writeString(add);
        dest.writeString(sub);
        dest.writeString(date);


    }


    private Mileage(Parcel in) {

        id = in.readString();
        name = in.readString();
        key = in.readInt();
        user = in.readInt();
        mileage = in.readInt();
        update_mileage = in.readString();
        update_mileage_date = in.readString();
        total_mileage_sub = in.readInt();
        total_mileage_add = in.readInt();
        description = in.readString();
        add = in.readString();
        sub = in.readString();
        date = in.readString();

    }

    public static final Creator<Mileage> CREATOR = new Creator<Mileage>() {

        @Override
        public Mileage createFromParcel(Parcel in) {
            return new Mileage(in);
        }

        @Override
        public Mileage[] newArray(int size) {
            return new Mileage[size];
        }
    };
}
