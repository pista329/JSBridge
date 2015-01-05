package com.siva4u.jsbridge;

import org.json.JSONObject;

import android.content.Context;
import android.webkit.WebView;

public class JSBridgeAPIBase {
	
    protected Context webViewContext;
    protected WebView webView;
    
    protected String callCallback(String inJsonStr, String outJsonStr) {
    	return callCallback(JSBridge.getJSONObject(inJsonStr), outJsonStr);
    }
    protected String callCallback(String inJsonStr, JSONObject outJsonObj) {
    	return callCallback(JSBridge.getJSONObject(inJsonStr), JSBridge.getString(outJsonObj));
    }
    protected String callCallback(JSONObject inJsonObj, final String outJsonStr) {
    	final String callbackID = JSBridge.getString(inJsonObj, "callbackID");
    	if(callbackID != null) {
	    	String str = JSBridge.getString(inJsonObj, "removeAfterExecute");
	    	if(str == null) str = "true";
	    	final String removeAfterExecute = str;
			webView.post(new Runnable() {
			    @Override
			    public void run() {
					webView.loadUrl("javascript: JSBridge._invokeJSCallback('"+callbackID+"',"+removeAfterExecute+",'"+outJsonStr+"');");
			    }
			});
			return "";
    	}
		return outJsonStr;
    }
    
    public JSBridgeAPIBase(Context c, WebView view) {
    	webViewContext = c;
    	webView = view;
    }    
}
