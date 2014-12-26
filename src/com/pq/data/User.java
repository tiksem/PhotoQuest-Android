package com.pq.data;

import com.utils.framework.collections.cache.GlobalStringCache;

/**
 * Created by CM on 12/20/2014.
 */

public class User implements WithAvatar {
    private static final GlobalStringCache STRING_CACHE = GlobalStringCache.getInstance();

    private Long avatarId;
    private String name;
    private String lastName;
    private String country;
    private String city;

    @Override
    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = STRING_CACHE.putOrGet(name);;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = STRING_CACHE.putOrGet(lastName);;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = STRING_CACHE.putOrGet(country);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = STRING_CACHE.putOrGet(city);;
    }
}
