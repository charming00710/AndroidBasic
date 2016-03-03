package com.example.greeendaodemo;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by zcm on 2016/1/27.
 */
public class ExampleDaoGenerator {
    public static void main(String[] args) {
        Schema schema =  new Schema(1, "com.example.greeendaodemo");

        addNote(schema);
        addBook(schema);

        try {
            new DaoGenerator().generateAll(schema, "E:\\android_projects\\AndroidBasic\\greeendaodemo\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void addBook(Schema schema) {
        Entity entity = schema.addEntity("Book");
        entity.setTableName("T_BOOK");
        entity.addIdProperty();
        entity.addStringProperty("name");
        entity.addFloatProperty("price");
    }

    private static void addNote(Schema schema) {
        Entity entity = schema.addEntity("Note");
        entity.setTableName("T_NOTE");
        entity.addIdProperty();
        entity.addStringProperty("text").notNull();
        entity.addStringProperty("comment").notNull();
        entity.addDateProperty("date");
    }
}
