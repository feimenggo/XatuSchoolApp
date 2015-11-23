package xatu.school.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmcc on 2015/11/2.
 */
public class Node {
    private int id;
    private int pId=0;   //根节点的pid为零
    private ScoreItem name;
    private int level;  //树的层级（深度）
    private boolean isExpand=false; //是否展开
    private int icon;

    private Node parent;
    private List<Node> children=new ArrayList<Node>();
    public Node(){

    }
    public Node(int id, int pid, ScoreItem name) {
        this.id = id;
        this.pId = pid;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getpId() {
        return pId;
    }

    public ScoreItem getName() {
        return name;
    }
     //得到当前结点的层级
    public int getLevel() {
        return parent==null ? 0 : parent.getLevel()+1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void setName(ScoreItem name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setIsExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if(!isExpand){
            for(Node node:children){
                    node.setIsExpand(false);
            }
        }
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public int getIcon() {
        return icon;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    //是否为根节点
    public boolean isRoot(){
        return parent==null;
    }
    //判断父节点是否为展开状态
    public boolean isParentExpand(){
        if(parent==null) return false;
        return parent.isExpand();
    }
    //判断是否为叶子结点,也就是没有孩子的结点
    public boolean isLeaf(){
        return children.size()==0;
    }

}
