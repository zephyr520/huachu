package com.huachu.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @DATE 2018/8/17
 */
public class HttpURLConnUtil {

    private static final String CONNFALL = "HTTPFAILED";

    /**
     * @param urlStr
     * @param charCode
     * @param sessionId
     * @return
     * @throws Exception
     */
    public static String urlGet(String urlStr, String charCode, String sessionId) throws Exception {
        String contentStr = "";
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(urlStr);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setConnectTimeout(30 * 1000);
            httpConnection.setReadTimeout(60 * 1000);
            if (sessionId != null && !"".equals(sessionId.trim())) {
                httpConnection.setRequestProperty("Cookie", sessionId);
            }
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream urlStream = httpConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream, charCode));
                String sCurrentLine = null;
                StringBuffer sTotalString = new StringBuffer();
                while ((sCurrentLine = bufferedReader.readLine()) != null) {
                    sTotalString.append(sCurrentLine);
                }
                contentStr = sTotalString.toString().trim();
                urlStream.close();
                bufferedReader.close();
            } else {
                contentStr = CONNFALL;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return contentStr;
    }

    /**
     * @param urlStr
     * @param parameters
     * @param charCode
     * @param sessionId
     * @return
     * @throws Exception
     */
    public static String urlPost(String urlStr, Map<String, String> parameters, String charCode, String sessionId) throws Exception {
        String contentStr = "";
        HttpURLConnection httpConnection = null;
        try {
            StringBuffer params = new StringBuffer();
            Iterator<Map.Entry<String, String>> paramsIter = parameters.entrySet().iterator();
            while (paramsIter.hasNext()) {
                Map.Entry<String, String> element = paramsIter.next();
                params.append(element.getKey());
                params.append("=");
                params.append(URLEncoder.encode(element.getValue(), charCode));
                params.append("&");
            }
            int iLen = params.length();
            if (iLen > 0) {
                params = params.deleteCharAt(iLen - 1);
            }
            URL url = new URL(urlStr);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setUseCaches(false);
            httpConnection.setInstanceFollowRedirects(true);
            if (sessionId != null && !"".equals(sessionId.trim())) {
                httpConnection.setRequestProperty("Cookie", sessionId);
            }
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.setConnectTimeout(300 * 1000);
            httpConnection.setReadTimeout(300 * 1000);
            DataOutputStream out = new DataOutputStream(httpConnection.getOutputStream());
            out.writeBytes(params.toString());

            out.flush();
            out.close();

            System.out.println(httpConnection.getResponseMessage());
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream urlStream = httpConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream, charCode));
                String sCurrentLine = null;
                StringBuffer sTotalString = new StringBuffer();
                while ((sCurrentLine = bufferedReader.readLine()) != null) {
                    sTotalString.append(sCurrentLine);
                }
                contentStr = sTotalString.toString().trim();
                urlStream.close();
                bufferedReader.close();
            } else {
                contentStr = CONNFALL;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return contentStr;
    }

    /**
     * @param urlStr
     * @param cookieKey
     * @return
     * @throws Exception
     */
    public static String getCookie(String urlStr, String cookieKey) throws Exception {
        HttpURLConnection httpConnection = null;
        String JSESSIONID = "";
        try {
            URL url = new URL(urlStr);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(30 * 1000);
            httpConnection.setReadTimeout(60 * 1000);
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String session_value = httpConnection.getHeaderField("Set-Cookie");
                if (session_value != null && !"".equals(session_value)) {
                    String[] sessionId = session_value.split(";");
                    if (sessionId != null) {
                        for (int i = 0; i < sessionId.length; i++) {
                            if (sessionId[i].indexOf(cookieKey) >= 0) {
                                JSESSIONID = sessionId[i];
                                break;
                            }
                        }
                    }
                }
                if ("".equals(JSESSIONID)) {
                    Map<String, List<String>> headerMap = httpConnection.getHeaderFields();
                    if (headerMap != null) {
                        List<String> listsessionValue = headerMap.get("Set-Cookie");
                        if (listsessionValue != null) {
                            StringBuffer StrBuf = new StringBuffer();
                            for (String strValue : listsessionValue) {
                                if (strValue.endsWith(";")) {
                                    StrBuf.append(strValue + " ");
                                } else {
                                    StrBuf.append(strValue + "; ");
                                }
                            }
                            JSESSIONID = StrBuf.toString().trim();
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return JSESSIONID;
    }

    /**
     * 模拟提交有文件上传的表单(通过http模拟上传文件)
     *
     * @param serverUrl
     * @param paramMap
     * @param files
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String simulationPostRequest(String serverUrl, Map<String, String> paramMap,
                                               byte[] files, String fileName) throws Exception {
        URL url = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer();
        try {
            // 每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
            String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";
            // 向服务器发送post请求 /* serverUrl="http://127.0.0.1:8080/test/upload" */
            url = new URL(serverUrl);
            connection = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            // 头
            String boundary = BOUNDARY;
            // 传输内容
            StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);
            // 尾
            String endBoundary = "\r\n--" + boundary + "--\r\n";
            OutputStream out = connection.getOutputStream();

            // 1. 处理普通表单域(即形如key = value对)的POST请求
            for (String key : paramMap.keySet()) {
                contentBody.append("\r\n").append("Content-Disposition: form-data; name=\"").append(key + "\"").append("\r\n").append("\r\n").append(paramMap.get(key))
                        .append("\r\n").append("--").append(boundary);
            }
            String boundaryMessage1 = contentBody.toString();
            out.write(boundaryMessage1.getBytes("utf-8"));

            // 2. 处理文件上传,上传文件的文件名，包括目录,form中field的名称
            contentBody = new StringBuffer();
            contentBody.append("\r\n").append("Content-Disposition:form-data; name=\"")
                    .append("newfiles" + "\"; ")
                    .append("filename=\"").append(fileName + "\"")
                    .append("\r\n").append("Content-Type:application/octet-stream").append("\r\n\r\n");

            String boundaryMessage2 = contentBody.toString();
            out.write(boundaryMessage2.getBytes("utf-8"));
            out.write(files);


            // 3. 写结尾
            out.write(endBoundary.getBytes("utf-8"));
            out.flush();
            out.close();

            // 4. 从服务器获得回答的内容
            String strLine = "";
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            while ((strLine = reader.readLine()) != null) {
                result.append(strLine).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (reader != null)
                reader.close();
            if (connection != null)
                connection.disconnect();
            if (url != null)
                url = null;
        }
        return result.toString();
    }

    public static String postJson(String urlStr, String json)
            throws IOException {

        HttpURLConnection httpConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream bout = null;
        DataOutputStream out = null;
        try {

            URL url = new URL(urlStr);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setUseCaches(false);
            // httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestProperty("Content-Type",
                    "application/json; charset=" + "utf-8");
            // httpConnection.setRequestProperty("Content-Type",
            // "application/x-www-form-urlencoded");
            httpConnection.setConnectTimeout(300 * 1000);
            httpConnection.setReadTimeout(300 * 1000);
            out = new DataOutputStream(httpConnection.getOutputStream());
            out.write(json.getBytes("utf-8"));
            out.flush();
            out.close();
            int code = httpConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpConnection.getInputStream();
                bout = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(buf)) != -1) {
                    bout.write(buf, 0, len);
                }
                bout.flush();
                String content = new String(bout.toByteArray(), "utf-8");
                return content;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (bout != null) {
                bout.close();
            }
            if (out != null) {
                out.close();
            }

        }

        return null;

    }

    public static String getJson(String urlStr) throws IOException {

        HttpURLConnection httpConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream bout = null;

        try {
            URL url = new URL(urlStr);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setUseCaches(false);
            httpConnection.setRequestProperty("Content-Type",
                    "application/json; charset=" + "utf-8");
            httpConnection.setConnectTimeout(300 * 1000);
            httpConnection.setReadTimeout(300 * 1000);

            int code = httpConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpConnection.getInputStream();
                bout = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(buf)) != -1) {
                    bout.write(buf, 0, len);
                }
                bout.flush();
                String content = new String(bout.toByteArray(), "utf-8");
                return content;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (bout != null) {
                bout.close();
            }

        }

        return null;

    }
}
