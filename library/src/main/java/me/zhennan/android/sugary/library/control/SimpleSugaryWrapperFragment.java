package me.zhennan.android.sugary.library.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import me.zhennan.android.sugary.library.R;

/**
 * Created by zhangzhennan on 14/6/19.
 */
public class SimpleSugaryWrapperFragment extends SugaryFragment implements ISugaryPageWrapper {

    public SimpleSugaryWrapperFragment(){
        setup(R.layout.page_simple_wrapper);
    }

    // ---------------------------------------------------------------
    // - ISugaryPage Interface
    // ---------------------------------------------------------------

    /**
     * not implement pass Bundle back
     * @param results
     */
    @Override
    final  public void prevPage(Bundle results) {
        getChildFragmentManager().popBackStack();
    }

    @Override
    final public void nextPage(ISugaryPage page, Bundle arguments) {

        int fragmentContainerId = R.id.sugaryPageContainer;

        try {
            if(page instanceof Fragment){
                Fragment fragment = (Fragment)page;
                fragment.setArguments(arguments);
                getChildFragmentManager().beginTransaction()
                        .replace(fragmentContainerId, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    final public void firstPage(ISugaryPage page, Bundle arguments) {

        while (0 < getChildFragmentManager().getBackStackEntryCount()){
            getChildFragmentManager().popBackStackImmediate();
        }

        int fragmentContainerId = R.id.sugaryPageContainer;
        if(null != page){
            try {
                if(page instanceof Fragment){
                    Fragment fragment = (Fragment)page;
                    fragment.setArguments(arguments);
                    getChildFragmentManager().beginTransaction()
                            .replace(fragmentContainerId, fragment)
                            .commit();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private ISugaryPage getChildByRequestCode(int requestCode){
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for(Fragment fragment : fragments){
            if(fragment instanceof ISugaryPage){
                if(((ISugaryPage)fragment).validRequestCode(requestCode)){
                    return (ISugaryPage)fragment;
                }
            }

            if(fragment instanceof ISugaryPageWrapper){
                if(((ISugaryPageWrapper)fragment).validChildRequestCode(requestCode)) {
                    return (ISugaryPageWrapper) fragment;
                }
            }
        }
        return null;
    }

    final public boolean validChildRequestCode(int requestCode){
        return null != getChildByRequestCode(requestCode);
    }

    @Override
    final public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(validRequestCode(requestCode)){
            super.onActivityResult(requestCode, resultCode, data);
        }else if(validChildRequestCode(requestCode)){
            ISugaryPage page = getChildByRequestCode(requestCode);
            if(page instanceof Fragment){
                ((Fragment) page).onActivityResult(requestCode, resultCode, data);
            }
        }

    }

}
