import java.util.*;

public class UrlShortener{
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = BASE62.length();
    private final Map<String, String> shortToLong = new HashMap<>();
    private final Map<String, String> longToShort = new HashMap<>();
    private long counter = 100000L;

    public synchronized String shorten(String longUrl){
        if(longToShort.containsKey(longUrl)){
            return longToShort.get(longUrl);
        }

        String key = encode(counter++);
        shortToLong.put(key, longUrl);
        longToShort.put(longUrl, key);
        return "https://short/" + key;
    }

    public String getOriginal(String shortUrl){
        String key = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        return shortToLong.getOrDefault(key, null);
    }

    private String encode(long num){
        StringBuilder sb = new StringBuilder();
        while(num > 0){
            sb.append(BASE62.charAt((int)(num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args){
        UrlShortener us = new UrlShortener();
        String s = us.shorten("https://google.com");
        System.out.println("Short: "+ s);
        System.out.println("Original: " + us.getOriginal(s));
    }
}

