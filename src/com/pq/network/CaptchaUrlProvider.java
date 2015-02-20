package com.pq.network;

/**
 * Created by CM on 2/20/2015.
 */
public class CaptchaUrlProvider implements ImageUrlProvider {
    private RequestManager requestManager;

    public CaptchaUrlProvider(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    public String getImageUrl(long imageId) {
        return requestManager.getCaptchaUrl(imageId);
    }

    @Override
    public String getThumbnailUrl(long imageId, int size) {
        throw new UnsupportedOperationException();
    }
}
