package com.pq.network;

/**
 * Created by CM on 12/21/2014.
 */
public interface ImageUrlProvider {
    public String getImageUrl(long imageId);
    public String getThumbnailUrl(long imageId, int size);
}
