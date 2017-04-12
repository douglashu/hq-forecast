package com.hq.app.olaf.net;


import android.view.View;

import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;

/**
 * Created by Zale on 2017/3/26.
 */

public abstract class HqNetCallBack extends NetTools.CallBack {
    private View mRootView;

    public HqNetCallBack(View mRootView) {
        this.mRootView = mRootView;
    }

    @Override
    public void onComplete(TaskResult taskResult) {
        if(mRootView!=null && !RequestFailedHandler.handleFailed(mRootView,taskResult)) {
            doComplete(taskResult);
        }else {
            dofinally(taskResult);
        }
    }

    protected  void dofinally(TaskResult taskResult){
        //if need do something when error
    }

    protected  abstract void doComplete(TaskResult taskResult);

}
