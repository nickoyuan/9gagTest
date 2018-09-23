package com.a9gag.nick.testapplication;

import com.bumptech.glide.load.HttpException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class JsonParseUnitTest {


    @Before
    public void setUp() {

    }

    @AfterClass
    public static void tearDown() {
    }

    @Test
    public void convertStreamToStringUnitTest() {
        // Given
        HttpRequest jsonTest = new HttpRequest();
        String testString = "HELLO";

        // When
        InputStream is = new ByteArrayInputStream(testString.getBytes(
            StandardCharsets.UTF_8
        ));

        // Then
        assertEquals(testString + "\n" , jsonTest.convertStreamToString(is));
    }

    @Test
    public void gagsDataUnitTest() {
        GagsData gagsData= new GagsData();
        gagsData.setUrlWidth("1");
        gagsData.setUrlHeight("2");
        gagsData.setPayloadNSFW("0");
        gagsData.setPayloadType("12");
        gagsData.setPayloadId("123");
        gagsData.setPayloadTitle("hello");
        gagsData.setPayloadUrl("google");

        assertEquals(gagsData.getUrlWidth(), "1");
        assertEquals(gagsData.getUrlHeight(), "2");
        assertEquals(gagsData.getPayloadNSFW(), "0");
        assertEquals(gagsData.getPayloadType(), "12");
        assertEquals(gagsData.getPayloadId(), "123");
        assertEquals(gagsData.getPayloadTitle(), "hello");
        assertEquals(gagsData.getPayloadUrl(), "google");
    }

    @Test
    public void jsonErrorResponseUnitTest() {
        try {
            // Given
            String errorResponse = new JSONObject().put(
                "meta", new JSONObject().put("code", "404")
            ).toString();
            // When
            FetchDataAsync json = new FetchDataAsync(null,null,null);

            // Then
            json.parseResponseData(errorResponse);
        } catch (JSONException e) {
            fail("JSON Exception has thrown test fail " + e);
        } catch (HttpException e) {
            // Test has passed
        }
    }

    @Test
    public void jsonDataUnitTest() {
        try {
            // Given
            JSONObject dataPayload = new JSONObject();
            dataPayload.put("_id", "11");
            dataPayload.put("title", "250 pounds");
            dataPayload.put("type", "Photo");
            dataPayload.put("url", "http:\\/\\/stg-img-9gag-fun.9cache.com" +
                "\\/photo\\/azbgoQb_700b.jpg");
            dataPayload.put("width", "780");
            dataPayload.put("height", "1024");
            dataPayload.put("nsfw", "0");

            JSONArray dataArray = new JSONArray();
            dataArray.put(dataPayload);
            JSONObject gags = new JSONObject().put(
                "gags", dataArray
            );
            JSONObject data = new JSONObject();
            data.put("data", gags);

            // When
            FetchDataAsync json = new FetchDataAsync(null,null,null);

            // Then
            json.parseJsonData(data.toString());
        } catch (JSONException e) {
            fail("JSON Exception has thrown test fail " + e);
        }
    }

}
