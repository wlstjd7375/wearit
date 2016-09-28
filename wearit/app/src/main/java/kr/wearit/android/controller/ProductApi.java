package kr.wearit.android.controller;

import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import kr.wearit.android.model.Brand;
import kr.wearit.android.model.Pagination;
import kr.wearit.android.model.Product;
import kr.wearit.android.model.ProductArgs;
import kr.wearit.android.model.ProductCategory;
import kr.wearit.android.model.ProductSpecial;
import kr.wearit.android.model.Style;

public class ProductApi extends Api {

	public static void getPage(ProductArgs.ShopProduct params, int page, OnListener<Pagination<Product>> handler) {
		get(params.getUrl(page), handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getSalePage(ProductArgs.ShopProduct params,int page, OnListener<Pagination<Product>> handler) {
		get(params.getSaleUrl(page), handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}
	public static void getListByShop(int shop, String order,int page, OnListener<Pagination<Product>> handler) {
		get("/product?shop="+shop+"&sort="+order+"&page="+page, handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getListByBrand(int brand, String order,int page, OnListener<Pagination<Product>> handler) {
		get("/product?brand="+brand+"&sort="+order+"&page="+page, handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getSaleListByShop(int shop, int page, OnListener<Pagination<Product>> handler) {
		get("/product?shop="+shop+"&sale=1"+"&page="+page, handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getSaleListByBrand(int brand, int page, OnListener<Pagination<Product>> handler) {
		get("/product?brand="+brand+"&sale=1"+"&page="+page, handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getSpecialList(int category, OnListener<Pagination<ProductSpecial>> handler) {
		get("/special?category=" + category,handler, new TypeToken<Response<Pagination<ProductSpecial>>>() {}.getType());
	}

	public static void getSpecial(int key, OnListener<ProductSpecial> handler) {
		get("/special/" + key,handler, new TypeToken<Response<ProductSpecial>>() {}.getType());
	}

	public static void getRelationPage(int relation, int page, OnListener<Pagination<Product>> handler) {
		get("/product/relation?relation=" + relation + "&page=" + page, handler, new TypeToken<Response<Pagination<Product>>>() {
		}.getType());
	}

	public static void getListByCategoryAndShop(int category, int shop, int page, OnListener<Pagination<Product>> handler) {
		get("/product?category=" + category + "&shop=" + shop + "&page=" + page, handler, new TypeToken<Response<Pagination<Product>>>() {
		}.getType());
	}

	public static void getListByCategoryAndBrand(int category, int brand, int page, OnListener<Pagination<Product>> handler) {
		get("/product?category=" + category + "&brand=" + brand + "&page=" + page, handler, new TypeToken<Response<Pagination<Product>>>() {
		}.getType());
	}
	public static void getListByCategory(int category, int page, OnListener<Pagination<Product>> handler) {
		get("/product?category=" + category+"&page="+page, handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getListOrder(int category, int page ,String order,OnListener<Pagination<Product>> handler){
		get("/product?sort="+order+"&category="+category+"&page="+page, handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getListOrder(int page ,String order,OnListener<Pagination<Product>> handler){
		get("/product?sort="+order+"&page="+page, handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getListSale(int category, int page ,OnListener<Pagination<Product>> handler){
		get("/product?sale=1"+"&category="+category+"&page="+page, handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getListSale(int page ,OnListener<Pagination<Product>> handler){
		get("/product?sale=1"+"&page="+page, handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getList(ProductArgs.Profile params, OnListener<ArrayList<Product>> handler) {
		get(params.getUrl(), handler, new TypeToken<Response<ArrayList<Product>>>() {}.getType());
	}

	public static void getList(String url, OnListener<ArrayList<Product>> handler) {
		get(url, handler, new TypeToken<Response<ArrayList<Product>>>() {}.getType());
	}

	public static void getList(Brand brand, OnListener<ArrayList<Product>> handler) {
		get(ProductArgs.create(brand).getUrl(), handler, new TypeToken<Response<ArrayList<Product>>>() {}.getType());
	}

	public static void getRecommend(ProductArgs.ShopProduct params, OnListener<ArrayList<Product>> handler) {
		get("/product/recommend?shop=" + params.shop.getKey() + "&type=" + params.type.value + (params.category.getKey() != ProductCategory.ALL.getKey() ? "&category=" + params.category.getKey() : "") + "&gender=" + params.gender.value, handler, new TypeToken<Response<ArrayList<Product>>>() {}.getType());
	}

	public static void get(Product item, OnListener<Product> handler) {
		get("/product/" + item.getKey(), handler, new TypeToken<Response<Product>>() {}.getType());
	}

	public static void get(int item, OnListener<Product> handler) {
		get("/product/" + item, handler, new TypeToken<Response<Product>>() {}.getType());
	}

	public static void getForRefresh(Product item, OnListener<Product> handler) {
		get("/product/" + item.getKey() + "?hit=n", handler, new TypeToken<Response<Product>>() {}.getType());
	}

	public static void getLink(Product item) {
		get("/product/" + item.getKey() + "/link", new OnEmptyListener<Void>() {}, new TypeToken<Response<Void>>() {}.getType());
	}

	public static void getStyle(int key, OnListener<ArrayList<Style>> handler) {
		get("/product/"+key+"/style",handler, new TypeToken<Response<ArrayList<Style>>>() {}.getType());
	}

	public static void getShopProductCategory(int shop, OnListener<ArrayList<ProductCategory>> handler) {
		get("/product/category/tree?shop=" + shop, handler, new TypeToken<Response<ArrayList<ProductCategory>>>() {}.getType());
	}

	public static void getBrandProductCategory(int brand, OnListener<ArrayList<ProductCategory>> handler) {
		get("/product/category/tree?brand=" + brand, handler, new TypeToken<Response<ArrayList<ProductCategory>>>() {}.getType());
	}

	//

	public static void addFavorite(Product item, OnAuthListener<Void> handler) {
		post("/product/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}

	public static void removeFavorite(Product item, OnAuthListener<Void> handler) {
		System.out.println("RemoveFavorite");
		delete("/product/" + item.getKey() + "/favorite", handler, new TypeToken<Response<Void>>() {}.getType());
	}

	//

	private static WeakReference<ArrayList<ProductCategory>> cacheCategoryTree;

	public static void getCategoGenderList(int gender,final OnListener<ArrayList<ProductCategory>> handler) {
		if (cacheCategoryTree != null) {
			ArrayList<ProductCategory> data = cacheCategoryTree.get();

			if (data != null) {
				handler.onSuccess(data);
				return;
			}
		}

		get("/product/category?key="+gender+"&mode=child-tree", new OnDefaultListener<ArrayList<ProductCategory>>() {

			@Override
			public void onSuccess(ArrayList<ProductCategory> data) {
				cacheCategoryTree = new WeakReference<ArrayList<ProductCategory>>(data);

				handler.onSuccess(data);
			}

			@Override
			public void onFail() {
				handler.onFail();
			}
		}, new TypeToken<Response<ArrayList<ProductCategory>>>() {}.getType());
	}

	public static void getCategoryTree(final OnListener<ArrayList<ProductCategory>> handler) {
		if (cacheCategoryTree != null) {
			ArrayList<ProductCategory> data = cacheCategoryTree.get();

			if (data != null) {
				handler.onSuccess(data);
				return;
			}
		}

		get("/product/category?mode=all-tree", new OnDefaultListener<ArrayList<ProductCategory>>() {

			@Override
			public void onSuccess(ArrayList<ProductCategory> data) {
				cacheCategoryTree = new WeakReference<ArrayList<ProductCategory>>(data);

				handler.onSuccess(data);
			}

			@Override
			public void onFail() {
				handler.onFail();
			}
		}, new TypeToken<Response<ArrayList<ProductCategory>>>() {}.getType());
	}

	public static void getRelations(OnListener<Pagination<Product>> handler) {
		get("/product", handler, new TypeToken<Response<Pagination<Product>>>() {}.getType());
	}

	public static void getRelationProductList(int relation, OnListener<ArrayList<Product>> handler) {
		get("/base/product/relation/" + relation + "/product", handler, new TypeToken<Response<ArrayList<Product>>>() {}.getType());
	}
}
