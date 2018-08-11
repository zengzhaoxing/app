package com.example.admin.fastpay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.CommonProblemBean;

import java.util.List;

/**
 * Created by sun on 2017/5/24.
 */

public class CommonProblemAdapter extends BaseAdapter {

    private Context context;
    private List<CommonProblemBean.DataBean> dataBeen;
    private List<CommonProblemBean.DataBean> been;
    private int selectItem;
    private LayoutInflater inflater;
    int sign = -1;

    public CommonProblemAdapter(Context context, List<CommonProblemBean.DataBean> dataBeen) {
        this.context = context;
        this.dataBeen = dataBeen;
    }

    @Override
    public int getCount() {
        return dataBeen == null ? 0 : dataBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.listview_common_problem,null);
            viewHolder.tv_item_problem = (TextView) convertView.findViewById(R.id.tv_item_problem);
            viewHolder.tv_item_content = (TextView) convertView.findViewById(R.id.tv_item_content);
            viewHolder.iv_item_problem = (ImageView) convertView.findViewById(R.id.iv_item_problem);
            viewHolder.rl_problem = (RelativeLayout) convertView.findViewById(R.id.rl_problem);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CommonProblemBean.DataBean bean = dataBeen.get(position);

        viewHolder.tv_item_problem.setText(bean.getTitle());
        viewHolder.tv_item_content.setText(bean.getSummary()+":"+bean.getContent());

        //刷新adapter的时候，getview重新执行，此时对在点击中标记的position做处理
        if (selectItem == position) {//当条目为刚才点击的条目时
            if (viewHolder.tv_item_problem.isSelected()) {//当条目状态图标为选中时，说明该条目处于展开状态，此时让它隐藏，并切换状态图标的状态。
                viewHolder.tv_item_problem.setSelected(false);
                viewHolder.tv_item_content.setVisibility(View.GONE);
                Log.e("listview","if执行");
                selectItem=-1;//隐藏布局后需要把标记的position去除掉，否则，滑动listview让该条目划出屏幕范围，
                // 当该条目重新进入屏幕后，会重新恢复原来的显示状态。经过打log可知每次else都执行一次 （条目第二次进入屏幕时会在getview中寻找他自己的状态，相当于重新执行一次getview）
                //因为每次滑动的时候没标记得position填充会执行click
            } else {//当状态条目处于未选中时，说明条目处于未展开状态，此时让他展开。同时切换状态图标的状态。
                viewHolder.tv_item_problem.setSelected(true);
                viewHolder.tv_item_content.setVisibility(View.VISIBLE);

                Log.e("listview","else执行");
            }
//                ObjectAnimator//
//                        .ofInt(vh.ll_hide, "rotationX", 0.0F, 360.0F)//
//                        .setDuration(500)//
//                        .start();
            // vh.selectorImg.setSelected(true);
        } else {//当填充的条目position不是刚才点击所标记的position时，让其隐藏，状态图标为false。

            //每次滑动的时候没标记得position填充会执行此处，把状态改变。所以如果在以上的if (vh.selectorImg.isSelected()) {}中不设置clickPosition=-1；则条目再次进入屏幕后，还是会进入clickposition==position的逻辑中
            //而之前的滑动（未标记条目的填充）时，执行此处逻辑，已经把状态图片的selected置为false。所以上面的else中的逻辑会在标记过的条目第二次进入屏幕时执行，如果之前的状态是显示，是没什么影响的，再显示一次而已，用户看不出来，但是如果是隐藏状态，就会被重新显示出来
            viewHolder.tv_item_content.setVisibility(View.GONE);
            viewHolder.tv_item_problem.setSelected(false);

//            Log.e("listview","状态改变");
        }

        return convertView;
    }


    private void in(final ViewHolder viewHolder, final int position) {
        viewHolder.rl_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }



    class ViewHolder{
        TextView tv_item_problem;
        TextView tv_item_content;
        ImageView iv_item_problem;
        RelativeLayout rl_problem;
    }

