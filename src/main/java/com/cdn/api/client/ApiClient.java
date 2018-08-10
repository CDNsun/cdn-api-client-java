package com.cdn.api.client;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    private enum RequestType {
        GET, POST, PUT, DELETE
    }

    private static final String URL_PREFIX = "https://cdnsun.com/api/";

    private String userName;
    private String password;

    public ApiClient(String userName, String password) throws Exception {
        if(userName == null || userName.isEmpty() || password == null || password.isEmpty())
            throw new Exception("Username/Password is null/empty!");

        this.userName = userName;
        this.password = password;
    }

    public Map<String,Object> get(String url, Map<String,Object> options) throws Exception {
        return this.request(url, RequestType.GET, options);
    }

    public Map<String,Object> post(String url, Map<String,Object> options) throws Exception {
        return this.request(url, RequestType.POST, options);
    }

    public Map<String,Object> put(String url, Map<String,Object> options) throws Exception {
        return this.request(url, RequestType.PUT, options);
    }

    public Map<String,Object> delete(String url, Map<String,Object> options) throws Exception {
        return this.request(url, RequestType.DELETE, options);
    }

    private Map<String,Object> request(String urlPrefix, RequestType requestType, Map<String,Object> options) throws Exception {
        if(urlPrefix == null || urlPrefix.isEmpty())
            throw new Exception("URL is null/empty!");

        if(options == null)
            throw new Exception("Null value is not allowed for options!");

        String queryString = "";
        boolean writePostData = false;
        String postData = "";
        boolean isSuccessfull = true;
        String errorMessage = "";

        switch (requestType){
            case POST:
            case PUT:
            case DELETE:
                if(!options.isEmpty()) {
                    postData = convertMapToJson(options);
                    writePostData = true;
                }
                break;
            case GET:
                if(!options.isEmpty()){
                    queryString = mapToQueryString(options);
                }
                break;
        }

        URL url = new URL(URL_PREFIX + urlPrefix + queryString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod(requestType.name());
        con.setRequestProperty ("Authorization", createAuthData());
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        if(writePostData) {
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(postData);
            out.flush();
            out.close();
        }

        int status = con.getResponseCode();


        if(status != 200)
        {
            isSuccessfull = false;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
            String inputLine;
            StringBuilder error = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                error.append(inputLine);
            }
            errorMessage = error.toString();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        con.disconnect();

        if(!isSuccessfull)
            throw new Exception("Response is not 200: " + errorMessage);

        return convertJsonToMap(content.toString());
    }

    private String createAuthData() throws UnsupportedEncodingException {
        String authData = this.userName + ":" + this.password;
        return "Basic " + DatatypeConverter.printBase64Binary(authData.getBytes("UTF-8"));
    }

    private static String mapToQueryString(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            String value = map.get(key).toString();
            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
                stringBuilder.append("=");
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return stringBuilder.toString();
    }

    private static Map<String, Object> convertJsonToMap(String data){
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String, Object> tmp = (Map<String,Object>) gson.fromJson(data, map.getClass());
        return tmp;
    }

    private static String convertMapToJson(Map<String, Object> data){
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}
