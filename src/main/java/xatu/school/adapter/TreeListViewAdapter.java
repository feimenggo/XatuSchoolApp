package xatu.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;


import java.io.File;
import java.util.List;

import xatu.school.bean.FileBean;
import xatu.school.bean.Node;
import xatu.school.utils.TreeHelper;

/**
 * Created by mmcc on 2015/11/3.
 */
public abstract class  TreeListViewAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<Node> mAllNodes;    //将用户传入的datas转换为真正的数结点类型
    protected List<Node> mVisibleNodes;  //得到真正的 可见 的结点
    protected List<FileBean> mDatas; //用户得到datas
    protected LayoutInflater mInflater;
    protected ListView mTree;

    //定义点击事件
    public interface OnTreeNodeClickListener
    {
       void onClick(Node node, int position);
    }
    //声明该接口
    public OnTreeNodeClickListener mListener;
    //设置回调函数
    public void setOnTreeNodeClickListener(OnTreeNodeClickListener mListener) {
        this.mListener = mListener;
    }

    public TreeListViewAdapter(ListView tree ,Context context, List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        mContext = context;
        mDatas= (List<FileBean>) datas;
        mInflater = LayoutInflater.from(context);
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        //为传过来的listview设置点击事件
        mTree=tree;
        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              expandOrCollapse(position);//根据position确定展开还是收缩

                if(mListener!=null)
                {
                   mListener.onClick(mVisibleNodes.get(position),position);
                }
            }
        });
    }
    //点击收缩或者展开
    private void expandOrCollapse(int position)
    {
          Node n=mVisibleNodes.get(position);
        if(n!=null)
        {
            if(n.isLeaf())return;
            n.setIsExpand(!n.isExpand()); //取非，若展开，点击收缩，若收缩，点击展开
            mVisibleNodes=TreeHelper.filterVisibleNodes(mAllNodes);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return mVisibleNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mVisibleNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node=mVisibleNodes.get(position);
        convertView=getConvertView(node,position,mDatas,convertView,parent);
        //设置子View展示的左边框距*30
        convertView.setPadding(node.getLevel()*20,3,3,3);

        return convertView;
    }
        public abstract View getConvertView(Node node,int position,List<FileBean> mDatas, View convertView,ViewGroup parent);
}
