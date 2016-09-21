package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-03-03.
 */
public class Advertise implements Parcelable {
    private int key;
    private String image;
    private String content;
    private int status;
    private String start;
    private String end;
    private String date;
    private String linkType;
    private String linkUrl;
    private int relation;
    private String imagePath;
    private ArrayList<Product> products;
    private Content contentObject;

    public Advertise() {

    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Content getContentObject() { return this.contentObject; }

    public void setContentObject(Content contentObject) { this.contentObject = contentObject; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeString(image);
        dest.writeString(content);
        dest.writeInt(status);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(date);
        dest.writeString(linkType);
        dest.writeString(linkUrl);
        dest.writeInt(relation);
        dest.writeString(imagePath);
        dest.writeTypedList(products);
        dest.writeParcelable(contentObject, flags);
    }

    private Advertise(Parcel in) {
        key = in.readInt();
        image = in.readString();
        content = in.readString();
        status = in.readInt();
        start = in.readString();
        end = in.readString();
        date = in.readString();
        linkType = in.readString();
        linkUrl = in.readString();
        relation = in.readInt();
        imagePath = in.readString();

        in.readTypedList(products = new ArrayList<Product>(), Product.CREATOR);
        contentObject = in.readParcelable(Content.class.getClassLoader());
    }

    public static final Creator<Advertise> CREATOR = new Creator<Advertise>() {

        @Override
        public Advertise createFromParcel(Parcel in) {
            return new Advertise(in);
        }

        @Override
        public Advertise[] newArray(int size) {
            return new Advertise[size];
        }
    };
}