package com.cdn;

import com.cdn.api.client.ApiClient;

import java.util.HashMap;
import java.util.Map;

public class ApiClientTester {
    public void run(String YOUR_API_USERNAME, String YOUR_API_PASSWORD, int YOUR_CDN_SERVICE_ID) throws Exception {
        HashMap<String, Object> data;
        ApiClient apiClient = new ApiClient(YOUR_API_USERNAME, YOUR_API_PASSWORD);

        data = new HashMap<String, Object>();
        Map<String, Object> result = apiClient.get("cdns/", data);
        Util.printMapFriendly(result);


        data = new HashMap<String, Object>();
        data.put("type", "GB");
        data.put("period", "4h");
        result = apiClient.get("cdns/" + YOUR_CDN_SERVICE_ID + "/reports/", data);
        Util.printMapFriendly(result);

        data = new HashMap<String, Object>();
        data.put("purge_paths", new String[]{"/path1.img", "/path2.img"});
        result = apiClient.post("cdns/" + YOUR_CDN_SERVICE_ID + "/purge/", data);
        Util.printMapFriendly(result);
    }
}
