package me.zhennan.android.sugary.library.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import me.zhennan.android.sugary.library.R;

/**
 * Created by zhangzhennan on 14/6/19.
 */
public class SimpleSugaryWrapperActivity extends ActionBarActivity implements ISugaryPageWrapper{

    public SimpleSugaryWrapperActivity() {
        setup(R.layout.activity_simple_wrapper);
    }

    protected void setup(int layoutResId){
        this.layoutResId = layoutResId;
    }

    protected void setup(int layoutResId, int menuResId){
        this.layoutResId = layoutResId;
        setMenuResId(menuResId);
    }

    protected void setup(int layoutResId, OnPrepareOptionMenuListener listener){
        this.layoutResId = layoutResId;
        setOnPrepareOptionMenuListener(listener);
    }

    // -------------------------------------------------------------------------------------
    // - Layout sugar method
    // -------------------------------------------------------------------------------------

    private Integer layoutResId;

    /**
     * if not use default layout, you must override this method to return custom fragmentContainerId in your layout file.
     * @return
     */
    protected int getFragmentContainerId(){

        if(null == layoutResId || layoutResId == R.layout.activity_simple_wrapper){
            return R.id.sugaryPageContainer;
        }else{
            return 0;
        }
    }

    protected void viewSetVisible(int viewId, int visibility){
        View view = findViewById(viewId);
        viewSetVisible(view, visibility);
    }

    protected void viewSetVisible(View view, int visibility){
        if(null != view) {
            view.setVisibility(visibility);
        }
    }

    /**
     * sugar method: view set onClickListener
     * #WARNING# DO NOT CALL THIS METHOD BEFORE onViewCreated CALLED
     * @param viewId view id
     * @param listener on click listener
     */
    protected void viewOnClick(int viewId, View.OnClickListener listener){
        View view = findViewById(viewId);
        viewOnClick(view, listener);
    }

    /**
     * sugar method: view set onClickListener
     * #WARNING# DO NOT CALL THIS METHOD BEFORE onViewCreated CALLED
     * @param view view instance
     * @param listener on click listener
     */
    protected void viewOnClick(View view, View.OnClickListener listener){
        if(null != view){
            view.setOnClickListener(listener);
        }
    }

    /**
     * sugar method: edit text set onEditorActionListener
     * #WARNING# DO NOT CALL THIS METHOD BEFORE onViewCreated CALLED
     * @param editTextId
     * @param listener
     */
    protected void editTextOnEnter(int editTextId, TextView.OnEditorActionListener listener){
        View view = findViewById(editTextId);
        if(view instanceof EditText){
            editTextOnEnter((EditText)view, listener);
        }
    }

    protected void editTextOnEnter(EditText editText, TextView.OnEditorActionListener listener){
        if(null != editText){
            editText.setOnEditorActionListener(listener);
        }
    }

    /**
     * sugar method: set text to textView;
     * #WARNING# DO NOT CALL THIS METHOD BEFORE onViewCreated CALLED
     * @param textViewId
     * @param text
     */
    protected void textViewSetText(int textViewId, String text){
        View view = findViewById(textViewId);
        if(view instanceof TextView){
            textViewSetText((TextView)view, text);
        }
    }

    protected void textViewSetText(TextView textView, String text){
        if(null != textView){
            textView.setText(text);
        }
    }

    /**
     * show popup dialog to block ue flow
     * @param title
     * @param message
     */
    protected void showPopup(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .create().show();
    }

    protected void showPopup(int titleResId, int messageResId, int okButtonResId, DialogInterface.OnClickListener okListener, int cancelButtonResId, DialogInterface.OnClickListener cancelListener){
        Resources res = getResources();
        showPopup(res.getString(titleResId), res.getString(messageResId), res.getString(okButtonResId), okListener, res.getString(cancelButtonResId), cancelListener);
    }

    protected void showPopup(String title, String message, String okButton, DialogInterface.OnClickListener okListener, String cancelButton, DialogInterface.OnClickListener cancelListener){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(okButton, okListener)
                .setNegativeButton(cancelButton, cancelListener)
                .create().show();
    }

