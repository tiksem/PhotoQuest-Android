package com.pq.data;

/**
 * Created by CM on 12/26/2014.
 */
public class Photoquest implements WithAvatar {
    private String name;
    private Long avatarId;

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
}
