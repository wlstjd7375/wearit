package kr.wearit.android.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kr.wearit.android.App;
import kr.wearit.android.Config;
import kr.wearit.android.model.User;
import kr.wearit.android.ui.common.WaitDialog;
import kr.wearit.android.util.JsonUtil;


public class Api {

	private static final String TAG = Api.class.getSimpleName();
	private static final boolean LOG = Config.LOG;

	@Deprecated
	private static final String SITE_DEV = "http://192.168.0.15:8080";
	@Deprecated
	@SuppressWarnings("unused")
	private static final String SITE_STG = "http://stylemap.preduct.biz:9000";
	@Deprecated
	private static final String SITE = SITE_DEV;
	@SuppressWarnings("unused")
	@Deprecated
	private static final String BASE_API = SITE + "/api";

	private static final String CHARSET = "UTF-8";
	private static final String CONTENT_TYPE = "application/json";

	private static final String HEADER_USER_NAME = "X-STYLEMAP-USER";
	private static final String HEADER_APP = "X-STYLEMAP-APP";

	private static final Header HEADER_CONTENT_TYPE = new BasicHeader("Content-Type", CONTENT_TYPE);
	private static final Header HEADER_API = new BasicHeader("X-STYLEMAP-API", "stylemap-client");

	//

	public interface OnListener<T> {

		void onStart();

		void onSuccess(T data);

		void onFail();
	}

	//

	public static class OnEmptyListener<T> implements OnListener<T> {

		public OnEmptyListener() {
		}

		@Override
		public void onStart() {
		}

		@Override
		public void onSuccess(T data) {
		}

		@Override
		public void onFail() {
		}
	}

	//

	public static class OnDefaultListener<T> extends OnEmptyListener<T> {

		private String mFail = "실패했습니다";

		public OnDefaultListener() {
		}

		public OnDefaultListener(String fail) {
			mFail = fail;
		}

		@Override
		public void onFail() {
			Toast.makeText(App.getInstance(), mFail, Toast.LENGTH_SHORT).show();
		}
	}

	//

	public static class OnWaitListener<T> extends OnDefaultListener<T> {

		protected WaitDialog mWait;

		//

		public OnWaitListener(Activity activity) {
//			mWait = new WaitDialog(activity);
		}

		public OnWaitListener(Activity activity, boolean flag) {
			mWait = new WaitDialog(activity);
		}

		public OnWaitListener() {
//			mWait = new WaitDialog();
		}


		//

		private WaitDialog getWaitDialog() {
			return mWait;
		}
	}



	//

	public static class OnCancelableWaitListener<T> extends OnWaitListener<T> {

		private boolean canceled = false;

		//

