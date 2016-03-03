package com.example.volleydemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by zcm on 2016/1/25.
 */
public class QueryResultGenerator {

    public QueryResult generateQueryResult(Context context, String fileName) {
        InputStream inputStream = getInputStream(context, fileName);
        if (inputStream == null) {
            return null;
        }


        QueryResult queryResult = null;

        try {
            XmlPullParser parser = getXmlPullParser(inputStream);

            if (parser == null) {
                return null;
            }
            int eventType = parser.getEventType();
            MatchBean match = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        queryResult = new QueryResult();
                        queryResult.setMatches(new ArrayList<MatchBean>());
                        break;

                    case XmlPullParser.START_TAG:
                        if (parser.getName().equalsIgnoreCase("reason")
                                && queryResult != null) {
                            queryResult.setStatus(parser.nextText());
                        } else if (parser.getName().equalsIgnoreCase("title")
                                && queryResult != null) {
                            queryResult.setTitle(parser.nextText());
                        } else if (parser.getName().equalsIgnoreCase("item")
                                && queryResult != null) {
                            match = new MatchBean();
                        } else {
                            Field field = getMatchField(parser.getName());
                            if (field != null) {
                                setFieldValue(match, field, parser.nextText());
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equalsIgnoreCase("item")) {
                            queryResult.getMatches().add(match);
                        }
                        break;
                }

                eventType = parser.next();

            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return queryResult;

    }

    private Field getMatchField(String name) {
        Field field = null;
        try {
            field = MatchBean.class.getDeclaredField(name);
        } catch (NoSuchFieldException e) {

        }

        return field;
    }

    private void setFieldValue(MatchBean match, Field field, Object fieldValue) {
        field.setAccessible(true);
        try {
            field.set(match, fieldValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private static XmlPullParser getXmlPullParser(InputStream inputStream) {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream, "UTF-8");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return parser;
    }

    @Nullable
    private InputStream getInputStream(Context context, String fileName) {
        return context.getResources().openRawResource(R.raw.test);
    }
}
