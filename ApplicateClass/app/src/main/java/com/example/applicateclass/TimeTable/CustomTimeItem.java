package com.example.applicateclass.TimeTable;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.applicateclass.R;

public class CustomTimeItem extends LinearLayout {
    private Boolean         isFull= false;
    private TextView        tvTitle;
    private TextView        tvSub;
    private ImageView       ivDivideLine;
    private LinearLayout    background;
    private CustomScheduleItem scheduleItem;
    private CustomTimeTable timeTable;

    public CustomTimeItem(Context context) {
        super(context);
        initView();
    }
    public CustomTimeItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }
    public CustomTimeItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_tableitem, this, false);
        addView(v);

        tvTitle = (TextView) v.findViewById(R.id.tableItem_title);
        tvSub = (TextView) v.findViewById(R.id.tableItem_sub);
        ivDivideLine = (ImageView)v.findViewById(R.id.tableItem_divide);
        background = (LinearLayout)v.findViewById(R.id.tableItem_background);
        isFull = false;

        background.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder oDialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog);

                oDialog.setMessage("앱을 종료하시겠습니까?")
                        .setTitle("일반 Dialog")
                        .setNeutralButton("삭제", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                timeTable.removeSchedule(scheduleItem);
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("닫기", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.


                        .show();
                return true;
            }
        });

    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TableItem);
        setTypeArray(typedArray);
    }
    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TableItem, defStyle, 0);
        setTypeArray(typedArray);
    }
    private void setTypeArray(TypedArray typedArray) {

        String titleText = typedArray.getString(R.styleable.TableItem_titleText);
        tvTitle.setText(titleText);
        String subText = typedArray.getString(R.styleable.TableItem_titleText);
        tvSub.setText(subText);

        boolean visible=true;
        visible = typedArray.getBoolean(R.styleable.TableItem_dividerSize,visible);

        if(!visible)
            ivDivideLine.setVisibility(View.GONE);
        else
            ivDivideLine.setVisibility(View.VISIBLE);

        typedArray.recycle();

    }

    public void setTableItem(String title, String sub, boolean visible ,int color,boolean isFull,CustomScheduleItem scheduleItem){
        tvTitle.setText(title);
        tvSub.setText(sub);

        if(!visible)
            ivDivideLine.setVisibility(View.GONE);
        else
            ivDivideLine.setVisibility(View.VISIBLE);
        background.setBackgroundColor(color);
        this.isFull = isFull;
        this.scheduleItem = scheduleItem;
    }

    public void removeItem(){
        tvTitle.setText("");
        tvSub.setText("");
        ivDivideLine.setVisibility(View.VISIBLE);
        background.setBackgroundColor(getResources().getColor(R.color.timetableBack));
        isFull = false;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setIvDivideLine(boolean visible) {
        if(!visible)
            ivDivideLine.setVisibility(View.GONE);
        else
            ivDivideLine.setVisibility(View.VISIBLE);
    }


    public void setScheduleItem(CustomScheduleItem scheduleItem) {
        this.scheduleItem = scheduleItem;
    }

    public CustomScheduleItem getScheduleItem() {
        return scheduleItem;
    }

    public void setTimeTable(CustomTimeTable timeTable) {
        this.timeTable = timeTable;
    }



    public void setTextColor(int color){
        tvTitle.setTextColor(color);
    }

}
