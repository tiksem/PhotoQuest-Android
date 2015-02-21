package com.pq.data;

import com.utils.framework.strings.Strings;

/**
 * Created by CM on 2/21/2015.
 */
public class Reply {
    public static final int FRIEND_REQUEST_ACCEPTED = 0;
    public static final int FRIEND_REQUEST_DECLINED = 1;
    public static final int COMMENT = 2;
    public static final int LIKE = 3;

    public int type;
    public String name;
    public String comment;
    public Long photoId;
    public long avatarId;
    public long addingDate;
}
