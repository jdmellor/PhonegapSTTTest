package com.example.phonegapstttest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.ArrayAdapter;


public class tts extends Plugin {
	private static final String TAG = "STT";

    private String speechRecognizerCallbackId = "";
    private boolean recognizerPresent = false;

    @Override
    public PluginResult execute(String action, JSONArray args, String callbackId) {

            if (!this.speechRecognizerCallbackId.equals("")) {
                return new PluginResult(PluginResult.Status.ERROR, "Speech recognition is in progress.");
            }

            this.speechRecognizerCallbackId = callbackId;
            startSpeechRecognitionActivity(args);
            PluginResult res = new PluginResult(Status.NO_RESULT);
            res.setKeepCallback(true);
            return res;
        }

    private void startSpeechRecognitionActivity(JSONArray args) {
        int reqCode = 42;   //Hitchhiker?


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
  
        cordova.startActivityForResult(this, intent, reqCode);
    }

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
//            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            float[] confidence = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);
//
//            if (confidence != null) {
//                Log.d(TAG, "confidence length "+ confidence.length);
//                Iterator<String> iterator = matches.iterator();
//                int i = 0;
//                while(iterator.hasNext()) {
//                    Log.d(TAG, "Match = " + iterator.next() + " confidence = " + confidence[i]);
//                    i++;
//                }
//            } else {
//                Log.d(TAG, "No confidence" +
//                        "");
//            }

            ReturnSpeechResults(requestCode, matches);
        }
        else {
            // Failure - Let the caller know
            ReturnSpeechFailure(resultCode);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ReturnSpeechResults(int requestCode, ArrayList<String> matches) {
        boolean firstValue = true;
        StringBuilder sb = new StringBuilder();
//        sb.append("{\"speechMatches\": {");
//        sb.append("\"requestCode\": ");
//        sb.append(Integer.toString(requestCode));
//        sb.append(", \"speechMatch\": [");
//
//        Iterator<String> iterator = matches.iterator();
//        while(iterator.hasNext()) {
//            String match = iterator.next();
//
//            if (firstValue == false)
//                sb.append(", ");
//            firstValue = false;
//            sb.append(JSONObject.quote(match));
//        }
//        sb.append("]}}");
        sb.append(matches.get(0));
        PluginResult result = new PluginResult(PluginResult.Status.OK, sb.toString());
        result.setKeepCallback(false);
        this.success(result, this.speechRecognizerCallbackId);
        this.speechRecognizerCallbackId = "";
    }

    private void ReturnSpeechFailure(int resultCode) {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, Integer.toString(resultCode));
        result.setKeepCallback(false);
        this.error(result, this.speechRecognizerCallbackId);
        this.speechRecognizerCallbackId = "";
    }
}


//	 private static final int REQUEST_CODE = 1234;
//	 private String speechRecognizerCallbackId = "";
//	 private static final String TAG = "TTS";
//	@Override
//	public PluginResult execute(String action, JSONArray data, String callbackId) {
//		// TODO Auto-generated method stub
//		PackageManager pm = cordova.getActivity().getPackageManager();
//        List<ResolveInfo> activities = pm.queryIntentActivities(
//                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
//        if (activities.size() == 0)
//        {
//        	return new PluginResult(PluginResult.Status.ERROR, "No Voice Recognizer Installed"); 
//        }
//        this.speechRecognizerCallbackId = callbackId;
//        startRecognizer(data);
//        PluginResult res = new PluginResult(Status.NO_RESULT);
//        res.setKeepCallback(true);
//        return res;
//	}
//	public void startRecognizer(JSONArray data){
//	       Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//	        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//	                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//	        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
//	        cordova.getActivity().startActivityForResult(intent, REQUEST_CODE);
//	       
//	}
//	public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (requestCode == REQUEST_CODE && resultCode == cordova.getActivity().RESULT_OK)
//        {
//        	Log.v(TAG,"Here");
//            // Populate the wordsList with the String values the recognition engine thought it heard
//            ArrayList<String> matches = data.getStringArrayListExtra(
//                    RecognizerIntent.EXTRA_RESULTS);
//            String[] p;
//            p= matches.toArray(new String[matches.size()]);
//            for(int i=0; i<p.length; i++){
//            	Log.v(TAG,p[i]);
//            }
//
//        super.onActivityResult(requestCode, resultCode, data);
//        }
//
//    }
//	private void returnResults(int requestCode, ArrayList<String> matches){
//		 boolean firstValue = true;
//	        StringBuilder sb = new StringBuilder();
//	        sb.append("{\"speechMatches\": {");
//	        sb.append("\"requestCode\": ");
//	        sb.append(Integer.toString(requestCode));
//	        sb.append(", \"speechMatch\": [");
//
//	        Iterator<String> iterator = matches.iterator();
//	        while(iterator.hasNext()) {
//	            String match = iterator.next();
//
//	            if (firstValue == false)
//	                sb.append(", ");
//	            firstValue = false;
//	            sb.append(JSONObject.quote(match));
//	        }
//	        sb.append("]}}");
//
//	        PluginResult result = new PluginResult(PluginResult.Status.OK, sb.toString());
//	        result.setKeepCallback(false);
//	        this.success(result, this.speechRecognizerCallbackId);
//	}
//}
