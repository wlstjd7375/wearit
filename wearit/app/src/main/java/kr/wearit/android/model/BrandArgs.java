package kr.wearit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import kr.wearit.android.view.Nav;

public abstract class BrandArgs implements Parcelable {

	public enum Mode {
		MAIN_SHOP_BRAND, MAIN_PRODUCT, SHOP_BRAND, SEARCH
	}

	//

	public static final MainShop MAIN_SHOP = new MainShop();
	public static final MainProduct MAIN_PRODUCT = new MainProduct();

	//

	public static ShopBrand create(Shop shop) {
		return new ShopBrand(shop);
	}

	public static Search create(String query, List<Brand> list) {
		return new Search(query, list);
	}

	//

	public final Mode mode;
	public final Nav nav;

	//

	protected BrandArgs(Mode mode, Nav nav) {
		this.mode = mode;
		this.nav = nav;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(mode);
		dest.writeSerializable(nav);
	}

	protected BrandArgs(Parcel in) {
		mode = (Mode) in.readSerializable();
		nav = (Nav) in.readSerializable();
	}

	//

	public static class MainShop extends BrandArgs {

		private MainShop() {
			super(Mode.MAIN_SHOP_BRAND, Nav.BRAND_SHOP);
		}

		//

		protected MainShop(Parcel in) {
			super(in);
		}

		public static final Creator<MainShop> CREATOR = new Creator<MainShop>() {

			@Override
			public MainShop createFromParcel(Parcel in) {
				return new MainShop(in);
			}

			@Override
			public MainShop[] newArray(int size) {
				return new MainShop[size];
			}
		};
	}

	public static class MainProduct extends BrandArgs {

		private MainProduct() {
			super(Mode.MAIN_PRODUCT, Nav.BRAND_PRODUCT);
		}

		//

		protected MainProduct(Parcel in) {
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

	public static class ShopBrand extends BrandArgs {

		public final Shop shop;

		//

		private ShopBrand(Shop shop) {
			super(Mode.SHOP_BRAND, Nav.BRAND);

			this.shop = shop;
		}

		//

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);

			dest.writeParcelable(shop, flags);
		}

		protected ShopBrand(Parcel in) {
			super(in);

			shop = in.readParcelable(ClassLoader.getSystemClassLoader());
		}

		public static final Creator<ShopBrand> CREATOR = new Creator<ShopBrand>() {

			@Override
			public ShopBrand createFromParcel(Parcel in) {
				return new ShopBrand(in);
			}

			@Override
			public ShopBrand[] newArray(int size) {
				return new ShopBrand[size];
			}
		};
	}

	//

	public static class Search extends BrandArgs {

		public final String query;
		public final List<Brand> list;

		//

		private Search(String query, List<Brand> list) {
			super(Mode.SEARCH, Nav.BRAND);

			this.query = query;
			this.list = list;
		}

		//

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);

			dest.writeString(query);
			dest.writeTypedList(list);
		}

		protected Search(Parcel in) {
			super(in);

			query = in.readString();
			in.readTypedList(list = new ArrayList<Brand>(), Brand.CREATOR);
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
