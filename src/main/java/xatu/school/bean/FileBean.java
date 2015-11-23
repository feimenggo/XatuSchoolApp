package xatu.school.bean;


import xatu.school.annotation.*;

/**
 * Created by mmcc on 2015/11/2.
 */
public class FileBean {
    @TreeNodeId
    private int id;   //本节点id
    @TreeNodepId
    private int pId;  //指向父节点的ID
    @TreeNodeLabel
    private ScoreItem label;

    public FileBean(int id, int pId, ScoreItem label) {
        this.id = id;
        this.pId = pId;
        this.label = label;

    }

    private String desc;

    public void setId(int id) {
        this.id = id;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void setLabel(ScoreItem label) {
        this.label = label;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {

        return id;
    }

    public int getpId() {
        return pId;
    }

    public ScoreItem getLabel() {
        return label;
    }

    public String getDesc() {
        return desc;
    }
}
