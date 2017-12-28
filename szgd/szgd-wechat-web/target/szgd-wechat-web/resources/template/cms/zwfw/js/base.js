function license(){
	//选择证照数据产生大类
	$("#dataType").select({
        title: "选择证照数据产生大类",
        items: [
          {
            title: "A类",
            value: "001",
          },
          {
            title: "B类",
            value: "002",
          },
          {
            title: "C类",
            value: "003",
          }
        ]
    });
    //选择主体类别
    $("#mainType").select({
        title: "选择主体类别",
        items: [
          {
            title: "个人",
            value: "001",
          },
          {
            title: "企业（组织）",
            value: "002",
          },
          {
            title: "个人和企业",
            value: "003",
          }
        ]
    });
    //选择证照类型
    $("#licenseType").select({
        title: "选择证照类型",
        items: [
          {
            title: "证照",
            value: "001",
          },
          {
            title: "证书",
            value: "002",
          },
          {
            title: "证明",
            value: "003",
          },
          {
            title: "证件",
            value: "003",
          },
          {
            title: "批文",
            value: "003",
          },
          {
            title: "批复",
            value: "003",
          },
          {
            title: "通知",
            value: "003",
          },
          {
            title: "公函",
            value: "003",
          },
          {
            title: "文件",
            value: "003",
          },
          {
            title: "合同",
            value: "003",
          },
          {
            title: "回执",
            value: "003",
          },
          {
            title: "其他",
            value: "003",
          }
        ]
    });
    //选择所属部门
    $("#departType").select({
        title: "选择所属部门",
        items: [
          {
            title: "全部",
            value: "001",
          },
          {
            title: "禁毒",
            value: "002",
          },
          {
            title: "交通警察",
            value: "003",
          }
        ]
    });
     //发布状态
    $("#publishStatus").select({
        title: "发布状态",
        items: [
          {
            title: "未发布",
            value: "001",
          },
          {
            title: "已发布",
            value: "002",
          }
        ]
    });
     //接口类型
    $("#interfaceType").select({
        title: "接口类型",
        items: [
          {
            title: "全部",
            value: "001",
          },
          {
            title: "数据库接口",
            value: "002",
          },
          {
            title: "服务接口",
            value: "003",
          }
        ]
    });
    
    //权力清单  
    //类型
    $("#powerType").select({
        title: "全部类型",
        items: [
          {
            title: "行政许可",
            value: "001",
          },
          {
            title: "行政处罚",
            value: "002",
          },
          {
            title: "行政强制",
            value: "003",
          }
        ]
    });
    //主题
    $("#powerTheme").select({
        title: "默认主题",
        items: [
          {
            title: "县（市、区）属地管理",
            value: "001",
          },
          {
            title: "市级保留",
            value: "002",
          },
          {
            title: "共性权利",
            value: "003",
          },
          {
            title: "审核转报",
            value: "003",
          }
        ]
    });
}
