package com.cdn;

public class Main {

    public static void main(String[] args) {
        if(args == null || args.length != 3 || !args[2].matches("^\\d+$")) {
            System.out.println("TESTER USAGE:");
            System.out.println("java -cp cdn-api-client-java-1.0.jar;cdn-api-client-java-1.0-tests.jar;gson-2.8.5.jar com.cdn.Main YOUR_API_USERNAME YOUR_API_PASSWORD YOUR_CDN_SERVICE_ID");
        }

        try {
            new ApiClientTester().run(args[0], args[1], Integer.parseInt(args[2]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
