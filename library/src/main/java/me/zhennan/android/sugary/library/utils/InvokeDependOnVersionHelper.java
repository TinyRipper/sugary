package me.zhennan.android.sugary.library.utils;

import android.os.Build;

/**
 * Created by zhangzhennan on 14/6/19.
 */
public class InvokeDependOnVersionHelper {

    private boolean isInvoked = false;


    public InvokeDependOnVersionHelper version(int version, OnVersionInvoke invoke){

        if(null != invoke && !isInvoked && Build.VERSION.SDK_INT == version){
            invoke.invoke();
            isInvoked = true;
        }
        return this;
    }

    public InvokeDependOnVersionHelper version(Object ... versionAndInvoke){

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

    public InvokeDependOnVersionHelper versionGreaterThan(int version, OnVersionInvoke invoke){

        if(null != invoke && !isInvoked && Build.VERSION.SDK_INT > version){
            invoke.invoke();
            isInvoked = true;
        }

        return this;
    }

    public InvokeDependOnVersionHelper versionLessThan(int version, OnVersionInvoke invoke){

        if(null != invoke && !isInvoked && Build.VERSION.SDK_INT < version){
            invoke.invoke();
            isInvoked = true;
        }
        return this;


    }

    public InvokeDependOnVersionHelper otherVersion(OnVersionInvoke invoke){

        if(null != invoke && !isInvoked){
            invoke.invoke();
            isInvoked = true;
        }
        return this;
    }

    static public InvokeDependOnVersionHelper invokeIfVersion(int version, OnVersionInvoke invoke){

        return new InvokeDependOnVersionHelper().version(version, invoke);
    }

    static public InvokeDependOnVersionHelper invokeIfVersionGreaterThan(int version, OnVersionInvoke invoke){
        return new InvokeDependOnVersionHelper().versionGreaterThan(version, invoke);
    }

    static public InvokeDependOnVersionHelper invokeIfVersionLessThan(int version, OnVersionInvoke invoke){
        return new InvokeDependOnVersionHelper().versionLessThan(version, invoke);
    }

    static public interface OnVersionInvoke{
        void invoke();
    }
}
