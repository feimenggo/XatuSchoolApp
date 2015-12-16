package xatu.school.utils;

import android.util.Log;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import xatu.school.R;
import xatu.school.annotation.*;
import xatu.school.bean.Node;
import xatu.school.bean.ScoreItem;

/**
 * 将用户的数据转化为树形数据
 */
public class TreeHelper {
    public static <T> List<Node> convertData2Nodes(List<T> datas) throws IllegalAccessException {
        List<Node> nodes = new ArrayList<>();
        Node node;
        for (T t : datas) {
            int id = -1;
            int pid = -1;
            ScoreItem Label = null;
            //通过反射，得到所有t里的属性( 反射与注解的结合使用 可以得到任意类的某个方法或属性)
            //通过注解帮我们标识类的某个方法或属性，然后通过反射拿到该值
            Class clazz = t.getClass();
            //通过反射 得到全部属性集
            Field[] fields = clazz.getDeclaredFields();
            //遍历属性集得到属性
            for (Field field : fields) {
                if (field.getAnnotation(TreeNodeId.class) != null) //判断是否有该注解
                {
                    field.setAccessible(true);  //设置权限，默认是外部不可使用
                    id = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodepId.class) != null) {
                    field.setAccessible(true);
                    pid = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    Label = (ScoreItem) field.get(t);
                }
            }
            node = new Node(id, pid, Label);
            nodes.add(node);
        }//for end

        //设置关联关系  即 父结点与子结点
        //node间的结点关系
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);   //得到每一个node
            for (int j = i + 1; j < nodes.size(); j++)  //拿每一个节点与剩下的比较，看是否具有父子关联
            {
                Node m = nodes.get(j);
                if (m.getpId() == n.getId())  //判断m的Pid(父id)是否等于n的id
                {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getpId()) { //否则判断m是否为n的父节点
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        //设置图标
        for (Node n : nodes) {
            setNodeIcon(n);
        }
        Log.i("Tag", nodes.size() + "");
        return nodes;
    }

    //为节点排序，避免根节点的子节点错乱
    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        List<Node> result = new ArrayList<>();
        List<Node> nodes = convertData2Nodes(datas);
        //获取树的根节点
        List<Node> rootNodes = getRootNodes(nodes);
        //遍历根结点
        for (Node node : rootNodes) {
            /*
           该方法，顺序的将根节点与子结点放入result中（将一个结点的所有孩子放入result中）
            * 第一个参数，表示将排序后的结点放入result集合
            * 第二个表示，需要排序的根节点，
            * 第三个表示，默认展开的层数
            * 第四个表示，当前结点层数，根节点是1层
            * */
            addNode(result, node, defaultExpandLevel, 1);
        }
        Log.i("Tag", result.size() + "");
        return result;
    }

    private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);
        if (defaultExpandLevel >= currentLevel) {
            node.setIsExpand(true);
        }
        if (node.isLeaf())  //不为子节点时继续执行下列递归，直到将当前node的所有子节点放入result中
            return;
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(result, node.getChildren().get(i), defaultExpandLevel, currentLevel + 1);
        }
    }

    //过滤出能显示的node(筛选出可见的node，才能显示在listview中)
    public static List<Node> filterVisibleNodes(List<Node> nodes) {
        List<Node> result = new ArrayList<>();
        for (Node node : nodes) {
            //根节点或者，父节点展开状态的结点，放入result中，可以显示出来
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }


    //从所有结点中得到所有根节点
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<>();

        for (Node node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

    //为node设置图标
    public static void setNodeIcon(Node n) {
        if (n.getChildren().size() > 0 && n.isExpand())  //说明有子节点,且展开，需要设置图标
        {
            n.setIcon(R.mipmap.tree_ex);
        } else if (n.getChildren().size() > 0 && !n.isExpand()) { //有子节点，但没展开
            n.setIcon(R.mipmap.tree_ec);
        } else {      //为子节点时不需要设置图标
            n.setIcon(-1);
        }
    }
}
