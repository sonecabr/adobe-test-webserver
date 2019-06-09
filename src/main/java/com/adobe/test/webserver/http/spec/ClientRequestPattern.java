package com.adobe.test.webserver.http.spec;


import java.util.regex.Pattern;

/**
 * Regex to query folder and files in webroot
 *
 * @author Andre Rocha
 * @since 2019-06-09
 */
public class ClientRequestPattern {

    public static final String firstLineRootWithBarRegex = "^(\\S+) / (\\S+)\\/(\\S+)?$";
    public static final String firstLineRootRegex = "^(\\S+) (\\S+)\\/(\\S+)?$";
    public static final String firstLineRegex = "^(\\S+) \\/(\\S+) (\\S+)\\/(\\S+)?$";
    public static final Pattern firstLineRootPattern = Pattern.compile(firstLineRootRegex);
    public static final Pattern firstLineRootWithBarPattern = Pattern.compile(firstLineRootWithBarRegex);
    public static final Pattern firstLinePattern = Pattern.compile(firstLineRegex);

}
