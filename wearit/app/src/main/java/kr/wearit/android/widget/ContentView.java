package kr.wearit.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;

import kr.wearit.android.Config;
import kr.wearit.android.R;
import kr.wearit.android.model.Content;
import kr.wearit.android.model.ContentPart;
import kr.wearit.android.util.ImageUtil;

public class ContentView extends LinearLayout {

    private static final String TAG = ContentView.class.getSimpleName();
    private static final boolean LOG = Config.LOG;

    public ContentView(Context context) {
        super(context);

        init();
    }

    public ContentView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    //

    private void init() {
        setOrientation(VERTICAL);
    }

    //

    //type = 1 => ProductActivity
    public void setContent(Content content, String title) {
        if (LOG)
            Log.d(TAG, "setContent // content = " + content);

        if (content == null) {
            Log.e(TAG, "setContent // content = " + content);
            return;
        }


        final ArrayList<String> images = new ArrayList<String>();
        boolean movie = false;
        View titleView = null;

        for (int i = 0, l = content.getParts().size(); i < l; i++) {
            ContentPart part = content.getParts().get(i);
            View view = null;

            switch (part.getTypeEnum()) {

                case TEXT:
                    if (title.length() != 0 && titleView == null) {
                        titleView = createTitle("");
                        titleView = createTitle(title);
                        addView(titleView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }

                    view = createText(part);
                    break;

                case IMAGE:
                    final int index = images.size();

                    images.add(part.getImageObject().getImagePath());
                    if(i==0){
                        view = createImage(part, 0);
                    }
                    else {
                        view = createImage(part);
                    }
                    break;
            }

            addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
        if(titleView == null){
            titleView = createTitle(title);
            addView(titleView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    public void setContent(Content content, String title, int type) {
        if (LOG)
            Log.d(TAG, "setContent // content = " + content);

        if (content == null) {
            Log.e(TAG, "setContent // content = " + content);
            return;
        }

        final ArrayList<String> images = new ArrayList<String>();
        boolean movie = false;
        View titleView = null;

        for (int i = 0, l = content.getParts().size(); i < l; i++) {
            ContentPart part = content.getParts().get(i);
            View view = null;


            switch (part.getTypeEnum()) {

                case TEXT:
                    if (title.length() != 0 && titleView == null) {
                        titleView = createTitle("");
                        titleView = createTitle(title);
                        addView(titleView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }

                    view = createText(part, type);
                    break;

                case IMAGE:
                    final int index = images.size();

                    images.add(part.getImageObject().getImagePath());
//                    if(i != 0) {
//                        ContentPart frontPart = content.getParts().get(i-1);
//                        if(frontPart.getTypeEnum() != ContentPart.Type.IMAGE){
//                            view = createImage(part);
//                        }
//                        else {
//                            view = createImage(part, type);
//                        }
//                    }
//                    else {
                    view = createImage(part, type);
//                    }
                    break;
            }

            addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
        if(titleView == null){
            titleView = createTitle(title);
            addView(titleView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }



    //

    @SuppressLint("InflateParams")
    private TextView createText(ContentPart part) {
        TextView view = (TextView) inflate(getContext(), R.layout.content_text, null).findViewById(R.id.text);

        String text = part.getText();

        view.setText(part.getText());

        return view;
    }

    private TextView createText(ContentPart part, int type) {
        TextView view = (TextView) inflate(getContext(), R.layout.content_product_text, null).findViewById(R.id.text);

        String text = part.getText();

        view.setText(part.getText());

        return view;
    }

    private TextView createTitle(String title){
        TextView view = (TextView) inflate(getContext(), R.layout.content_title, null).findViewById(R.id.title);
        view.setText(title);

        return view;
    }


    private ImageView createImage(ContentPart part) {
        ImageView view = (ImageView) inflate(getContext(), R.layout.content_image, null).findViewById(R.id.image);

        ImageUtil.display(view, part.getImageObject());

        return view;
    }

    private ImageView createImage(ContentPart part, int type) {
        ImageView view = (ImageView) inflate(getContext(), R.layout.content_product_image, null).findViewById(R.id.image);
//		view.setHeightRatio(ImageUtil.getRatio(part.getImageObject()));
        ImageUtil.display(view, part.getImageObject());

        return view;
    }
}
