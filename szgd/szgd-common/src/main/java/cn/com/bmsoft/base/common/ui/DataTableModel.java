package cn.com.bmsoft.base.common.ui;

import cn.com.bmsoft.base.common.easyui.DataGridRowModel;
import cn.com.bmsoft.base.common.response.ResponseBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * datatable 组件Json模型
 *
 * @author daniel
 */
public final class DataTableModel<T> extends ResponseBean<DataTableRowModel> {

    private int total;

    private List<DataTableRowModel> datas = new ArrayList<DataTableRowModel>();

    public DataTableModel() {
    }

    public DataTableModel(List<T> rowObjs) {
        this(rowObjs, rowObjs.size());
    }

    public DataTableModel(List<T> rowObjs, int total) {
        this.setRowObjects(rowObjs);
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataTableRowModel> getDatas() {
        return this.datas;
    }

    public void setDatas(List<DataTableRowModel> datas) {
        this.datas = datas;
    }

    public void setRowObjects(List<T> rowObjs) {
        List<DataTableRowModel> list = new ArrayList<DataTableRowModel>();
        for (Object obj : rowObjs) {
            DataTableRowModel row = new DataTableRowModel();
            row.setObject(obj);
            list.add(row);
        }
        this.setDatas(list);
    }

    public DataTableModel<T> addRow(DataTableRowModel row) {
        this.datas.add(row);
        return this;
    }

    public DataTableModel<T> addRowObject(Object obj) {
        DataTableRowModel row = new DataTableRowModel();
        row.setObject(obj);
        this.addRow(row);
        return this;
    }

    public void sort(final String... attributes) {
        List<DataTableRowModel> dataGridRowModels = this.getDatas();
        Collections.sort(dataGridRowModels, new Comparator<DataTableRowModel>() {
            @Override
            public int compare(DataTableRowModel o1, DataTableRowModel o2) {
                for (String attribute : attributes) {
                    Object v1 = o1.get(attribute);
                    Object v2 = o2.get(attribute);
                    
                    if (v1.equals(v2)) {
                        continue;
                    } else {
                        if (v1 instanceof String) {
                            return ((String) v1).compareToIgnoreCase(((String) v2));
                        } else {
                            throw new UnsupportedOperationException(attribute + "：不支持该属性的排序。");
                        }
                    }
                }
                return 0;
            }
        });
    }
}
