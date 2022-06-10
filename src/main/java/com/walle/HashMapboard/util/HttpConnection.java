package com.walle.HashMapboard.util;

import org.json.simple.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpConnection {
    private static HttpURLConnection connection;

    /** HttpURLConnection GET 방식 */
    public static String getRequest(String targetUrl) {

        String response = "";
        String line;
        BufferedReader br;
        StringBuffer sb = new StringBuffer();
        Charset charset = Charset.forName("UTF-8");

        try {
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // 전송 방식
            connection.setConnectTimeout(5000); // 연결 타임아웃 설정(5초)
            connection.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)

            int status = connection.getResponseCode();
            //System.out.println("getRequest's response : " + status);

            if (status > 299)
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), charset));
            else
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));

            while((line = br.readLine()) != null){
                sb.append(line);
            }

            br.close();
            response = sb.toString();

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return response;
    }

    /** HttpURLConnection POST 방식 */
    public static String postRequest(String targetUrl, Map<String, Object> requestMap) {

        String response = "";
        String line;
        BufferedReader br;
        BufferedWriter bw;
        StringBuffer sb = new StringBuffer();
        Charset charset = Charset.forName("UTF-8");

        try {
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); // 전송 방식
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setConnectTimeout(5000); // 연결 타임아웃 설정(5초)
            connection.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)
            connection.setDoOutput(true);	// URL 연결을 출력용으로 사용(true)

            String requestBody = getJsonStringFromMap(requestMap); // map to jsonString

            bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), charset));
            bw.write(requestBody);
            bw.flush();
            bw.close();

            int status = connection.getResponseCode();
            //System.out.println("postRequest's response : " + status);

            if (status > 299)
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), charset));
            else
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));

            while ((line = br.readLine()) != null)
                sb.append(line);

            br.close();
            response = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /** Map을 jsonString으로 변환 */
    public static String getJsonStringFromMap(Map<String, Object> map) {

        JSONObject json = new JSONObject();

        for(Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            json.put(key, value);
        }

        return json.toJSONString();
    }

    /* Test Code */
    public static void main(String[] args) {

        /* GET Test*/
        String getResponse = getRequest("https://jsonplaceholder.typicode.com/albums");
        System.out.println("getRequest: " + getResponse);

        /* POST TEST*/
        String url = "https://jsonplaceholder.typicode.com/posts";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "foo");
        map.put("body", "bar");
        map.put("userId", 1);

        String postResponse = postRequest(url, map);
        System.out.println("postRequest: " + postResponse);

    }
}