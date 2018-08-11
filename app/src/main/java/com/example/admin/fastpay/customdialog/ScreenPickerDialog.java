package com.example.admin.fastpay.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.admin.fastpay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2017/5/24.
 * <p>
 * 筛选
 */

public class ScreenPickerDialog extends Dialog {

    public ScreenPickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public interface OnDateSelectedListener {
        void onDateSelected(List<String> s, List<String> t);
    }

    public static class Builder {
        private List<String> mData;
        private List<String> stateData;
        private final Context context;
        private OnDateSelectedListener callback;

        private TextView bu_screen_zhifubao;
        private TextView bu_screen_weixin;
        private TextView bu_screen_jingdong;
        private TextView bu_screen_yinlian;
        private TextView bu_screen_yizhifu;
        private TextView bu_screen_chenggong;
        private TextView bu_screen_weizhifu;
        private TextView bu_screen_kuaijiea;

        public Builder(Context context) {
            this.context = context;
            mData=new ArrayList<>();
            stateData = new ArrayList<>();
        }

        /**
         * 获取当前选择的日期
         */
        private final List<String> getCurrDateValues() {
            mData.clear();

            if(bu_screen_jingdong.isSelected()) {
                mData.add(bu_screen_jingdong.getText().toString());
            }
            if(bu_screen_weixin.isSelected()) {
                mData.add(bu_screen_weixin.getText().toString());
            }
            if(bu_screen_yinlian.isSelected()) {
                mData.add(bu_screen_yinlian.getText().toString());
            }
            if(bu_screen_yizhifu.isSelected()) {
                mData.add(bu_screen_yizhifu.getText().toString());
            }
            if(bu_screen_zhifubao.isSelected()) {
                mData.add(bu_screen_zhifubao.getText().toString());
            }
            if (bu_screen_kuaijiea.isSelected()){
                mData.add(bu_screen_kuaijiea.getText().toString());
            }
            return mData;
        }

        private final List<String> getStateDateValues() {
            stateData.clear();
            if(bu_screen_chenggong.isSelected()) {
                stateData.add(bu_screen_chenggong.getText().toString());
            }
            if(bu_screen_weizhifu.isSelected()) {
                stateData.add(bu_screen_weizhifu.getText().toString());
            }
            return stateData;
        }

        public Builder setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
            callback = onDateSelectedListener;
            return this;
        }

        private boolean shadow = true;

        //设置dialog的形状
        public DatePickerDialog create() {
            final DatePickerDialog dialog = new DatePickerDialog(context, shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);

            View view1 = View.inflate(context, R.layout.loyout_picker_screen, null);
            bu_screen_zhifubao = (TextView) view1.findViewById(R.id.bu_screen_zhifubao);
            bu_screen_weixin = (TextView) view1.findViewById(R.id.bu_screen_weixin);
            bu_screen_jingdong = (TextView) view1.findViewById(R.id.bu_screen_jingdong);
            bu_screen_yinlian = (TextView) view1.findViewById(R.id.bu_screen_yinlian);
            bu_screen_yizhifu = (TextView) view1.findViewById(R.id.bu_screen_yizhifu);
            bu_screen_chenggong = (TextView) view1.findViewById(R.id.bu_screen_chenggong);
            bu_screen_weizhifu = (TextView) view1.findViewById(R.id.bu_screen_weizhifu);
            bu_screen_kuaijiea = (TextView) view1.findViewById(R.id.bu_screen_kuaijiea);

            selectState();

            view1.findViewById(R.id.tx_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    callback.onDateSelected(getCurrDateValues(),getStateDateValues());//点击确定后要返回的信息
                }
            });

            view1.findViewById(R.id.bu_reset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    dialog.dismiss();
                    bu_screen_zhifubao.setSelected(false);
                    bu_screen_weixin.setSelected(false);
                    bu_screen_jingdong.setSelected(false);
                    bu_screen_yinlian.setSelected(false);
                    bu_screen_yizhifu.setSelected(false);
                    bu_screen_chenggong.setSelected(false);
                    bu_screen_weizhifu.setSelected(false);
                    bu_screen_kuaijiea.setSelected(false);
                }
            });

            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            win.setGravity(Gravity.BOTTOM);
            win.setWindowAnimations(R.style.Animation_Bottom_Rising);

            dialog.setContentView(view1);
            dialog.setCanceledOnTouchOutside(true);//设置点击窗口外面也可以取消
            dialog.setCancelable(true);//设置窗口是否可以撤销

            return dialog;
        }


        private void selectState() {
            bu_screen_zhifubao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bu_screen_zhifubao.isSelected()) {
                        bu_screen_zhifubao.setSelected(false);
                    } else {
                        bu_screen_zhifubao.setSelected(true);
                    }
                }
            });

            bu_screen_weixin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bu_screen_weixin.isSelected()) {
                        bu_screen_weixin.setSelected(false);
                    } else {
                        bu_screen_weixin.setSelected(true);
                    }
                }
            });

            bu_screen_jingdong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bu_screen_jingdong.isSelected()) {
                        bu_screen_jingdong.setSelected(false);
                    } else {
                        bu_screen_jingdong.setSelected(true);
                    }
                }
            });

            bu_screen_yinlian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bu_screen_yinlian.isSelected()) {
                        bu_screen_yinlian.setSelected(false);
                    } else {
                        bu_screen_yinlian.setSelected(true);
                    }
                }
            });

            bu_screen_yizhifu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bu_screen_yizhifu.isSelected()) {
                        bu_screen_yizhifu.setSelected(false);
                    } else {
                        bu_screen_yizhifu.setSelected(true);
                    }
                }
            });

            bu_screen_chenggong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bu_screen_chenggong.isSelected()) {
                        bu_screen_chenggong.setSelected(false);
                    } else {
                        bu_screen_chenggong.setSelected(true);
                    }
                }
            });

            bu_screen_weizhifu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bu_screen_weizhifu.isSelected()) {
                        bu_screen_weizhifu.setSelected(false);
                    } else {
                        bu_screen_weizhifu.setSelected(true);
                    }
                }
            });

            bu_screen_kuaijiea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bu_screen_kuaijiea.isSelected()) {
                        bu_screen_kuaijiea.setSelected(false);
                    } else {
                        bu_screen_kuaijiea.setSelected(true);
                    }
                }
            });
        }

    }

}
