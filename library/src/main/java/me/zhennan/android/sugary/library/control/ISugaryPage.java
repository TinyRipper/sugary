package me.zhennan.android.sugary.library.control;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by zhangzhennan on 14/6/19.
 */
public interface ISugaryPage extends Serializable{

    public boolean validRequestCode(int requestCode);

    /**
     * get the page wrapper
     * @return
     */
    public ISugaryPageWrapper getWrapper();

    /**
     * goto next of navigation stack
     * @param page
     */
    public void nextPage(Class<ISugaryPage> page);
    public void nextPage(Class<ISugaryPage> page, Bundle arguments);

    /**
     * goto prev of navigation stack
     */
    public void prevPage();
    public void prevPage(Bundle results);

    /**
     * goto the top of navigation stack
     */
    public void firstPage();
    public void firstPage(Class<ISugaryPage> page);
    public void firstPage(Class<ISugaryPage> page, Bundle arguments);

    /**
     * create new stack under current stack
     * @param intent
     */
    public void newPage(Intent intent);
    public void newPage(Intent intent, OnNewPageResponse pageResponseListener);

    /**
     * success
     */
    public void success();
    public void success(Bundle response);

    /**
     * cancel
     */
    public void cancel();
    public void cancel(Bundle response);

    public void close(int resultCode, Bundle response);

    public interface OnNewPageResponse{
        void onNewPageComplete(Bundle extras);
        void onNewPageCanceled(Bundle extras);
        void onNewPageFirstUser(Bundle extras);
        void onNewPageUnknown(Bundle extras);
    }
}
