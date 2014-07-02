package me.zhennan.android.sugary.library.control;

import android.app.Activity;
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
    public void nextPage(Class<? extends ISugaryPage> page);
    public void nextPage(Class<? extends ISugaryPage> page, Bundle arguments);

    /**
     * goto prev of navigation stack
     */
    public void prevPage();
    public void prevPage(Bundle results);

    /**
     * goto the top of navigation stack
     */
    public void firstPage();
    public void firstPage(Class<? extends ISugaryPage> page);
    public void firstPage(Class<? extends ISugaryPage> page, Bundle arguments);

    /**
     * create new stack under current stack
     * @param intent
     */
    public void newPage(Intent intent);
    public void newPage(Intent intent, OnNewPageResponse pageResponseListener);

    /**
     * pageSuccess
     */
    public void pageSuccess();
    public void pageSuccess(Bundle response);

    /**
     * pageCancel
     */
    public void pageCancel();
    public void pageCancel(Bundle response);

    public void pageReturn(Bundle response);
    public void onPageReturn(Bundle response);

    public void pageClose(int resultCode, Bundle response);

    public interface OnNewPageResponse{
        void onNewPageComplete(Intent result);
        void onNewPageCanceled(Intent result);
        void onNewPageFirstUser(Intent result);
        void onNewPageUnknown(int resultCode, Intent result);
    }

    public class DefaultOnNewPageResponse implements OnNewPageResponse{
        @Override
        public void onNewPageComplete(Intent result) {

        }

        @Override
        public void onNewPageCanceled(Intent result) {

        }

        @Override
        public void onNewPageFirstUser(Intent result) {

        }

        @Override
        public void onNewPageUnknown(int resultCode, Intent result) {
            switch (resultCode){
                case Activity.RESULT_OK:
                    onNewPageComplete(result);
                    break;

                case Activity.RESULT_CANCELED:
                    onNewPageCanceled(result);
                    break;

                case Activity.RESULT_FIRST_USER:
                    onNewPageFirstUser(result);
                    break;
            }
        }
    }
}