		public OnCancelableWaitListener(Activity activity) {
			super(activity);

			mWait = new WaitDialog(activity, new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					canceled = true;
				}
			});
		}

		//

		public boolean isCanceled() {
			return canceled;
		}
	}

	//

	public interface OnAuthListener<T> extends OnListener<T> {
	}

	//

	public static class OnAuthEmptyListener<T> extends OnEmptyListener<T> implements OnAuthListener<T> {
	}

	//

	public static class OnAuthDefaultListener<T> extends OnDefaultListener<T> implements OnAuthListener<T> {

		public OnAuthDefaultListener() {
		}
	}

	//

	public static class OnAuthWaitListener<T> extends OnWaitListener<T> implements OnAuthListener<T> {

		public OnAuthWaitListener(Activity activity) {
			super(activity);
		}
	}

	//

	private static AsyncHttpClient client = new AsyncHttpClient();

	//

	public static String getResourceUrl(String resourceUrl) {
		if (TextUtils.isEmpty(resourceUrl))
			return null;

		return Config.getSite() + resourceUrl;
	}

	private static Header[] getHeaders() {
		return getHeaders(false);
	}

	private static Header[] getHeaders(boolean contentType) {
		List<Header> headers = new ArrayList<Header>();

		headers.add(HEADER_API);

		User user = App.getInstance().getUser();

		if (user != null)
			headers.add(new BasicHeader(HEADER_USER_NAME, String.valueOf(user.getKey())));

		if (contentType)
			headers.add(HEADER_CONTENT_TYPE);

		headers.add(new BasicHeader(HEADER_APP, App.getInstance().getApp()));

		return headers.toArray(new Header[0]);
	}

	//

	protected static <T> void get(String url, OnListener<T> handler, Type type) {
		if (LOG)
			Log.d(TAG, "get // url = " + url);
		client.get(App.getInstance(), Config.getBase() + url, getHeaders(), null, new ResponseHandler<T>(handler, type, url));
	}

	protected static <T> void post(String url, OnListener<T> handler, Type type) {
		post(url, null, handler, type);
	}

	protected static <T, R> void post(String url, T item, OnListener<R> handler, Type type) {
		if (LOG) {
			Log.d(TAG, "post // url = " + url);
			Log.d(TAG, "post // data = " + JsonUtil.toString(item));
		}

		try {
			client.post( //
					handler instanceof OnCheckListener ? ((OnCheckListener) handler).getActivity() : App.getInstance(), //
					Config.getBase() + url, //
					getHeaders(true), //
					new StringEntity(JsonUtil.toString(item), CHARSET), //
					CONTENT_TYPE, //
					new ResponseHandler<R>(handler, type, url));
		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}

	protected static <T, R> void postSync(String url, T item, OnListener<R> handler, Type type) {
		if (LOG) {
			Log.d(TAG, "post // url = " + url);
			Log.d(TAG, "post // data = " + JsonUtil.toString(item));
		}

		try {
			SyncHttpClient client = new SyncHttpClient();

			client.post( //
					handler instanceof OnCheckListener ? ((OnCheckListener) handler).getActivity() : App.getInstance(), //
					Config.getBase() + url, //
					getHeaders(true), //
					new StringEntity(JsonUtil.toString(item), CHARSET), //
					CONTENT_TYPE, //
					new ResponseHandler<R>(handler, type, url));

		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}

	protected static <T> void postFile(String url, String path, OnListener<T> handler, Type type) {
		if (LOG)
			Log.d(TAG, "postFile // url = " + url + ", path = " + path);

		try {
			RequestParams params = new RequestParams();
			params.put("file", new File(path));

			client.post( //
					handler instanceof OnCheckListener ? ((OnCheckListener) handler).getActivity() : App.getInstance(), //
					Config.getBase() + url, //
					getHeaders(), //
					params, //
					CONTENT_TYPE, //
					new ResponseHandler<T>(handler, type, url));
		} catch (Exception e) {
			//throw new AssertionError(e);

			Toast.makeText(App.getInstance(), "첨부 파일을 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
		}
	}

	protected static <T> void put(String url, OnListener<T> handler, Type type) {
		put(url, null, handler, type);
	}

	protected static <T, R> void put(String url, T item, OnListener<R> handler, Type type) {
		if (LOG)
			Log.d(TAG, "put // url = " + url);
		try {
			client.put( //
					handler instanceof OnCheckListener ? ((OnCheckListener) handler).getActivity() : App.getInstance(), //
					Config.getBase() + url, //
					getHeaders(true), //
					new StringEntity(JsonUtil.toString(item), CHARSET), //
					CONTENT_TYPE, //
					new ResponseHandler<R>(handler, type, url));
		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}

	protected static <T> void delete(String url, OnListener<T> handler, Type type) {
		if (LOG)
			Log.d(TAG, "delete // url = " + url);

		client.delete( //
				App.getInstance(), //
				Config.getBase() + url, //
				getHeaders(), //
				null, //
				new ResponseHandler<T>(handler, type, url));
	}

	//

	private static class ResponseHandler<T> extends TextHttpResponseHandler {

		private static final String TAG = Api.TAG + "." + ResponseHandler.class.getSimpleName();
		private static final boolean LOG = Config.LOG;
		private static final boolean LOG_W = true;

		//

		private OnListener<T> handler;
		private Type type;
		private String url;

		//

		public ResponseHandler(OnListener<T> handler, Type type, String url) {
			this.handler = handler;
			this.type = type;
			this.url = url;
		}

		//

		@SuppressWarnings("unused")
		@Override
		public void onStart() {
			if (LOG && false)
				Log.d(TAG, "get.onStart");

//			if (handler instanceof OnWaitListener)
//				((OnWaitListener<T>) handler).getWaitDialog().show();
		}

		@SuppressWarnings("unused")
		@Override
		public void onProgress(int bytesWritten, int totalSize) {
			if (LOG && false)
				Log.d(TAG, "onProgress // bytesWritten = " + bytesWritten + ", totalSize = " + totalSize);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, String responseString) {
			if (LOG)
				Log.d(TAG, "onSuccess // url = " + url + ", statusCode = " + statusCode + ", responseString = " + responseString);
			System.out.println(responseString);
			Response<T> response = JsonUtil.parseResponse(responseString, type);
			boolean canceled = handler instanceof OnCancelableWaitListener && ((OnCancelableWaitListener<T>) handler).isCanceled();

			if (response.isSuccess() && !canceled)
				handler.onSuccess((T) (response).getData());
			else
				handler.onFail();

//			if (handler instanceof OnWaitListener)
//				((OnWaitListener<T>) handler).getWaitDialog().hide();
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			if (LOG_W)
				Log.w(TAG, "onFailure // url = " + url + ", statusCode = " + statusCode + ", responseString = " + responseString, throwable);

			handler.onFail();

//			if (handler instanceof OnWaitListener)
//				((OnWaitListener<T>) handler).getWaitDialog().hide();
		}

		@SuppressWarnings("unused")
		@Override
		public void onFinish() {
			if (LOG && false)
				Log.d(TAG, "onFinish");
		}

		@Override
		public void onCancel() {
			if (LOG)
				Log.d(TAG, "onCancel");
		}

		@Override
		public void onRetry(int retryNo) {
			if (LOG)
				Log.d(TAG, "onRetry // retryNo = " + retryNo);
		}
	}

	//

	public static class OnCheckListener extends OnDefaultListener<Boolean> {

		protected Activity activity;
		protected WaitDialog wait;
		protected String ok;
		protected String no;
		//

		public OnCheckListener(Activity activity, String ok, String no) {
			this.activity = activity;
			this.ok = ok;
			this.no = no;

			//this.wait = new WaitDialog(activity);
			//this.wait.show();
		}
		//

		@Override
		public void onSuccess(Boolean check) {
			if (check)
				Toast.makeText(getActivity(), ok, Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getActivity(), no, Toast.LENGTH_SHORT).show();

			//wait.hide();
		}

		@Override
		public void onFail() {
			Toast.makeText(getActivity(), "실패했습니다", Toast.LENGTH_SHORT).show();

			//wait.hide();
		}

		//

		public Activity getActivity() {
			return activity;
		}
	}
}
