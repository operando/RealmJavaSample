package com.os.operando.realm.sample.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject {
    @PrimaryKey
    public long id;
    public String title;
    public String comment;
}
