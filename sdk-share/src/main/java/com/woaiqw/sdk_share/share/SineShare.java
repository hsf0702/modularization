package com.woaiqw.sdk_share.share;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.woaiqw.sdk_share.model.IShareModel;
import com.woaiqw.sdk_share.utils.BitmapAsyncTask;


/**
 * Created by haoran on 2018/8/7.
 */

public class SineShare {


    WbShareHandler mWbShareHandler;
    private Activity mActivity;

    public SineShare(Activity activity, String appKey) {
        this(activity, appKey, "https://api.weibo.com/oauth2/default.html");
    }

    public SineShare(Activity activity, String appKey, String redirectUrl) {
        this(activity, appKey, redirectUrl, "");
    }

    public SineShare(Activity activity, String appKey, String redirectUrl, String scope) {
        Application application = activity.getApplication();
        WbSdk.install(application, new AuthInfo(application, appKey, redirectUrl, scope));
        this.mActivity = activity;
        mWbShareHandler = new WbShareHandler(activity);
        mWbShareHandler.registerApp();
    }

    public void doResultIntent(Intent intent, WbShareCallback callback) {
        mWbShareHandler.doResultIntent(intent, callback);
    }


    public void sendTextMessage(IShareModel share) {
        String title = share.getTitle();
        String content = share.getContent();
        String actionUrl = share.getActionUrl();

        WeiboMultiMessage message = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.title = title;
        textObject.text = content;
        textObject.actionUrl = actionUrl;
        message.textObject = textObject;

        mWbShareHandler.shareMessage(message, false);

    }


    public void sendWebShareMessage(IShareModel share) {
        final String title = share.getTitle();
        final String content = share.getContent();
        final String actionUrl = share.getActionUrl();
        final String imgUrl = share.getImgUrl();
        final int drawableId = share.getDrawableId();
        final WeiboMultiMessage message = new WeiboMultiMessage();


        TextObject textObject = new TextObject();
        textObject.title = title;
        textObject.text = content;
        textObject.actionUrl = actionUrl;
        message.textObject = textObject;


        if (TextUtils.isEmpty(imgUrl)) {

            Bitmap bmp = BitmapFactory.decodeResource(mActivity.getResources(), drawableId);
            ImageObject imageObject = new ImageObject();
            imageObject.setImageObject(bmp);
            message.imageObject = imageObject;
            mWbShareHandler.shareMessage(message, false);

        } else {
            new BitmapAsyncTask(mActivity, imgUrl, new BitmapAsyncTask.OnBitmapListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {

                    ImageObject imageObject = new ImageObject();
                    imageObject.setImageObject(bitmap);
                    message.imageObject = imageObject;
                    mWbShareHandler.shareMessage(message, false);

                }

                @Override
                public void onException(Exception exception) {

                    Bitmap bmp = BitmapFactory.decodeResource(mActivity.getResources(), drawableId);
                    ImageObject imageObject = new ImageObject();
                    imageObject.setImageObject(bmp);
                    message.imageObject = imageObject;
                    mWbShareHandler.shareMessage(message, false);

                }
            }).execute();
        }

    }

    public void sendImageMessage(IShareModel share) {

        final String title = share.getTitle();
        final String content = share.getContent();
        final String actionUrl = share.getActionUrl();
        final String imgUrl = share.getImgUrl();
        final int drawableId = share.getDrawableId();
        final WeiboMultiMessage message = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.title = title;
        textObject.text = content;
        textObject.actionUrl = actionUrl;
        message.textObject = textObject;

        final ImageObject imageObject = new ImageObject();

        if (TextUtils.isEmpty(imgUrl)) {

            Bitmap bmp = BitmapFactory.decodeResource(mActivity.getResources(), drawableId);
            imageObject.setImageObject(bmp);

        } else {

            new BitmapAsyncTask(mActivity, imgUrl, new BitmapAsyncTask.OnBitmapListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {

                    imageObject.setImageObject(bitmap);
                    message.imageObject = imageObject;
                    mWbShareHandler.shareMessage(message, false);

                }

                @Override
                public void onException(Exception exception) {

                    Bitmap bmp = BitmapFactory.decodeResource(mActivity.getResources(), drawableId);
                    imageObject.setImageObject(bmp);
                    message.imageObject = imageObject;
                    mWbShareHandler.shareMessage(message, false);

                }
            }).execute();

        }
    }

}


