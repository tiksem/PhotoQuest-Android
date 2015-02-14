package com.pq.data;

/**
 * Created by CM on 12/26/2014.
 */
public class Photoquest implements WithAvatar {
    private String name;
    private Long avatarId;
    private String createdBy;
    private Long viewsCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Long viewsCount) {
        if (viewsCount != null) {
            this.viewsCount = viewsCount;
        } else {
            this.viewsCount = 0l;
        }
    }
}
