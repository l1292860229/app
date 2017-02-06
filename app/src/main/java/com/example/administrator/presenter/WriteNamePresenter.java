package com.example.administrator.presenter;

import com.example.administrator.activity.WriteNameActivity;
import com.example.administrator.interfaceview.IUWriteNameView;

/**
 * Created by Administrator on 2017/1/25.
 */

public class WriteNamePresenter {
    private WriteNameActivity context;
    private IUWriteNameView writeNameView;
    public WriteNamePresenter(WriteNameActivity context, IUWriteNameView writeNameView){
        this.context = context;
        this.writeNameView = writeNameView;
    }
    public void init(){
        String name = context.getIntent().getStringExtra("name");
        String title = context.getIntent().getStringExtra("title");
        String type=  context.getIntent().getStringExtra("type");
        boolean verify=  context.getIntent().getBooleanExtra("verify",true);
        writeNameView.init(name,title,verify);
        if(type.equals(WriteNameActivity.SINGLE)){
            writeNameView.setSingle();
        }else if(type.equals(WriteNameActivity.MULTI)){
            writeNameView.setMulti();
        }
    }
}
