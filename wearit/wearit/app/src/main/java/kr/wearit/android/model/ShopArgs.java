package kr.wearit.android.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import kr.wearit.android.view.Nav;

public abstract class ShopArgs implements Parcelable {

    public enum Mode {
        ALL, MAIN_SHOP_NEW, MAIN_SHOP_BY_PLACE, MAIN_SHOP_BY_CONCEPT, MAIN_SHOP_BY_BRAND, MAIN_PRODUCT, PROFILE, SEARCH, SHOPLIST,
    }

    public enum Click {
        SHOP
    }

    public enum Indicator {
        NONE, MAP, NEXT
    }

    //

    public static final All ALL = new All();
    public static final ShopArgs MAIN_SHOP_NEW = new MainShopNew();
    public static final ShopArgs MAIN_PRODUCT = new MainProduct();
    public static final Profile PROFILE = new Profile();

    //

    public static MainShopByPlace create(ShopPlace place) {
        return new MainShopByPlace(place);
    }

    public static MainShopByConcept create(ShopConcept concept) {
        return new MainShopByConcept(concept);
    }

    public static MainShopByBrand create(Brand brand) {
        return new MainShopByBrand(brand);
    }

    public static ShopList create(List<Shop> list) {
        return new ShopList(list);
    }

    public static Search create(String query, List<Shop> list) {
        return new Search(query, list);
    }

    //

    public final Mode mode;
    public final Click click;
    public final Nav nav;
    @Nullable
    public Gender gender;
    /**
     * main / product / shop - stylemap row
     */
    public final boolean root;
    public final Indicator indicator;
    public final boolean stylemap;

    //

    protected ShopArgs(Mode mode, Click click, Nav nav, Gender gender, boolean root) {
        this(mode, click, nav, gender, root, Indicator.MAP, false);
    }

    protected ShopArgs(Mode mode, Click click, Nav nav, Gender gender, boolean root, Indicator indicator, boolean stylemap) {
        this.mode = mode;
        this.click = click;
        this.nav = nav;
        this.gender = gender;
        this.root = root;
        this.indicator = indicator;
        this.stylemap = stylemap;
    }

    //

    @SuppressLint("DefaultLocale")
    public String getUrl(int page) {
        if (gender != null) {
            int genderKey;
            System.out.println("Gender Value : " + gender.value);
            if(gender.value == 1) {
                return String.format("/shop?sort=order&page=%d", page);
            }
            else if(gender.value == 2){
                genderKey = 39;
            }
            else if(gender.value == 3){
                genderKey = 40;
            }
            else if(gender.value == 4) {
                genderKey = 42;
            }
            else {
                genderKey = 41;
            }

            return String.format("/shop?category=%d&sort=order&page=%d", genderKey, page);

        }
        else
            return String.format("/shop?sort=order&page=%d", page);
    }

