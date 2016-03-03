package com.example.volleydemo;

import java.util.List;

/**
 * Created by zcm on 2016/1/22.
 */
public class QueryResult {
    private String status;
    private String title;

    private List<MatchBean> matches;

    public List<MatchBean> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchBean> matches) {
        this.matches = matches;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
