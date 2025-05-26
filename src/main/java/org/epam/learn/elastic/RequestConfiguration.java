package org.epam.learn.elastic;

import co.elastic.clients.transport.rest5_client.low_level.HttpAsyncResponseConsumerFactory;
import co.elastic.clients.transport.rest5_client.low_level.RequestOptions;

public class RequestConfiguration {

    private static final String TOKEN = "NFpxQThwWUIwdnpPaGFJT0tHRkE6blpQQ2laNE00OFNaX0sxSDUyZzhhZw==";

    private static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT
                .toBuilder()
                .addHeader("Authorization", "ApiKey " + TOKEN)
                .setHttpAsyncResponseConsumerFactory(
                        HttpAsyncResponseConsumerFactory.DEFAULT
                );

        COMMON_OPTIONS = builder.build();
    }

    public static RequestOptions getRequestOptions() {
        return COMMON_OPTIONS;
    }
}
