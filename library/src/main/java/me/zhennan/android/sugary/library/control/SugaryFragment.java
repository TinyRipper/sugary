package me.zhennan.android.sugary.library.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by zhangzhennan on 14/6/19.
 */
public class SugaryFragment extends Fragment implements ISugaryPage {

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
     * sugar method: view set onClickListener
     * #WARNING# DO NOT CALL THIS METHOD BEFORE onViewCreated CALLED
     * @param viewId view id
     * @param listener on click listener
     */
    protected void viewOnClick(int viewId, View.OnClickListener listener){
        if(null != getView()){
            View view = getView().findViewById(viewId);
            viewOnClick(view, listener);
        }
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
        if(null != getView()){
            View view = getView().findViewById(editTextId);
            if(view instanceof EditText){
                editTextOnEnter((EditText)view, listener);
            }
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
        if(null != getView()){
            View view = getView().findViewById(textViewId);
            if(view instanceof TextView){
                textViewSetText((TextView)view, text);
            }
        }
    }

    protected void textViewSetText(TextView textView, String text){
        if(null != textView){
            textView.setText(text);
        }
    }

    // -------------------------------------------------------------------------------------
    // - Menu action sugar method
    // -------------------------------------------------------------------------------------

    private Integer menuResId;
    protected void setMenuResId(Integer menuResId){
        this.menuResId = menuResId;

        setHasOptionsMenu(null != this.onPrepareOptionMenuListener || null != menuResId);
    }

    private OnPrepareOptionMenuListener onPrepareOptionMenuListener;
    private void setOnPrepareOptionMenuListener(OnPrepareOptionMenuListener onPrepareOptionMenuListener){
        this.onPrepareOptionMenuListener = onPrepareOptionMenuListener;

        setHasOptionsMenu(null != this.onPrepareOptionMenuListener || null != menuResId);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(null != layoutResId){
            return inflater.inflate(layoutResId, container, false);
        }else{
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // destroy menu item action listener map;
        this.setMenuActionListenerMap(null);
        // destroy menu create listener;
        this.setOnPrepareOptionMenuListener(null);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    final public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(null != onPrepareOptionMenuListener){
            onPrepareOptionMenuListener.onPrepareOptionMenu(getActivity().getMenuInflater(), menu);
        }
    }

    @Override
    final public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(null == onPrepareOptionMenuListener && null != menuResId){
            inflater.inflate(menuResId, menu);
        }else{
            super.onCreateOptionsMenu(menu, inflater);
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

    // ---------------------------------------------------------------
    // - ISugaryPage Interface
    // ---------------------------------------------------------------

    @Override
    public ISugaryPageWrapper getWrapper() {

        Fragment parent = getParentFragment();
        Activity activity = getActivity();
        if(null != parent && parent instanceof ISugaryPageWrapper){
            return (ISugaryPageWrapper)parent;
        }else if(null != activity && activity instanceof ISugaryPageWrapper){
            return (ISugaryPageWrapper)activity;
        }else{
            return null;
        }
    }

    @Override
    public void nextPage(Class<ISugaryPage> page) {
        if(null != getWrapper()){
            getWrapper().nextPage(page);
        }

    }

    @Override
    public void nextPage(Class<ISugaryPage> page, Bundle arguments) {
        if(null != getWrapper()) {
            getWrapper().nextPage(page, arguments);
        }
    }

    @Override
    public void prevPage() {
        if(null != getWrapper()) {
            getWrapper().prevPage();
        }
    }

    @Override
    public void prevPage(Bundle results) {
        if(null != getWrapper()) {
            getWrapper().prevPage(results);
        }
    }

    @Override
    public void firstPage() {
        if(null != getWrapper()) {
            getWrapper().firstPage();
        }
    }

    @Override
    public void firstPage(Class<ISugaryPage> page) {
        if(null != getWrapper()) {
            getWrapper().firstPage(page);
        }
    }

    @Override
    public void firstPage(Class<ISugaryPage> page, Bundle arguments) {
        if(null != getWrapper()) {
            getWrapper().firstPage(page, arguments);
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
    public boolean validRequestCode(int requestCode) {
        return -1 < getRequestCallbackMap().indexOfKey(requestCode);
    }

    @Override
    public void newPage(Intent intent) {
        newPage(intent, null);
    }

    @Override
    public void newPage(Intent intent, OnNewPageResponse pageResponseListener) {

        final int MAXIMUM_REQUEST_CODE = 0xffff;
        if(null != pageResponseListener){

            int requestCode = getRequestCallbackMap().size();
            requestCode |= hashCode();
            requestCode &= MAXIMUM_REQUEST_CODE;

            getRequestCallbackMap().append(requestCode, pageResponseListener);
            startActivityForResult(intent, requestCode);
        }else{
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(validRequestCode(requestCode)){
            OnNewPageResponse response = getRequestCallbackMap().get(requestCode);
            switch (resultCode){
                case Activity.RESULT_OK:
                    response.onNewPageComplete(data.getExtras());
                    break;
                case Activity.RESULT_CANCELED:
                    response.onNewPageCanceled(data.getExtras());
                    break;
                case Activity.RESULT_FIRST_USER:
                    response.onNewPageFirstUser(data.getExtras());
                    break;
                default:
                    response.onNewPageUnknown(data.getExtras());
                    break;
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void success() {
        success(null);
    }

    @Override
    public void success(Bundle response) {
        if(null != getWrapper()) {
            getWrapper().success(response);
        }else{

            if(null != response){
                Intent intent = new Intent();
                intent.putExtras(response);
                getActivity().setResult(Activity.RESULT_OK, intent);
            }else{
                getActivity().setResult(Activity.RESULT_OK);
            }
            getActivity().finish();
        }
    }

    @Override
    public void cancel() {
        cancel(null);
    }

    @Override
    public void cancel(Bundle response) {
        if(null != getWrapper()) {
            getWrapper().cancel(response);
        }else{
            if(null != response){
                Intent intent = new Intent();
                intent.putExtras(response);
                getActivity().setResult(Activity.RESULT_CANCELED, intent);
            }else{
                getActivity().setResult(Activity.RESULT_CANCELED);
            }
            getActivity().finish();
        }
    }

    @Override
    public void close(int resultCode, Bundle response) {
        if(null != getWrapper()) {
            getWrapper().close(resultCode, response);
        }else{
            if(null != response){
                Intent intent = new Intent();
                intent.putExtras(response);
                getActivity().setResult(resultCode, intent);
            }else{
                getActivity().setResult(resultCode);
            }

            getActivity().finish();
        }
    }
}
