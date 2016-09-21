package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-01-04.
 */
public class ProductSpecial implements Parcelable {
    private int key;
    private String image;
    private String content;
    private String date;
    private String linktype;
    private String linkurl;
    private int relation;
    private String image_path;
    private String thumb_image_path;
    private ArrayList<Product> products;
    private Content contentObject;

    public ProductSpecial() {

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLinktype() {
        return linktype;
    }

    public void setLinktype(String linkType) {
        this.linktype = linkType;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkUrl) {
        this.linkurl = linkUrl;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getThumb_image_path() { return thumb_image_path; }

    public void setThumb_image_path(String thumb_image_path) { this.thumb_image_path = thumb_image_path; }

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
        dest.writeString(content);
        dest.writeString(image);
        dest.writeString(linktype);
        dest.writeString(linkurl);
        dest.writeString(date);
        dest.writeTypedList(products);
        dest.writeParcelable(contentObject, flags);
        dest.writeString(image_path);
        dest.writeString(thumb_image_path);
        dest.writeInt(relation);
    }

    private ProductSpecial(Parcel in) {
        key = in.readInt();
        content = in.readString();
        image = in.readString();
        linktype = in.readString();
        linkurl = in.readString();
        date = in.readString();
        in.readTypedList(products = new ArrayList<Product>(), Product.CREATOR);
        contentObject = in.readParcelable(Content.class.getClassLoader());
        image_path = in.readString();
        thumb_image_path = in.readString();
        relation = in.readInt();

    }

    public static final Creator<ProductSpecial> CREATOR = new Creator<ProductSpecial>() {

        @Override
        public ProductSpecial createFromParcel(Parcel in) {
            return new ProductSpecial(in);
        }

        @Override
        public ProductSpecial[] newArray(int size) {
            return new ProductSpecial[size];
        }
    };
}
