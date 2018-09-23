package com.a9gag.nick.testapplication;


import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.bumptech.glide.load.HttpException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.a9gag.nick.testapplication.HotPageFragment.isLoading;

// Import Package and Java class

public class FetchDataAsync extends AsyncTask<String, String, String> implements Constants {

    ArrayList<GagsData> gagsListData = new ArrayList<>();
    ListView list;
    View mProgressBarFooter;
    CustomListAdapter customList = null;
    int retry = 0;

    public FetchDataAsync(
        ListView list,
        View mProgressBarFooter,
        CustomListAdapter customList
    ) {
        this.list = list;
        this.mProgressBarFooter = mProgressBarFooter;
        this.customList = customList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        list.addFooterView(mProgressBarFooter);
    }

    @Override
    protected String doInBackground(String... info) {
        fetchAndParseData(new HttpRequest());

        // Uncomment this out to see the Indicator bar at the End.
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    private void fetchAndParseData(HttpRequest httpJson) {
        String dataStr = httpJson.makeServiceCall();
        try {
            parseResponseData(dataStr);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            if(retry < 2) {
                retry = retry + 1;
                fetchAndParseData(new HttpRequest());
            }
        }
    }

    @Override
    protected void onPostExecute(String unused) {
        customList.addListItemToAdapter(gagsListData);
        customList.notifyDataSetChanged();
        list.removeFooterView(mProgressBarFooter);
        isLoading = false;
    }

    public void parseResponseData(String jsonResponse) throws JSONException, HttpException {
        if (jsonResponse == null) {
            return;
        }
        JSONObject metaData = new JSONObject(jsonResponse);
        String codeResponse = metaData.getJSONObject(META).getString(CODE);
        if (codeResponse.equals(META_OK)) {
            parseJsonData(jsonResponse);
        } else {
            throw new HttpException(META_ERROR);
        }
    }

    public void parseJsonData(String jsonData) {
        gagsListData.clear();
        try {
            JSONObject dataPayload = new JSONObject(jsonData);
            JSONObject getDataJSONData = dataPayload.getJSONObject("data");
            JSONArray dataGag = getDataJSONData.getJSONArray("gags");
            for (int i = 0; i < dataGag.length(); i++) {
                JSONObject dataJsonObject = dataGag.getJSONObject(i);
                GagsData gagsData = new GagsData();
                gagsData.setPayloadId(dataJsonObject.getString("_id"));
                gagsData.setPayloadTitle(dataJsonObject.getString("title"));
                gagsData.setPayloadType(dataJsonObject.getString("type"));
                gagsData.setPayloadUrl(dataJsonObject.getString("url"));
                gagsData.setUrlWidth(dataJsonObject.getString("width"));
                gagsData.setUrlHeight(dataJsonObject.getString("height"));
                gagsData.setPayloadNSFW(dataJsonObject.getString("nsfw"));
                gagsListData.add(gagsData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
