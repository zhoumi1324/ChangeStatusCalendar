package com.mige.changestatuscalendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dsw.calendar.component.MonthView;
import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.theme.DefaultDayTheme;
import com.dsw.calendar.views.GridCalendarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int y ,m ,d ;
    private GridCalendarView gridCalendarView;
    private boolean isContain = false;
    private List<CalendarInfo> calendarInfos = new ArrayList<CalendarInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        gridCalendarView = findViewById(R.id.calendar_view);
        calendarInfos.add(new CalendarInfo(2018 ,7 ,26 ,"不可用" ,1));
        calendarInfos.add(new CalendarInfo(2018 ,7 ,3 ,"已订" , 2));
        calendarInfos.add(new CalendarInfo(2018 ,7 ,4 ,"维修",0));
        calendarInfos.add(new CalendarInfo(2018 ,7 ,9 ,"已订" ,2));
        calendarInfos.add(new CalendarInfo(2018 ,7 ,10 ,"已订" ,2));
        calendarInfos.add(new CalendarInfo(2018 ,7 ,12 ,"不可用" , 1));
        calendarInfos.add(new CalendarInfo(2018 ,7 ,22 ,"在住" ,0));
        gridCalendarView.setCalendarInfos(calendarInfos);

        gridCalendarView.setDayTheme(new DefaultDayTheme());
        gridCalendarView.setDateClick(new MonthView.IDateClick(){

            @Override
            public void onClickOnDate(int year, int month, int day ) {
                if(day!=0){
                    y= year;
                    m = month;
                    d = day;
                    String date = year + "-" + month + "-" + day;
                    Toast.makeText(MainActivity.this, date ,Toast.LENGTH_SHORT).show();
                    showSingleChoiceDialog();
                }
            }
        });
    }

    int yourChoice;
    private void showSingleChoiceDialog(){
        final String[] items = { "不可用","已订","维修","在住" };
        yourChoice = 0;
        final AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle("选择房间状态");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (yourChoice != -1) {
//                            Toast.makeText(MainActivity.this,
//                                    "你选择了" + items[yourChoice],
//                                    Toast.LENGTH_SHORT).show();
//                        }
                        String desc = "";
                        int rest = 1;
                        switch (yourChoice){
                            case 0:
                                desc = "不可用";
                                rest = 1;
                                break;
                            case 1:
                                desc = "已订";
                                rest = 2;
                                break;
                            case 2:
                                desc = "维修";
                                rest = 0;
                                break;
                            case 3:
                                desc = "在住";
                                rest = 0;
                                break;
                        }
                        for (int i = 0 ;i<calendarInfos.size();i++){
                            CalendarInfo calendarInfo = calendarInfos.get(i);
                            if(calendarInfo.year == y&&calendarInfo.month == m&&calendarInfo.day == d){
                                isContain = true;

                                calendarInfo.des = desc;
                                calendarInfo.rest = rest;
                                break;
                            }
                        }
                        if(!isContain){
                            calendarInfos.add(new CalendarInfo(y ,m ,d ,desc ,rest));
                        }
                        gridCalendarView.setCalendarInfos(calendarInfos);
                        isContain = false;
                    }
                });
        singleChoiceDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
        singleChoiceDialog.show();
    }

}
