package srauhbaasch.chucks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class JokesAdapter extends BaseAdapter {
    private ArrayList<String> dataList;
    private Context context;


    public JokesAdapter(Context context, ArrayList<String> dataList){
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.joke_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.jokeTextView = convertView.findViewById(R.id.jokeTextView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.jokeTextView.setText(dataList.get(position));

        convertView.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.LTGRAY);

        return convertView;
    }

    static class ViewHolder{
        public TextView jokeTextView;
    }
}
