package com.projectprice.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.JavascriptInterface;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;
import android.print.PrintManager;
import android.print.PrintAttributes;
import android.os.Build;
import android.content.Intent;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    private WebView web;
    private String pendingEstimate;
    private static final int SAVE_ESTIMATE = 9001;
    public final class Bridge {
        @JavascriptInterface public void copy(String value) {
            runOnUiThread(() -> {
                ClipboardManager cb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cb.setPrimaryClip(ClipData.newPlainText("ProjectPrice", value));
                Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            });
        }
        @JavascriptInterface public void saveEstimate(String json) {
            runOnUiThread(() -> {
                pendingEstimate = json;
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/json");
                intent.putExtra(Intent.EXTRA_TITLE, "projectprice-estimate.json");
                startActivityForResult(intent, SAVE_ESTIMATE);
            });
        }
        @JavascriptInterface public void printPage() {
            runOnUiThread(() -> {
                PrintManager pm = (PrintManager)getSystemService(Context.PRINT_SERVICE);
                pm.print("ProjectPrice estimate", web.createPrintDocumentAdapter("ProjectPrice"), new PrintAttributes.Builder().build());
            });
        }
    }
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(0xff050505);
        getWindow().setNavigationBarColor(0xff050505);
        web = new WebView(this);
        setContentView(web);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        web.addJavascriptInterface(new Bridge(), "ProjectPriceAndroid");
        web.loadUrl("file:///android_asset/index.html");
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SAVE_ESTIMATE && resultCode == RESULT_OK && data != null && data.getData() != null && pendingEstimate != null) {
            try(OutputStream out = getContentResolver().openOutputStream(data.getData())) {
                if(out != null) out.write(pendingEstimate.getBytes(StandardCharsets.UTF_8));
                Toast.makeText(this, "Estimate saved", Toast.LENGTH_SHORT).show();
            } catch(Exception e) { Toast.makeText(this, "Could not save estimate", Toast.LENGTH_LONG).show(); }
            pendingEstimate = null;
        }
    }
    @Override public void onBackPressed() {
        if(web.canGoBack()) web.goBack(); else super.onBackPressed();
    }
}
