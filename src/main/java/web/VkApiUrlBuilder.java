package web;

import java.util.HashMap;

public class VkApiUrlBuilder {
    private final String method;
    private final HashMap<String, String> params;
    private final static String secretToken = System.getenv("ACCESS_TOKEN");

    public VkApiUrlBuilder(String method, HashMap<String, String> params) {
        this.method = method;
        this.params = params;
    }

    public String build() {
        String domain = "https://api.vk.com/method/";
        StringBuilder builder = new StringBuilder(domain);

        builder
                .append(method)
                .append("?");

        for (String key: params.keySet()) {
            builder
                    .append(key)
                    .append("=")
                    .append(params.get(key))
                    .append("&");
        }

        builder
                .append("access_token=")
                .append(secretToken)
                .append("&");

        // https://oauth.vk.com/authorize?client_id=51436218&display=page&redirect_uri=https://oauth.vk.com/blank.html/callback&scope=friends&response_type=token&v=5.131&state=123456
        String version = "5.131";
        builder
                .append("v=")
                .append(version);

        return builder.toString();
    }
}
