package kr.wearit.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.util.DynamicHeightImageView;

import java.util.ArrayList;
import kr.wearit.android.R;
import kr.wearit.android.model.News;
import kr.wearit.android.model.NewsPair;
import kr.wearit.android.util.ImageUtil;
import kr.wearit.android.view.news.NewsActivity;

/**
 * Created by KimJS on 2016-09-11.
 */
public class MainNewsAdapter extends ArrayAdapter<NewsPair> {

    private String TAG = "MainNewsAdapter##";
    private Context mContext;
    private ArrayList<NewsPair> mNewsList;
    private int mScreenWidth;

    // View lookup cache
    private static class ViewHolder {
        ImageView ivNewstLeft;
        ImageView ivNewsRight;
        TextView tvTitleLeft;
        TextView tvTitleRight;
    }

    public MainNewsAdapter(Context context, ArrayList<NewsPair> arrayList, int screenWidth) {
        super(context, R.layout.listrow_main_news, arrayList);
        // TODO Auto-generated constructor stub
        mContext = context;
        mNewsList = arrayList;
        mScreenWidth = screenWidth;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        //Log.d(TAG, "position = " + position +  " size = " + mNewsList.size());
        View view = convertView;
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.listrow_main_news, parent, false);
            //viewHolder.tvTitleLeft = (TextView)view.findViewById(R.id.tvNewsTitleLeft);
            //viewHolder.tvTitleRight = (TextView)view.findViewById(R.id.tvNewsTitleRight);
            viewHolder.ivNewstLeft = (ImageView)view.findViewById(R.id.ivMainNewsLeft);
            viewHolder.ivNewsRight = (ImageView)view.findViewById(R.id.ivMainNewsRight);

            //fix height
            view.getLayoutParams().height = mScreenWidth/2;
            view.requestLayout();

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }


        final News itemLeft = getItem(position).getNews1();
        viewHolder.ivNewstLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewsActivity.class);
                intent.putExtra("key", itemLeft.getKey());
                getContext().startActivity(intent);
            }
        });
        final News itemRight = getItem(position).getNews2();
        viewHolder.ivNewsRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewsActivity.class);
                intent.putExtra("key", itemRight.getKey());
                getContext().startActivity(intent);
            }
        });
        //set left
        //viewHolder.ivNewstLeft.setHeightRatio(1);
        ImageUtil.display(viewHolder.ivNewstLeft, itemLeft.getImagePath());
        //viewHolder.tvTitleLeft.setText(itemLeft.getTitle());

        //set right
        //viewHolder.ivNewstLeft.setHeightRatio(1);
        ImageUtil.display(viewHolder.ivNewsRight, itemRight.getImagePath());
        //viewHolder.tvTitleRight.setText(itemRight.getTitle());

        return view;
    }

}
