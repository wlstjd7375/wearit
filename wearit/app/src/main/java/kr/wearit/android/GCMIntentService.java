package kr.wearit.android;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.DeviceApi;
import kr.wearit.android.controller.MessageApi;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.view.SplashActivity;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = GCMIntentService.class.getSimpleName();
	private static final boolean LOG = Config.LOG;
	boolean isProcess = false;
	boolean isBackGround = false;
	boolean isMessageThread = false;
	//
	//private static final AtomicInteger mId = new AtomicInteger(1000);
	//
	private enum Type {
		BROADCAST_NOTIFICATION, BROADCAST_MESSAGE, MESSAGE, COUPON, POINT
	}
	//
	private Handler mHandler = new Handler();
	//
	public GCMIntentService() {
		super(Const.GCM_SENDER_ID);

		if (LOG)
			Log.d(TAG, "GCMIntentService");
	}
	//
	@Override
	protected void onRegistered(Context context, String registrationId) {
		if (LOG)
			Log.d(TAG, "onRegistered // registrationId = " + registrationId);

		DeviceApi.register(registrationId, new Api.OnDefaultListener<Void>() {

			@Override
			public void onSuccess(Void data) {
				GCMRegistrar.setRegisteredOnServer(App.getInstance(), true);
			}
		});
	}
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		if (LOG)
			Log.d(TAG, "onUnregistered // registrationId = " + registrationId);

		if (!GCMRegistrar.isRegisteredOnServer(context))
			return;

		DeviceApi.unregister(registrationId, new Api.OnDefaultListener<Void>() {

			@Override
			public void onSuccess(Void data) {
				GCMRegistrar.setRegisteredOnServer(App.getInstance(), false);
			}
		});
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void onMessage(final Context context, Intent intent) {
		if (LOG) {
			Log.d(TAG, "onMessage // intent = " + intent);

			Bundle extras = intent.getExtras();

			for (String key : extras.keySet())
				Log.d(TAG, "onMessage // " + key + " = " + extras.get(key));

			Log.d(TAG, "onMessage // login = " + App.getInstance().isLogin());
		}

		Type type = parseType(intent.getStringExtra(Const.GCM_TYPE));

		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();

		isProcess = false;
		isMessageThread = false;

		if (App.getInstance().isLogin()) {
			switch (type) {
				case BROADCAST_MESSAGE:
					if (!App.getInstance().getUser().isGcmMessage())
						return;
					break;
				case BROADCAST_NOTIFICATION:
					if (!App.getInstance().getUser().isGcmNotify())
						return;
					break;
				case COUPON:
					if (!App.getInstance().getUser().isGcmNotify())
						return;
					break;
			}
		}

		for(ActivityManager.RunningAppProcessInfo process : list)
		{
			if(process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
				isBackGround = true;
			}
			if((process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND || process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND)&& process.processName.equals("kr.wearit.android")){
				System.out.println("웨어잇이 실행중 !!");
				isProcess = true;
				break;
			}
		}

		final int user = Integer.parseInt(intent.getStringExtra(Const.GCM_USER));
		String userNickname = intent.getStringExtra(Const.GCM_USER_NICKNAME);
		String userImage = intent.getStringExtra(Const.GCM_USER_IMAGE);
		String content = intent.getStringExtra(Const.GCM_CONTENT);
		@SuppressWarnings("unused")
		String image = intent.getStringExtra(Const.GCM_IMAGE);

		Intent ci = null;

		final Notification.Builder builder = new Notification.Builder(context) //
				.setSmallIcon(R.drawable.ic_launcher) //
				.setContentText(content) //
						//.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, SplashActivity.class), 0)) //
				.setWhen(System.currentTimeMillis()) //
				.setAutoCancel(true);

		switch (type) {
			case BROADCAST_MESSAGE:
				ci = new Intent(context, SplashActivity.class);

				builder.setContentTitle(context.getString(R.string.app_name));
				break;
			case BROADCAST_NOTIFICATION:
				ci = new Intent(context, SplashActivity.class);

				builder.setContentTitle(context.getString(R.string.app_name));
				break;
			case COUPON:
//				if(!isProcess) {
//					ci = SplashActivity.createIntent(context, -1, "", false);
//				}
//				else {
				ci = new Intent(context, SplashActivity.class);
//				ci = CouponBoardActivity.createIntent(context);
//				}
				builder.setContentTitle(context.getString(R.string.app_name));
				break;
		}

		ci.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		builder.setContentIntent(PendingIntent.getActivity(context, 0, ci, PendingIntent.FLAG_UPDATE_CURRENT));

		final NotificationManager nm = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
		final int id = 1000;//mId.incrementAndGet();

//		if(!isProcess)
//			nm.notify(id, builder.getNotification());

		switch (type) {
			case BROADCAST_MESSAGE:
			case BROADCAST_NOTIFICATION:
				break;
			case COUPON:
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(context, "WearIt - 새로운 쿠폰이 도착했습니다.", Toast.LENGTH_SHORT).show();
					}
				});
				break;
		}

		if (!TextUtils.isEmpty(userImage)) {
			Resources res = context.getResources();

			ImageUtil.load(userImage, new ImageSize((int) res.getDimensionPixelSize(android.R.dimen.notification_large_icon_width), (int) res.getDimensionPixelSize(android.R.dimen.notification_large_icon_height)), new SimpleImageLoadingListener() {

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					if (LOG)
						Log.d(TAG, "onLoadingComplete // imageUri = " + imageUri);

					builder.setLargeIcon(loadedImage);

					nm.notify(id, builder.getNotification());
				}
			});
		}

		if (App.getInstance().isLogin()) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					MessageApi.getCount(new Api.OnAuthDefaultListener<Integer>() {

						@Override
						public void onSuccess(Integer data) {
							if (LOG)
								Log.d(TAG, "getCount // data = " + data);


							builder.setNumber(data);


							nm.notify(id, builder.getNotification());
						}
					});
				}
			});
		}
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		if (LOG)
			Log.d(TAG, "onDeletedMessages // total = " + total);
	}

	@Override
	public void onError(Context context, String errorId) {
		if (LOG)
			Log.d(TAG, "onError // errorId = " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		if (LOG)
			Log.d(TAG, "onRecoverableError // errorId = " + errorId);

		return super.onRecoverableError(context, errorId);
	}

	//

	private static Type parseType(String type) {
		if (Const.GCM_TYPE_BROADCAST_NOTIFICATION.equals(type))
			return Type.BROADCAST_NOTIFICATION;

		if (Const.GCM_TYPE_BROADCAST_MESSAGE.equals(type))
			return Type.BROADCAST_MESSAGE;

		if (Const.GCM_TYPE_MESSAGE.equals(type))
			return Type.MESSAGE;

		if (Const.GCM_TYPE_COUPON.equals(type))
			return Type.COUPON;

		if (Const.GCM_TYPE_POINT.equals(type))
			return Type.POINT;

		throw new AssertionError();
	}
}
