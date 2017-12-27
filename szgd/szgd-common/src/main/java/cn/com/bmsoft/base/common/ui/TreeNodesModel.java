package cn.com.bmsoft.base.common.ui;

import cn.com.bmsoft.base.common.response.ResponseBean;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简单树节点返回对象
 */
public class TreeNodesModel extends ResponseBean<TreeNodeModel> {

    private static final long serialVersionUID = 1;

    private int total;
    private List<TreeNodeModel> datas = new ArrayList<TreeNodeModel>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TreeNodeModel> getDatas() {
        return datas;
    }

    public void setDatas(List<TreeNodeModel> datas) {
        this.datas = datas;
    }

    public void addData(TreeNodeModel treeNodeModel) {
        this.datas.add(treeNodeModel);
    }

}
