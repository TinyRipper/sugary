package me.zhennan.android.sugary.library.utils;

import android.os.Build;

/**
 * Created by zhangzhennan on 14/6/19.
 */
public class InvokeHelper {

    private boolean isInvoked = false;


    public InvokeHelper version(int version, OnVersionInvoke invoke){

        if(null != invoke && !isInvoked && Build.VERSION.SDK_INT == version){
            invoke.invoke();
            isInvoked = true;
        }
        return this;
    }

    public InvokeHelper version(Object ... versionAndInvoke){

        if(null != versionAndInvoke && !isInvoked){

            int size = versionAndInvoke.length;
            for(int i = 0; i < size && i + 1 < size; i+=2){

                Integer version = (Integer)versionAndInvoke[i];
                OnVersionInvoke invoke = (OnVersionInvoke)versionAndInvoke[i + 1];

                if(null != version && null != invoke && !isInvoked){
                    version(version, invoke);
                }

                if(isInvoked){
                    break;
                }
            }
        }

        return this;
    }

    public InvokeHelper versionGreaterThan(int version, OnVersionInvoke invoke){

        if(null != invoke && !isInvoked && Build.VERSION.SDK_INT < version){
            invoke.invoke();
            isInvoked = true;
        }
        return this;
    }

    public InvokeHelper versionLessThan(int version, OnVersionInvoke invoke){

        if(null != invoke && !isInvoked && Build.VERSION.SDK_INT > version){
            invoke.invoke();
            isInvoked = true;
        }

        return this;
    }

    public InvokeHelper otherVersion(OnVersionInvoke invoke){

        if(null != invoke && !isInvoked){
            invoke.invoke();
            isInvoked = true;
        }
        return this;
    }

    static public InvokeHelper invokeIfVersion(int version, OnVersionInvoke invoke){

        return new InvokeHelper().version(version, invoke);
    }

    static public InvokeHelper invokeIfVersionGreaterThan(int version, OnVersionInvoke invoke){
        return new InvokeHelper().versionGreaterThan(version, invoke);
    }

    static public InvokeHelper invokeIfVersionLessThan(int version, OnVersionInvoke invoke){
        return new InvokeHelper().versionLessThan(version, invoke);
    }

    static interface OnVersionInvoke{
        void invoke();
    }
}
