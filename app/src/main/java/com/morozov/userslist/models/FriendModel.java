package com.morozov.userslist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class FriendModel implements Serializable {

    public FriendModel(int id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel user = (UserModel) o;
        return id == user.getId();
    }
}
