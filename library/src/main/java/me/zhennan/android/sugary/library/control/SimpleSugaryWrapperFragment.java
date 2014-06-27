package me.zhennan.android.sugary.library.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import me.zhennan.android.sugary.library.R;

/**
 * Created by zhangzhennan on 14/6/19.
 */
public class SimpleSugaryWrapperFragment extends SugaryFragment implements ISugaryPageWrapper {

    public SimpleSugaryWrapperFragment(){
        setup(R.layout.page_simple_wrapper);
    }

    protected int getFragmentContainerId(){
        return R.id.sugaryPageContainer;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        // the child fragment manager depend activity. suck!!
        if(null != getActivity()) {
            List<Fragment> fragments = null == getChildFragmentManager() ? null : getChildFragmentManager().getFragments();
            if (null != fragments) {
                for (Fragment fragment : fragments) {
                    fragment.setMenuVisibility(menuVisible && fragment.isVisible());
                }
            }
        }
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

        if( 0 < getChildFragmentManager().getBackStackEntryCount()){
            getChildFragmentManager().popBackStack();
        }else{
            cancel();
        }
    }

    @Override
    final public void nextPage(Class<? extends ISugaryPage> pageClass, Bundle arguments) {

        int fragmentContainerId = getFragmentContainerId();
        try {

            ISugaryPage page = (ISugaryPage)Fragment.instantiate(getActivity(), pageClass.getName(), arguments);
            if(null != page){
                Fragment fragment = ((Fragment)page);
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
    final public void firstPage(Class<? extends ISugaryPage> pageClass, Bundle arguments) {

        while (0 < getChildFragmentManager().getBackStackEntryCount()){
            getChildFragmentManager().popBackStackImmediate();
        }

        int fragmentContainerId = getFragmentContainerId();
        ISugaryPage page = (ISugaryPage)Fragment.instantiate(getActivity(), pageClass.getName(), arguments);
        if(null != page){
            try {
                Fragment fragment = (Fragment)page;
                fragment.setArguments(arguments);
                getChildFragmentManager().beginTransaction()
                        .replace(fragmentContainerId, fragment)
                        .commit();
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

    @Override
    public void success(Bundle response) {
        super.success(response);
    }

    @Override
    public void cancel(Bundle response) {
        super.cancel(response);
    }


}
