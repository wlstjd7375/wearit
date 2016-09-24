package kr.wearit.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import kr.wearit.android.R;

/**
 * Created by KimJS on 2016-09-17.
 */
public class MyPageAdapter  extends ArrayAdapter<String> {

    private String TAG = "MyPageAdapter##";
    private Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView tvMyPageItem;
    }

    public MyPageAdapter(Context context, ArrayList<String> arrayList) {
        super(context, R.layout.listrow_mypage, arrayList);
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.listrow_mypage, parent, false);
            viewHolder.tvMyPageItem = (TextView) view.findViewById(R.id.tvMyPageItem);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.tvMyPageItem.setText(getItem(position));


        return view;
    }

}
