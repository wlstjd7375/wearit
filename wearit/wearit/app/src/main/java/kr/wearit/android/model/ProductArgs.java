package kr.wearit.android.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import kr.wearit.android.BuildConfig;

public abstract class ProductArgs implements Parcelable {

	private static final boolean ASSERT = BuildConfig.DEBUG;

	//

	public enum Mode {
		ALL, ROOT, SHOP, BRAND, PROFILE, SEARCH
	}

	//

	public static final Profile PROFILE = new Profile();

	//

	public static Search create(String query, List<Product> list) {
		return new Search(query, list);
	}

	public static BrandProduct create(Brand brand) {
		return BrandProduct.newInstance(brand);
	}

	//

	public final Mode mode;
	public final boolean detail;

	//

	private ProductArgs(Mode mode, boolean detail) {
		this.mode = mode;
		this.detail = detail;
	}

	//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(mode);
		dest.writeInt(detail ? 1 : 0);
	}

	protected ProductArgs(Parcel in) {
		mode = (Mode) in.readSerializable();
		detail = in.readInt() == 1;
	}

	//

	public static class ShopProduct extends ProductArgs {

		public static ShopProduct newInstance(Key<Shop> shop, ProductType type, Key.Name<ProductCategory> category, Gender gender) {
			if (ASSERT) {
				Assert.assertNotNull(shop);
				Assert.assertNotNull(type);
				Assert.assertNotNull(category);
				Assert.assertNotNull(gender);
			}

			return new ShopProduct(Mode.SHOP, shop, type, category, gender);
		}

		//

		public final Key<Shop> shop;
		public final ProductType type;
		public final Key.Name<ProductCategory> category;
		public final Gender gender;

		//

		protected ShopProduct(Mode mode, Key<Shop> shop, ProductType type, Key.Name<ProductCategory> category, Gender gender) {
			super(mode, true);

			this.shop = shop;
			this.type = type;
			this.category = category;
			this.gender = gender;
		}

		//

		@SuppressLint("DefaultLocale")
		public String getUrl(int page) {
			return String.format("/product?shop=%d&type=%d&category=%d&gender=%d&page=%d", shop.getKey(), type.value, category.getKey(), gender.value, page);
		}

		@SuppressLint("DefaultLocale")
		public String getSaleUrl(int page) {
			return String.format("/product?shop=%d&type=%d&category=%d&gender=%d&sale=1&page=%d", shop.getKey(), type.value, category.getKey(), gender.value, page);
		}

		//

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);

			dest.writeParcelable(shop, flags);
			dest.writeSerializable(type);
			dest.writeParcelable(category, flags);
			dest.writeSerializable(gender);
		}

		protected ShopProduct(Parcel in) {
			super(in);

			shop = in.readParcelable(ClassLoader.getSystemClassLoader());
			type = (ProductType) in.readSerializable();
			category = in.readParcelable(ProductCategory.class.getClassLoader());
			gender = (Gender) in.readSerializable();
		}

		public static final Creator<ShopProduct> CREATOR = new Creator<ShopProduct>() {

			@Override
			public ShopProduct createFromParcel(Parcel in) {
				return new ShopProduct(in);
			}

			@Override
			public ShopProduct[] newArray(int size) {
				return new ShopProduct[size];
			}
		};
	}

	//

	public static class BrandProduct extends ProductArgs {

		public static BrandProduct newInstance(Brand brand) {
			if (ASSERT) {
				Assert.assertNotNull(brand);
			}

			return new BrandProduct(Mode.BRAND, brand);
		}

		//

		public final Brand brand;

		//

		protected BrandProduct(Mode mode, Brand brand) {
			super(mode, true);

			this.brand = brand;
		}

		//

		@SuppressLint("DefaultLocale")
		public String getUrl() {
			return String.format("/product/brand/%d", brand.getKey());
		}

		//

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);

			dest.writeParcelable(brand, flags);
		}

		protected BrandProduct(Parcel in) {
			super(in);

			brand = in.readParcelable(ClassLoader.getSystemClassLoader());
		}

		public static final Creator<BrandProduct> CREATOR = new Creator<BrandProduct>() {

			@Override
			public BrandProduct createFromParcel(Parcel in) {
				return new BrandProduct(in);
			}

			@Override
			public BrandProduct[] newArray(int size) {
				return new BrandProduct[size];
			}
		};
	}

	//

	public static class All extends ShopProduct {

		public static All newInstance(ProductType type, ProductCategory category, Gender gender) {
			return new All(type, category, gender);
		}

		private All(ProductType type, ProductCategory category, Gender gender) {
			super(Mode.SHOP, Shop.ALL, type, category, gender);
		}

		private All(Parcel in) {
			super(in);
		}

		public static final Creator<All> CREATOR = new Creator<All>() {

			@Override
			public All createFromParcel(Parcel in) {
				return new All(in);
			}

			@Override
			public All[] newArray(int size) {
				return new All[size];
			}
		};
	}

	//

	public static class Root extends ShopProduct {

		public static Root newInstance(ProductType type, ProductCategory category, Gender gender) {
			return new Root(type, category, gender);
		}

		private Root(ProductType type, ProductCategory category, Gender gender) {
			super(Mode.SHOP, Shop.ROOT, type, category, gender);
		}

		private Root(Parcel in) {
			super(in);
		}

		public static final Creator<Root> CREATOR = new Creator<Root>() {

			@Override
			public Root createFromParcel(Parcel in) {
				return new Root(in);
			}

			@Override
			public Root[] newArray(int size) {
				return new Root[size];
			}
		};
	}

	//

	public static class Profile extends ProductArgs {

		private Profile() {
			super(Mode.PROFILE, false);
		}

		//

		public String getUrl() {
			return "/me/product";
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

	public static class Search extends ProductArgs {

		public final String query;
		public final List<Product> list;

		//

		private Search(String query, List<Product> list) {
			super(Mode.SEARCH, false);

			this.query = query;
			this.list = list;
		}

		//

		private Search(Parcel in) {
			super(in);

			query = in.readString();
			in.readTypedList(list = new ArrayList<Product>(), Product.CREATOR);
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
