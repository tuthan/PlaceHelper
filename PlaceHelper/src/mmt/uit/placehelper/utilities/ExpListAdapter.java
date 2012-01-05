package mmt.uit.placehelper.utilities;

import java.util.List;

import mmt.uit.placehelper.R;
import mmt.uit.placehelper.models.Child;
import mmt.uit.placehelper.models.MainGroup;

import android.R.drawable;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpListAdapter extends BaseExpandableListAdapter {

	private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET =
            {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {
         EMPTY_STATE_SET, // 0
         GROUP_EXPANDED_STATE_SET // 1
	};
	
	 @Override
	    public boolean areAllItemsEnabled()
	    {
	        return true;
	    }

	    private Context mContext;

	    private List<MainGroup> groups;

	    public ExpListAdapter(Context context, List<MainGroup> groups) {
	        this.mContext = context;
	        this.groups = groups;	        
	    }

	    /**
	     * A general add method, that allows you to add a Vehicle to this list
	     * 
	     * Depending on if the category of the vehicle is present or not,
	     * the corresponding item will either be added to an existing group if it 
	     * exists, else the group will be created and then the item will be added
	     * @param vehicle
	     */
	   /* public void addItem(Category category) {
	        if (!groups.contains(category.getGroup())) {
	            groups.add(category.getGroup());
	        }
//	        if ((category.getGroup() != "Khách sạn")&&(category.getGroup() != "Danh sách ưa thích")){
		        int index = groups.indexOf(category.getGroup());
		        if (children.size() < index + 1) {
		            children.add(new ArrayList<Category>());
		        }
		        children.get(index).add(category);
//	        }
	    }*/

	    @Override
	    public Object getChild(int groupPosition, int childPosition) {
	        return groups.get(groupPosition).getChild().get(childPosition);
	    }

	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	    
	    // Return a child view. You can load your custom layout here.
	    @Override
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
	            View convertView, ViewGroup parent) {
	        Child child = (Child) getChild(groupPosition, childPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) mContext
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.ph_cat_child, null);
	        }
	        TextView tv = (TextView) convertView.findViewById(R.id.tv_child);
	        tv.setText(child.getName());
	        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_child);
	        iv.setImageResource(child.getImgID());
	        return convertView;
	    }

	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return groups.get(groupPosition).getChild().size();
	    }

	    @Override
	    public Object getGroup(int groupPosition) {
	        return groups.get(groupPosition);
	    }

	    @Override
	    public int getGroupCount() {
	        return groups.size();
	    }

	    @Override
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }

	    // Return a group view. You can load your custom layout here.
	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
	            ViewGroup parent) {
	        MainGroup group =  (MainGroup)getGroup(groupPosition);
	        
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) mContext
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.ph_cat_group, null);
	        }
	        TextView tv = (TextView) convertView.findViewById(R.id.tv_group);
	        tv.setText(group.getName());
	        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_group);
	        iv.setImageResource(group.getImgID());
	        ImageView indicator = (ImageView)convertView.findViewById(R.id.gr_indicator);
	        if (getChildrenCount(groupPosition)==0){
	        	indicator.setVisibility(View.INVISIBLE);
	        }
	        else {
	        	indicator.setVisibility(View.VISIBLE);
	        	int stateIndex = (isExpanded?1:0);
	        	Drawable mDraw = indicator.getDrawable();
	        	mDraw.setState(GROUP_STATE_SETS[stateIndex]);
	        }
	        return convertView;
	    }

	    @Override
	    public boolean hasStableIds() {
	        return true;
	    }

	    @Override
	    public boolean isChildSelectable(int arg0, int arg1) {
	        return true;
	    }
}
