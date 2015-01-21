package com.pq.data;

/**
 * Created by CM on 1/20/2015.
 */
public class Feed {
    private Long photoId;
    private Long photoquestId;
    private Long userId;
    private Long addingDate;
    private Long likesCount;
    private Long avatarId;
    private String photoquestName;
    private String userName;

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Long getPhotoquestId() {
        return photoquestId;
    }

    public void setPhotoquestId(Long photoquestId) {
        this.photoquestId = photoquestId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Long addingDate) {
        this.addingDate = addingDate;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public String getPhotoquestName() {
        return photoquestName;
    }

    public void setPhotoquestName(String photoquestName) {
        this.photoquestName = photoquestName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