    private ProgressDialog progressDialog;

    /**
     * show progress dialog to block ue flow
     * @param title
     * @param message
     */
    protected void showProgress(String title, String message){
        if(null == progressDialog){
            progressDialog = new ProgressDialog(this);
        }

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void showProgress(int titleResId, int messageResId){
        Resources res = getResources();
        showProgress(res.getString(titleResId), res.getString(messageResId));
    }

    protected void hideProgress(){
        if(null != progressDialog){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // -------------------------------------------------------------------------------------
    // - Menu action sugar method
    // -------------------------------------------------------------------------------------

    private Integer menuResId;
    protected void setMenuResId(Integer menuResId){
        this.menuResId = menuResId;

        if(null != getWindow()) {
            supportInvalidateOptionsMenu();
        }
    }

    private OnPrepareOptionMenuListener onPrepareOptionMenuListener;
    private void setOnPrepareOptionMenuListener(OnPrepareOptionMenuListener onPrepareOptionMenuListener){
        this.onPrepareOptionMenuListener = onPrepareOptionMenuListener;

        if(null != getWindow()) {
            supportInvalidateOptionsMenu();
        }
    }

    private SparseArray<OnOptionMenuActionListener> menuActionListenerMap;
    private SparseArray<OnOptionMenuActionListener> getMenuActionListenerMap(){
        if(null == menuActionListenerMap){
            menuActionListenerMap = new SparseArray<OnOptionMenuActionListener>();
        }
        return menuActionListenerMap;
    }

    private void setMenuActionListenerMap(SparseArray<OnOptionMenuActionListener> menuActionListenerMap){
        this.menuActionListenerMap = menuActionListenerMap;
    }

    /**
     * register menu item callback listener with menuItemId
     * @param menuItemId
     * @param listener
     */
    protected void registerMenuItemAction(int menuItemId, OnOptionMenuActionListener listener){
        if(null != listener){
            getMenuActionListenerMap().append(menuItemId, listener);
        }else{
            unregisterMenuItemAction(menuItemId);
        }
    }

    /**
     * register menu item action
     * @param menuIdAndListeners Integer, OnOptionMenuActionListener 1 by 1;
     */
    protected void registerMenuItemAction(Object ... menuIdAndListeners){
        int size = menuIdAndListeners.length;
        for(int i = 0; i < size && i + 1 < size; i+=2){
            if(menuIdAndListeners[i] instanceof Integer && menuIdAndListeners[i + 1] instanceof OnOptionMenuActionListener) {
                Integer menuResId = (Integer) menuIdAndListeners[i];
                OnOptionMenuActionListener listener = (OnOptionMenuActionListener) menuIdAndListeners[i + 1];
                registerMenuItemAction(menuResId, listener);
            }
        }
    }

    /**
     * unregister menu item action
     * @param menuId
     */
    protected void unregisterMenuItemAction(int menuId){
        getMenuActionListenerMap().remove(menuId);
    }

    protected void unregisterMenuItemAll(){
        getMenuActionListenerMap().clear();
    }

    @Override
    final public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(null != onPrepareOptionMenuListener){
            onPrepareOptionMenuListener.onPrepareOptionMenu(getMenuInflater(), menu);
            return true;
        }else{
            return false;
        }
    }

    @Override
    final public boolean onCreateOptionsMenu(Menu menu) {
        if(null == onPrepareOptionMenuListener && null != menuResId){
            getMenuInflater().inflate(menuResId, menu);
            return true;
        }else{
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    final public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(-1 < getMenuActionListenerMap().indexOfKey(id)){
            OnOptionMenuActionListener listener = getMenuActionListenerMap().get(id);
            listener.onOptionMenuAction(item);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    protected interface OnPrepareOptionMenuListener extends Serializable {
        void onPrepareOptionMenu(MenuInflater inflater, Menu menu);
    }

    protected interface OnOptionMenuActionListener extends Serializable {
        void onOptionMenuAction(MenuItem menuItem);
    }

    // -------------------------------------------------------------------------------------
    // - state
    // -------------------------------------------------------------------------------------

    private FragmentManager.OnBackStackChangedListener backStackChangeListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if(-1 < getPrevPageArguments().indexOfKey(count)){
                Bundle result = getPrevPageArguments().get(count);
                getPrevPageArguments().remove(count);
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for(Fragment fragment : fragments){
                    if(fragment instanceof ISugaryPage){
                        ((ISugaryPage) fragment).onPageReturn(result);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(null != layoutResId){
            setContentView(layoutResId);
        }else{
            setContentView(R.layout.activity_simple_wrapper);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(backStackChangeListener);

    }

    @Override
    public void onDestroy() {
        // destroy menu item action listener map;
        this.setMenuActionListenerMap(null);
        // destroy menu create listener;
        this.setOnPrepareOptionMenuListener(null);

        getSupportFragmentManager().removeOnBackStackChangedListener(backStackChangeListener);

        super.onDestroy();
    }

    // ---------------------------------------------------------------
    // - ISugaryPage Interface
    // ---------------------------------------------------------------

    @Override
    final public ISugaryPageWrapper getWrapper() {
        return null;
    }

    /**
     * goto next page when this fragment have ISugaryPageWrapper parent
     * @param pageClass
     * @see me.zhennan.android.sugary.library.control.ISugaryPageWrapper
     */
    @Override
    final public void nextPage(Class<? extends ISugaryPage> pageClass) {
        nextPage(pageClass, null);
    }

    @Override
    public void nextPage(Class<? extends ISugaryPage> pageClass, Bundle arguments) {
        int fragmentContainerId = getFragmentContainerId();

        ISugaryPage page = (ISugaryPage)Fragment.instantiate(this, pageClass.getName(), arguments);
        if(null != page){
            Fragment fragment = (Fragment)page;
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(fragmentContainerId, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private SparseArray<Bundle> prevPageArguments;
    protected SparseArray<Bundle> getPrevPageArguments(){
        if(null == prevPageArguments){
            prevPageArguments = new SparseArray<Bundle>();
        }
        return prevPageArguments;
    }

    /**
     * goto prev page when this fragment have ISugaryPageWrapper parent
     * @see me.zhennan.android.sugary.library.control.ISugaryPageWrapper
     */
    @Override
    final public void prevPage() {
        prevPage(null);
    }

    /**
     * not implement pass Bundle back
     * @param results
     */
    @Override
    final  public void prevPage(Bundle results) {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if( 0 < count){
            if(null != results){
                getPrevPageArguments().put(count - 1, results);
            }
            getSupportFragmentManager().popBackStack();
        }else if(null == results){
            pageCancel();
        }else{
            pageSuccess(results);
        }
    }

    /**
     * goto first of the page stack when this fragment have ISugaryPageWrapper parent
     * @see me.zhennan.android.sugary.library.control.ISugaryPageWrapper
     */
    @Override
    final public void firstPage() {
        firstPage(null, null);
    }

    /**
     * goto first of the page stack and replace the first one  when this fragment have ISugaryPageWrapper parent
     * @see me.zhennan.android.sugary.library.control.ISugaryPageWrapper
     */
    @Override
    final public void firstPage(Class<? extends ISugaryPage> pageClass) {
        firstPage(pageClass, null);
    }

    @Override
    public void firstPage(Class<? extends ISugaryPage> pageClass, Bundle arguments) {
        while (0 < getSupportFragmentManager().getBackStackEntryCount()){
            getSupportFragmentManager().popBackStackImmediate();
        }

        int fragmentContainerId = getFragmentContainerId();
        ISugaryPage page = (ISugaryPage)Fragment.instantiate(this, pageClass.getName(), arguments);
        if(null != page){
            Fragment fragment = (Fragment)page;
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(fragmentContainerId, fragment)
                    .commit();
        }
    }

    @Override
    final public void newPage(Intent intent) {
        newPage(intent, null);
    }

    /**
     * start Activity with callback when the started Activity set result and finished
     * @param intent intent
     * @param pageResponseListener callback listener
     */
    @Override
    public void newPage(Intent intent, OnNewPageResponse pageResponseListener) {

        final int MAXIMUM_REQUEST_CODE = 0xffff;
        if(null != pageResponseListener){

            int requestCode = getRequestCallbackMap().size();
            requestCode |= this.hashCode();
            requestCode &= MAXIMUM_REQUEST_CODE;

            getRequestCallbackMap().append(requestCode, pageResponseListener);
            startActivityForResult(intent, requestCode);
        }else{
            startActivity(intent);
        }
    }

    private SparseArray<OnNewPageResponse> requestCallbackMap;
    private SparseArray<OnNewPageResponse> getRequestCallbackMap(){
        if(null == requestCallbackMap){
            requestCallbackMap = new SparseArray<OnNewPageResponse>();
        }
        return requestCallbackMap;
    }

    @Override
    final public boolean validRequestCode(int requestCode) {
        return -1 < getRequestCallbackMap().indexOfKey(requestCode);
    }

    private ISugaryPage getChildByRequestCode(int requestCode){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
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

    @Override
    public boolean validChildRequestCode(int requestCode) {
        return null != getChildByRequestCode(requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        final int MASK_DIGITAL = 16;
        final int REAL_REQUEST_DIGITAL = 0xffff;

        int mask = requestCode >> MASK_DIGITAL;
        int realRequestCode = requestCode & REAL_REQUEST_DIGITAL;

        // current level
        if(0 == mask){

            if(validRequestCode(realRequestCode)) {
                OnNewPageResponse response = getRequestCallbackMap().get(realRequestCode);
                getRequestCallbackMap().remove(realRequestCode);

                response.onNewPageUnknown(resultCode, data);
            }else{
                super.onActivityResult(requestCode, resultCode, data);
            }

        }
        // child level
        else if(validChildRequestCode(realRequestCode)){

            ISugaryPage page = getChildByRequestCode(realRequestCode);
            if(page instanceof Fragment){
                ((Fragment)page).onActivityResult(realRequestCode, resultCode, data);
            }
        }
        // no one respond request code
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    /**
     *  pageClose activity with Activity.RESULT_OK result code.
     */
    @Override
    final public void pageSuccess() {
        pageSuccess(null);
    }

    @Override
    public void pageSuccess(Bundle response) {
        if(null != response){
            Intent intent = new Intent();
            intent.putExtras(response);
            setResult(Activity.RESULT_OK, intent);
        }else{
            setResult(Activity.RESULT_OK);
        }
        finish();
    }

    /**
     *  pageClose activity with Activity.RESULT_CANCELED result code.
     */
    @Override
    final public void pageCancel() {
        pageCancel(null);
    }

    @Override
    public void pageCancel(Bundle response) {
        if(null != response){
            Intent intent = new Intent();
            intent.putExtras(response);
            setResult(Activity.RESULT_CANCELED, intent);
        }else{
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }

    /**
     *  pageClose activity with custom result code.
     */
    @Override
    final public void pageClose(int resultCode, Bundle response) {
        if(null != response){
            Intent intent = new Intent();
            intent.putExtras(response);
            setResult(resultCode, intent);
        }else{
            setResult(resultCode);
        }

        finish();
    }

    @Override
    public void pageReturn(Bundle response) {
        if(null != getWrapper()){
            prevPage(response);
        }else{
            pageSuccess(response);
        }
    }

    @Override
    public void onPageReturn(Bundle response) {

    }

    // post delay
    public void post(Runnable action){
        Handler handler = new Handler();
        handler.post(action);
    }

    public void postDelayed(Runnable action, long delayMillis){
        Handler handler = new Handler();
        handler.postDelayed(action, delayMillis);
    }
}
