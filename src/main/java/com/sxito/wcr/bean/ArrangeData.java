package com.sxito.wcr.bean;

import java.util.Date;

/**
 * Created by 62450 on 2017/12/8.
 */
public class ArrangeData {
    private int id;
    private String issue;
    private String vote;
    private String nodeNormal;
    private String nodeTree;
    private Date gmtCreate;
    private Date gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getNodeNormal() {
        return nodeNormal;
    }

    public void setNodeNormal(String nodeNormal) {
        this.nodeNormal = nodeNormal;
    }

    public String getNodeTree() {
        return nodeTree;
    }

    public void setNodeTree(String nodeTree) {
        this.nodeTree = nodeTree;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
