package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by bagjehyeon on 2015. 12. 28..
 */
public class Home implements Parcelable {
    private int key;
    private String image;
    private String title;
    private String comment1;
    private String comment2;
    private String order;
    private String start;
    private String end;
    private String date;

    private String linktype;
    private String linkurl;


    private int relation;

    private Content contentObject;

    private String image_path;
    private String thumb_image_path;

    private ArrayList<Product> products;

    public Home(){

    }

    public void setKey(int key){
        this.key = key;
    }

    public int getKey(){
        return this.key;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setComment1(String comment1){
        this.comment1 = comment1;
    }

    public String getComment1(){
        return this.comment1;
    }

    public void setComment2(String comment2){
        this.comment2 = comment2;
    }

    public String getComment2(){
        return this.comment2;
    }

    public void setLinktype(String linktype){
        this.linktype = linktype;
    }

    public String getLinktype(){
        return this.linktype;
    }

    public void setLinkurl(String linkurl){
        this.linkurl = linkurl;
    }

    public String getLinkurl(){
        return this.linkurl;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }
    public void setImage_path(String image_path){
        this.image_path = image_path;
    }

    public String getImage_path(){
        return this.image_path;
    }

    public void setThumb_image_path(String thumb_image_path){
        this.thumb_image_path = thumb_image_path;
    }

    public String getThumb_image_path(){
        return this.thumb_image_path;
    }


    public Content getContentObject() {
        return contentObject;
    }

    public void setContentObject(Content contentObject) {
        this.contentObject = contentObject;
    }


    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeString(title);
        dest.writeString(comment1);
        dest.writeString(comment2);
        dest.writeString(linktype);
        dest.writeString(linkurl);
        dest.writeInt(relation);

        dest.writeString(image_path);
        dest.writeString(thumb_image_path);

        dest.writeParcelable(contentObject, flags);
        dest.writeTypedList(products);
    }

    private Home(Parcel in) {
        key = in.readInt();
        title = in.readString();
        comment1 = in.readString();
        comment2 = in.readString();
        linktype = in.readString();
        linkurl = in.readString();
        relation = in.readInt();

        image_path = in.readString();
        thumb_image_path = in.readString();

        contentObject = in.readParcelable(Content.class.getClassLoader());
        in.readTypedList(products = new ArrayList<Product>(), Product.CREATOR);
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {

        @Override
        public Home createFromParcel(Parcel in) {
            return new Home(in);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };
}