//    /**
//     * 取得分组数
//     * @return 组数
//     */
//    @Override
//    public int getGroupCount() {
//        return dataBeen.size();
//    }
//
//    /**
//     * 取得指定分组的子元素数
//     * @param groupPosition：要取得子元素个数的分组位置
//     * @return:指定分组的子元素个数
//     */
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return been.size();
//    }
//
//    /**
//     * 取得与给定分组关联的数据
//     * @param groupPosition 分组的位置
//     * @return 指定分组的数据
//     */
//    @Override
//    public Object getGroup(int groupPosition) {
//        return dataBeen.get(groupPosition);
//    }
//
//    /**
//     * 取得与指定分组、指定子项目关联的数据
//     * @param groupPosition:包含子视图的分组的位置
//     * @param childPosition:指定的分组中的子视图的位置
//     * @return 与子视图关联的数据
//     */
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        return been.get(groupPosition);
//    }
//
//    /**
//     * 取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
//     * @param groupPosition 要取得ID的分组位置
//     * @return 与分组关联的ID
//     */
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    /**
//     * 取得给定分组中给定子视图的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
//     * @param groupPosition 包含子视图的分组的位置
//     * @param childPosition 要取得ID的指定的分组中的子视图的位置
//     * @return 与子视图关联的ID
//     *
//     */
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    /**
//     * 是否指定分组视图及其子视图的id对应的后台数据改变也会保持该id
//     * @return 是否相同的id总是指向同一个对象
//     */
//    @Override
//    public boolean hasStableIds() {
//        return true;
//    }
//
//    /**
//     * 去的用于显示给定分组的视图，该方法仅返回分组的视图对象。
//     * @param groupPosition:决定返回哪个视图的组位置
//     * @param isExpanded：该分组是展开状态（true）还是收起状态（false）
//     * @param convertView:如果可能，重用旧的视图对象.使用前你应该保证视图对象为非空，并且是否是合适的类型.
//     *            如果该对象不能转换为可以正确显示数据的视图 ，该方法就创建新视图.不保证使用先前由getGroupView(int,boolean,View, ViewGroup)创建的视图.
//     * @param parent:该视图最终从属的父视图
//     * @return 指定位置相应的组试图
//     */
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//
//        convertView = inflater.inflate(R.layout.group, null);
//        TextView groupNameTextView = (TextView) convertView.findViewById(R.id.tv_group);
//
//        ImageView ivSelector = (ImageView) convertView.findViewById(R.id.iv_selector);
//
//        groupNameTextView.setText(getGroup(groupPosition).toString());
//
////        ivSelector.setImageResource(R.drawable.jiantouxiaxi);
////
////        // 更换展开分组图片
////        if (!isExpanded) {
////            ivSelector.setImageResource(R.drawable.jiantoushang);
////        }
//
//        return convertView;
//    }
//
//    /**
//     * 取得显示给定分组给定子位置的数据用的视图
//     * @param groupPosition:包含要取得子视图的分组位置。
//     * @param childPosition：分组中子视图（要返回的视图）的位置。
//     * @param isLastChild：该视图是否为组中的最后一个视图。
//     * @param convertView： 如果可能，重用旧的视图对象，使用前应保证视图对象为非空，且是否是适合的类型。
//     *            如果该对象不能转换为正确显示数据的视图，该方法就创建新视图。
//     *            不保证使用先前由getChildView(int,int,boolean,View,ViewGroup)创建的视图。
//     * @param parent：该视图最终从属的父视图
//     * @return:指定位置相应的子视图。
//     */
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//
//        convertView = inflater.inflate(R.layout.child, null);
//
//        TextView nickTextView = (TextView) convertView.findViewById(R.id.tv_child);
//
//        nickTextView.setText(getChild(groupPosition, childPosition).toString());
//
//        return convertView;
//    }
//
//    /**
//     * 指定位置的子视图是否可选择
//     * @param groupPosition 包含要取得子视图的分组位置
//     * @param childPosition 分组中子视图的位置
//     * @return 是否子视图可选择
//     */
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return true;
//    }


}
