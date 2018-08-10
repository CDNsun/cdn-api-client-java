# Client for CDNsun CDN API


SYSTEM REQUIREMENTS

 * Java 6 or newer
 * Maven 4.0.0 (tested) or newer
 * Google gson 2.8.5 (tested) or newer

CDN API DOCUMENTATION

https://cdnsun.com/knowledgebase/api

BUILD

  To build the generator go to **/cdn-api-client-java/** folder and run the following command:
```  
mvn clean package
```  
  Upon success of the build, you will find three jar files in the folder named ***target***:
   * **cdn-api-client-java-1.0.jar**: Main library jar which includes necessary classes to make api call.
   * **cdn-api-client-java-1.0-tests.jar**: Used for testing purposes.
   * **gson-2.8.5.jar**: Used for JSon parsing.


REFERENCING

Add reference to **cdn-api-client-java-1.0.jar** in project.

Add reference to **gson-2.8.5.jar** in project.

HOW TO USE

Create ApiClient objects instance with a constructor that takes **userName** and **password** as parameter.
Create a HashMap to pass options to methods.
Call the method which is needed and set it to a new variable.

CLIENT USAGE
* Initialize the client

```
String userName = "userName";
String password = "password";
int id = 123456;

HashMap<String, Object> data;
ApiClient apiClient = new ApiClient(userName, password);
```

* Get CDN service reports (https://cdnsun.com/knowledgebase/api/documentation/res/com.cdn/act/reports)

```
data = new HashMap<String, Object>();
data.put("type", "GB");
data.put("period", "4h");
result = apiClient.get("cdns/" + id + "/reports/", data);
Util.printMapFriendly(result);
```

* Purge CDN service content (https://cdnsun.com/knowledgebase/api/documentation/res/com.cdn/act/purge)

```
data = new HashMap<String, Object>();
data.put("purge_paths", new String[]{"/path1.img", "/path2.img"});
result = apiClient.post("cdns/" + id + "/purge/", data);
Util.printMapFriendly(result);
```

TEST USAGE

Run the following command under target folder:

For Linux based systems:
```
java -cp cdn-api-client-java-1.0.jar:cdn-api-client-java-1.0-tests.jar:gson-2.8.5.jar com.cdn.Main YOUR_API_USERNAME YOUR_API_PASSWORD YOUR_CDN_SERVICE_ID
```

For Windows based systems:
```
java -cp cdn-api-client-java-1.0.jar;cdn-api-client-java-1.0-tests.jar;gson-2.8.5.jar com.cdn.Main YOUR_API_USERNAME YOUR_API_PASSWORD YOUR_CDN_SERVICE_ID
```

CONTACT

* W: https://cdnsun.com
* E: info@cdnsun.com
