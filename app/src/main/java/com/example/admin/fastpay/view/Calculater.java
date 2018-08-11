package com.example.admin.fastpay.view;

import android.content.Context;
import android.content.SharedPreferences;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.fastpay.R;
import com.zxz.www.base.utils.StringUtil;


/**
 * Created by sun on 2017/5/5.
 * 自定义计算器
 */

public class Calculater extends LinearLayout {

    private ImageButton ib_number1;
    private ImageButton ib_number2;
    private ImageButton ib_number3;
    private ImageButton ib_number4;
    private ImageButton ib_number5;
    private ImageButton ib_number6;
    private ImageButton ib_number7;
    private ImageButton ib_number8;
    private ImageButton ib_number9;
    private ImageButton ib_number0;

    private ImageButton ib_shanchu;
    private ImageButton ib_xiaoshu;

    private EditText tv_show;
    private Toast toast;

    public static StringBuffer sumtext;

    public Calculater(Context context) {
        this(context,null);
    }

    public Calculater(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Calculater(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.calculater,this);

        init();
    }

    private void init() {

        toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER |Gravity.LEFT, 0, 0);
        sumtext = new StringBuffer();

        ib_number1 = (ImageButton) findViewById(R.id.ib_number1);
        ib_number2 = (ImageButton) findViewById(R.id.ib_number2);
        ib_number3 = (ImageButton) findViewById(R.id.ib_number3);
        ib_number4 = (ImageButton) findViewById(R.id.ib_number4);
        ib_number5 = (ImageButton) findViewById(R.id.ib_number5);
        ib_number6 = (ImageButton) findViewById(R.id.ib_number6);
        ib_number7 = (ImageButton) findViewById(R.id.ib_number7);
        ib_number8 = (ImageButton) findViewById(R.id.ib_number8);
        ib_number9 = (ImageButton) findViewById(R.id.ib_number9);
        ib_number0 = (ImageButton) findViewById(R.id.ib_number0);

        ib_number1.setOnClickListener(oc);
        ib_number2.setOnClickListener(oc);
        ib_number3.setOnClickListener(oc);
        ib_number4.setOnClickListener(oc);
        ib_number5.setOnClickListener(oc);
        ib_number6.setOnClickListener(oc);
        ib_number7.setOnClickListener(oc);
        ib_number8.setOnClickListener(oc);
        ib_number9.setOnClickListener(oc);
        ib_number0.setOnClickListener(oc);

        ib_shanchu = (ImageButton) findViewById(R.id.ib_shanchu);
        ib_xiaoshu = (ImageButton) findViewById(R.id.ib_xiaoshu);

        ib_shanchu.setOnClickListener(oc);
        ib_xiaoshu.setOnClickListener(oc);

        tv_show = (EditText) findViewById(R.id.tv_show);

//        EditTextTools editTextTools= new EditTextTools(tv_show,10,2);

        showLimit();//设置显示框
    }

    public String getAmount() {
        return amount;
    }

    private String amount;

    private void showLimit() {

        tv_show.addTextChangedListener(new TextWatcher() {

            //在文本被改变中调用
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtil.isEmpty(s) && s.toString().contains(".") && s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                    tv_show.setText(s);
                    tv_show.setSelection(s.length());
                    //如果在点击数字的，保存字符串的stringbuffer也要-1
                    sumtext.deleteCharAt(sumtext.length() - 1);
                    tv_show.setText(sumtext);
                }

            }
            //在文本被改变之前调用
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null) {
                    return;
                }
                amount = String.valueOf(sumtext);
                // 设置当第一个为.时显示的不变，点击别的改变
                if (s.toString().startsWith(".")) {
                    tv_show.setText("0.00");
                    sumtext.delete(0,sumtext.length());

                }

                String sumtextStr = sumtext.toString();
                //第一个字为0时，后面的数字若不为小数点，则当前只显示第二个字
                if(sumtextStr.length()>1&&sumtextStr.startsWith("0")) {
                    char c = sumtextStr.charAt(1);
                    if(!String.valueOf(c).equals(".")){
                        sumtext.deleteCharAt(0);
                        tv_show.setText(sumtext);
                        return;
                    }
                }

                if(sumtextStr.equals("0.00")) {
                    sumtext.deleteCharAt(3);
                    tv_show.setText(sumtext);
                    return;
                }

                if (sumtext.toString().startsWith("0") && sumtext.toString().length() > 1) {
                    if(sumtext.toString().substring(1,2).equals("0")){
                        sumtext.deleteCharAt(sumtext.length() - 1);
                        tv_show.setText(sumtext);
                    }
                }
            }

        });
    }

    MyOnclickListener oc = new MyOnclickListener();

    protected class MyOnclickListener implements OnClickListener {

        private StringBuffer showtext;

        public MyOnclickListener() {
            showtext = new StringBuffer();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_number1:
                    showtext.append("1");//进行拼接并放在尾部，返回
                    show();
                    break;
                case R.id.ib_number2:
                    showtext.append("2");
                    show();
                    break;
                case R.id.ib_number3:
                    showtext.append("3");
                    show();
                    break;
                case R.id.ib_number4:
                    showtext.append("4");
                    show();
                    break;
                case R.id.ib_number5:
                    showtext.append("5");
                    show();
                    break;
                case R.id.ib_number6:
                    showtext.append("6");
                    show();
                    break;
                case R.id.ib_number7:
                    showtext.append("7");
                    show();
                    break;
                case R.id.ib_number8:
                    showtext.append("8");
                    show();
                    break;
                case R.id.ib_number9:
                    showtext.append("9");
                    show();
                    break;
                case R.id.ib_number0:
                    showtext.append("0");
                    show();
                    break;
                case R.id.ib_shanchu:
                    back();
                    break;
                case R.id.ib_xiaoshu:
                    //如果存在小数点就直接结束，如果没有就接着走
                    if(sumtext.toString().indexOf(".") > -1){
                        break;
                    }
                    showtext.append(".");
                    show();
                    break;
            }
        }

        private void back() {
            if (sumtext.length() == 1){
                sumtext.deleteCharAt(sumtext.length() - 1);
                tv_show.setText("0.00");
            }else if(sumtext.length() == 0){

            } else {
                sumtext.deleteCharAt(sumtext.length() - 1);
                tv_show.setText(sumtext);
            }
        }

        private void show() {
            sumtext.append(showtext.toString());
            tv_show.setText(sumtext);
            showtext.delete(0, showtext.length());

        }
    }

}
