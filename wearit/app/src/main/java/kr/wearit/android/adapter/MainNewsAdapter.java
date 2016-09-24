package kr.wearit.android.adapter;

import android.content.Context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
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


        News itemLeft = getItem(position).getNews1();
        News itemRight = getItem(position).getNews2();

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