    //

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mode);
        dest.writeSerializable(click);
        dest.writeSerializable(nav);
        dest.writeSerializable(gender);
        dest.writeInt(root ? 1 : 0);
        dest.writeSerializable(indicator);
        dest.writeInt(stylemap ? 1 : 0);
    }

    protected ShopArgs(Parcel in) {
        mode = (Mode) in.readSerializable();
        click = (Click) in.readSerializable();
        nav = (Nav) in.readSerializable();
        gender = (Gender) in.readSerializable();
        root = in.readInt() == 1;
        indicator = (Indicator) in.readSerializable();
        stylemap = in.readInt() == 1;
    }

    //

    public static class All extends ShopArgs {

        private All() {
            super(Mode.ALL, Click.SHOP, null, null, false);
        }

        //

        private All(Parcel in) {
            super(in);
        }

        public static final Creator<Profile> CREATOR = new Creator<Profile>() {

            @Override
            public Profile createFromParcel(Parcel in) {
                return new Profile(in);
            }

            @Override
            public Profile[] newArray(int size) {
                return new Profile[size];
            }
        };
    }

    //

    public static class MainShopNew extends ShopArgs {

        private MainShopNew() {
            super(Mode.MAIN_SHOP_NEW, Click.SHOP, Nav.SHOP_INFO, Gender.ALL, false);
        }

        //

        private MainShopNew(Parcel in) {
            super(in);
        }

        public static final Creator<MainShopNew> CREATOR = new Creator<MainShopNew>() {

            @Override
            public MainShopNew createFromParcel(Parcel in) {
                return new MainShopNew(in);
            }

            @Override
            public MainShopNew[] newArray(int size) {
                return new MainShopNew[size];
            }
        };
    }

    public static class MainShopByPlace extends ShopArgs {

        public final ShopPlace place;

        //

        private MainShopByPlace(ShopPlace place) {
            super(Mode.MAIN_SHOP_BY_PLACE, Click.SHOP, Nav.SHOP_INFO, Gender.ALL, false);

            this.place = place;
        }

        //

        @Override
        @SuppressLint("DefaultLocale")
        public String getUrl(int page) {
            int genderKey;
            if(gender.value == 1) {
                return String.format("/shop?place=%d&page=%d", place.getKey(), page);
            }
            else if(gender.value == 2){
                genderKey = 39;
            }
            else if(gender.value == 3){
                genderKey = 40;
            }
            else if(gender.value == 4) {
                genderKey = 42;
            }
            else {
                genderKey = 41;
            }

            return String.format("/shop?category=%d&place=%d&page=%d", genderKey, place.getKey(), page);
        }

        //

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            dest.writeParcelable(place, flags);
        }

        protected MainShopByPlace(Parcel in) {
            super(in);

            place = in.readParcelable(ShopPlace.class.getClassLoader());
        }

        public static final Creator<MainShopByPlace> CREATOR = new Creator<MainShopByPlace>() {

            @Override
            public MainShopByPlace createFromParcel(Parcel in) {
                return new MainShopByPlace(in);
            }

            @Override
            public MainShopByPlace[] newArray(int size) {
                return new MainShopByPlace[size];
            }
        };
    }

    public static class MainShopByConcept extends ShopArgs {

        public final ShopConcept concept;

        //

        private MainShopByConcept(ShopConcept concept) {
            super(Mode.MAIN_SHOP_BY_CONCEPT, Click.SHOP, Nav.SHOP_INFO, Gender.ALL, false);

            this.concept = concept;
        }

        //

        @Override
        @SuppressLint("DefaultLocale")
        public String getUrl(int page) {
            return String.format("/shop?gender=%d&concept=%d&page=%d", gender.value, concept.getKey(), page);
        }

        //

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            dest.writeParcelable(concept, flags);
        }

        protected MainShopByConcept(Parcel in) {
            super(in);

            concept = in.readParcelable(ShopConcept.class.getClassLoader());
        }

        public static final Creator<MainShopByConcept> CREATOR = new Creator<MainShopByConcept>() {

            @Override
            public MainShopByConcept createFromParcel(Parcel in) {
                return new MainShopByConcept(in);
            }

            @Override
            public MainShopByConcept[] newArray(int size) {
                return new MainShopByConcept[size];
            }
        };
    }

    public static class MainShopByBrand extends ShopArgs {

        public final Brand brand;

        //

        private MainShopByBrand(Brand brand) {
            super(Mode.MAIN_SHOP_BY_BRAND, Click.SHOP, Nav.SHOP_BRAND, Gender.ALL, false);

            this.brand = brand;
        }

        //

        @Override
        @SuppressLint("DefaultLocale")
        public String getUrl(int page) {
            return String.format("/shop?gender=%d&brand=%d&page=%d", gender.value, brand.getKey(), page);
        }

        //

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            dest.writeParcelable(brand, flags);
        }

        protected MainShopByBrand(Parcel in) {
            super(in);

            brand = in.readParcelable(Brand.class.getClassLoader());
        }

        public static final Creator<MainShopByBrand> CREATOR = new Creator<MainShopByBrand>() {

            @Override
            public MainShopByBrand createFromParcel(Parcel in) {
                return new MainShopByBrand(in);
            }

            @Override
            public MainShopByBrand[] newArray(int size) {
                return new MainShopByBrand[size];
            }
        };
    }

    public static class MainProduct extends ShopArgs {

        private MainProduct() {
            super(Mode.MAIN_PRODUCT, Click.SHOP, Nav.SHOP_PRODUCT, null, true, Indicator.NEXT, true);
        }

        //

        private MainProduct(Parcel in) {
            super(in);
        }

        public static final Creator<MainProduct> CREATOR = new Creator<MainProduct>() {

            @Override
            public MainProduct createFromParcel(Parcel in) {
                return new MainProduct(in);
            }

            @Override
            public MainProduct[] newArray(int size) {
                return new MainProduct[size];
            }
        };
    }

    //

    public static class Profile extends ShopArgs {

        private Profile() {
            super(Mode.PROFILE, Click.SHOP, Nav.SHOP_INFO, null, false);
        }

        //

        private Profile(Parcel in) {
            super(in);
        }

        public static final Creator<Profile> CREATOR = new Creator<Profile>() {

            @Override
            public Profile createFromParcel(Parcel in) {
                return new Profile(in);
            }

            @Override
            public Profile[] newArray(int size) {
                return new Profile[size];
            }
        };
    }

    //

    public static class ShopList extends ShopArgs {

        public final List<Shop> list;

        //

        private ShopList(List<Shop> list) {
            super(Mode.SHOPLIST, Click.SHOP, Nav.SHOP_INFO, null, false);

            this.list = list;
        }

        //

        private ShopList(Parcel in) {
            super(in);

            in.readTypedList(list = new ArrayList<Shop>(), Shop.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            dest.writeTypedList(list);
        }

        public static final Creator<ShopList> CREATOR = new Creator<ShopList>() {

            @Override
            public ShopList createFromParcel(Parcel in) {
                return new ShopList(in);
            }

            @Override
            public ShopList[] newArray(int size) {
                return new ShopList[size];
            }
        };
    }

    //

    public static class Search extends ShopArgs {

        public final String query;
        public final List<Shop> list;

        //

        private Search(String query, List<Shop> list) {
            super(Mode.SEARCH, Click.SHOP, Nav.SHOP_INFO, null, false);

            this.query = query;
            this.list = list;
        }

        //

        private Search(Parcel in) {
            super(in);

            query = in.readString();
            in.readTypedList(list = new ArrayList<Shop>(), Shop.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            dest.writeString(query);
            dest.writeTypedList(list);
        }

        public static final Creator<Search> CREATOR = new Creator<Search>() {

            @Override
            public Search createFromParcel(Parcel in) {
                return new Search(in);
            }

            @Override
            public Search[] newArray(int size) {
                return new Search[size];
            }
        };
    }
}
