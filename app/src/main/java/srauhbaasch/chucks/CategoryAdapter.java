package srauhbaasch.chucks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends BaseAdapter{
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            //inflate layout
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate( R.layout.category_item, parent, false);

            //set up ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.descriptionTV = convertView.findViewById(R.id.categoryText);
            viewHolder.imageIV = convertView.findViewById(R.id.categoryImage);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        try {
            viewHolder.descriptionTV.setText(categoryList.get(position).getStringId());
            viewHolder.imageIV.setImageResource(categoryList.get(position).getImageId());
        } catch (NoSuchFieldException e) {
            viewHolder.descriptionTV.setText("Not Available");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            viewHolder.descriptionTV.setText("Not Available");
            e.printStackTrace();
        }

        return convertView;
    }

    static class ViewHolder {
        TextView descriptionTV;
        ImageView imageIV;
    }
}

