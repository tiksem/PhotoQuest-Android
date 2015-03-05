package com.pq.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CM on 3/5/2015.
 */
public class UserInfo implements Parcelable {
    public long id;
    public Long avatarId;
    public String nameData;

    public UserInfo(User user) {
        id = user.getId();
        avatarId = user.getAvatarId();
        nameData = user.getNameData();
    }

    public UserInfo(DialogInfo dialogInfo) {
        id = dialogInfo.userId;
        avatarId = dialogInfo.avatarId;
        nameData = dialogInfo.name + " " + dialogInfo.lastName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeValue(this.avatarId);
        dest.writeString(this.nameData);
    }

    private UserInfo(Parcel in) {
        this.id = in.readLong();
        this.avatarId = (Long) in.readValue(Long.class.getClassLoader());
        this.nameData = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
