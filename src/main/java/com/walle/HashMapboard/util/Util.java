package com.walle.HashMapboard.util;

import javax.servlet.http.HttpServletRequest;

public class Util {

    public static boolean isEmpty(String text){
        if(text==null || "".equals(text)) return true;
        else return false;
    }

    public static boolean isNotEmpty(String text){
        return !isEmpty(text);
    }

    public static boolean isNumber(String text){
        try{
            Integer.parseInt(text);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public static String nvl(Object text){
        return nvl(text,"");
    }

    public static String nvl(Object text, String def){
        if(text==null || "".equals(text.toString().trim())){
            return def;
        }else{
            return text.toString();
        }
    }

    public static int nvl(String text, int def){
        int ret = def;
        try{
            ret = Integer.parseInt(text);
        }catch(Exception e){
            ret = def;
        }
        return ret;
    }

    public static double nvl(String text, double def){
        double ret = def;
        try{
            ret = Double.parseDouble(text);
        }catch(Exception e){
            ret = def;
        }
        return ret;
    }

    public static String getRootUrl(HttpServletRequest request){
        String ret = "";

        ret += request.getScheme() + "://" + request.getServerName();
        if(request.getLocalPort()!=80){
            ret += ":"+request.getLocalPort();
        }
        ret += "/";

        return ret;
    }

    public static String getClientIp(HttpServletRequest request){
        String clientIP = request.getHeader("Proxy-Client-IP");

        if(clientIP == null){
            clientIP = request.getHeader("WL-Proxy-Client-IP");
            if(clientIP == null){
                clientIP = request.getHeader("X-Forwared-For");
                if(clientIP == null){
                    clientIP = request.getRemoteAddr();
                }

            }

        }
        return clientIP;
    }
}
