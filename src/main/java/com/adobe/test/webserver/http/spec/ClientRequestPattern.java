package com.adobe.test.webserver.http.spec;


import java.util.regex.Pattern;

public class ClientRequestPattern {

    public static final String firstLineRootRegex = "^(\\S+) (\\S+)\\/(\\S+)?$";
    public static final String firstLineRegex = "^(\\S+) \\/(\\S+) (\\S+)\\/(\\S+)?$";
    public static final Pattern firstLineRootPattern = Pattern.compile(firstLineRootRegex);
    public static final Pattern firstLinePattern = Pattern.compile(firstLineRegex);

}
