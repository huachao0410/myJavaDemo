package by.vi.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.vi.cnf.spring.DefaultDataSource;
import by.vi.cnf.spring.MyDataSource;
 

@Service
public class DbCompare {
	
	@Autowired
	DefaultDataSource ds1; 
	 
	@Autowired
	MyDataSource dsTwo;
	
	public String compareTable(){
		StringBuilder sb=new StringBuilder();
		List<String> src=getTableList(ds1.getDs());
		List<String> dst=getTableList(dsTwo.getDs());  
		
		int sameNum=0;
		int diffNum=0;
		int notTableNum=0;
		
		sb.append("比较源库表个数:"+src.size()+",\t目标库表个数"+dst.size()+"<br>");
		ArrayList<String> el=new ArrayList<String>();
		String n;
		for(int i=0;i<src.size();i++){
			sb.append("\r\n");			
			n=src.get(i);
			el.add(n);
			sb.append("对比表:"+n+","+space(3));
			sb.append("\r\n");
			if(exist(n,dst)){				
				boolean b=compareTable(sb,n,ds1.getDs(),dsTwo.getDs());
				if(b){
					sameNum++;
				}else{
					diffNum++;
				}
			}else{
				notTableNum++;
				sb.append("\r\n");
				sb.append("目标库不存在表:<font color='red'>"+n+"</font><br>");
			}
		}
		
		sb.append("\r\n");
		sb.append("<p>相同结构表数目:"+sameNum+",差异表个数:"+diffNum+",目标库缺少表个数:"+notTableNum+"</p><br>");
		
		return sb.toString();
	}
	
	private boolean compareTable(StringBuilder sb,String table,DataSource src,DataSource dest){
		boolean same=true;
		
		HashMap<String,String> srcTable;
		HashMap<String,String> dstTable;
		srcTable=getTableDesc(table,src);
		dstTable=getTableDesc(table,dest); 
		Object []kf=srcTable.keySet().toArray();
		Object []kt=dstTable.keySet().toArray();
		if(kf.length!=kt.length){
			sb.append(space(8)+"表:<font color='red'>"+table+"</font>,字段数不一致<br>");
			same=false;
		}
		ArrayList<String> exist=new ArrayList<String>();
		String k,v1,v2;
		for(int i=0;i<kf.length;i++){
			k=(String)kf[i];
			v1=srcTable.get(k);
			v2=dstTable.get(k);

			if(v2==null){
				sb.append("\r\n");
				sb.append(space(8)+"目标表缺少字段:"+k+"("+v1+")<br>");
				same=false;
			}else{
				if( v1.equals(v2) == false ){
					sb.append("\r\n");
					sb.append(space(8)+"字段["+k+"]定义不一致,源:"+v1+",目标:"+v2+"<br>");
					same=false;
				}
				exist.add(k);
			}
		}
		for(int i=0;i<kt.length;i++){
			k=(String)kt[i];
			if(notExist(k,kf)){
				sb.append("\r\n");
				sb.append(space(8)+"源表字段多余,field="+k+"<br>");
				same=false;
			}else{
				
			}
		}
		 
		if(!same){
			sb.append("\r\n");
			sb.append(space(8)+"<font color='red'><b>结构不一致</b></font><br>");
		}else{
			sb.append("\r\n");
			sb.append(space(8)+"<font color='blue'><b>结构相同</b></font><br> ");
		}
		return same;
	}
	
	private boolean notExist(String v,Object[] ex){
		boolean b=true;
		for(int i=0;i<ex.length;i++){
			if(v.equals(ex[i])){
				b=false;
				break;
			}
		}
		return b;
	}
	
	public boolean notExist(String v,List<String> ex){
		boolean b=true;
		for(int i=0;i<ex.size();i++){
			if(v.equals(ex.get(i))){
				b=false;
				break;
			}
		}
		return b;
	}
	
	public String space(int n){
		StringBuilder sb=new StringBuilder();
		while(n>0){
			sb.append("&nbsp;");
			n--;
		}
		return sb.toString();
	}
	
	private HashMap<String,String> getTableDesc(String table,DataSource ds){
		String sql="SELECT a.attnum, a.attname AS field, t.typname AS type, a.attlen AS length,	a.atttypmod AS lengthvar,"+
			       "a.attnotnull AS notnull,b.description AS comment "+
				   "FROM pg_class c, pg_attribute a "+
			       "LEFT OUTER JOIN pg_description b ON a.attrelid=b.objoid AND a.attnum = b.objsubid,pg_type t "+
				   "WHERE c.relname = '"+table+"'"+
			       " and a.attnum > 0 and a.attrelid = c.oid "+
				   " and a.atttypid = t.oid "+
			       " ORDER BY a.attnum";
		System.out.println(sql);
		HashMap<String,String> m=new HashMap<String,String>();
		try{
			Connection c=ds.getConnection();
			PreparedStatement p=c.prepareStatement(sql);
			ResultSet rs=p.executeQuery();
			String key,value;
			while(rs.next()){
				key=rs.getString("field");
				value=rs.getString("type")+","+
					  rs.getString("lengthvar")+","+
					  rs.getString("type")+","+
					  rs.getString("notnull");
				m.put(key, value);
			}
			rs.close();
			p.close();
			c.close();
		}catch(Exception e){
			e.printStackTrace();
		} 
		return m;
	}
	
	private boolean exist(String v,List<String> t){
		boolean b=false;
		for(int i=0;i<t.size();i++){
			if(v.equals(t.get(i))){
				b=true;
				break;
			}
		}
		return b;
	}
	
	private List<String> getTableList(DataSource ds){
		String sql="select relname as tn ,col_description(c.oid, 0) as COMMENTS from pg_class c "+
				" where relkind = 'r' and relname not like 'pg_%' and relname not like 'sql_%' order by relname";
		List<String> ls=new ArrayList<String>();
		try{
			Connection c=ds.getConnection();
			PreparedStatement p=c.prepareStatement(sql);
			ResultSet rs=p.executeQuery();
			while(rs.next()){
				ls.add(rs.getString("tn"));
			}
			rs.close();
			p.close();
			c.close();
		}catch(Exception e){
			e.printStackTrace();
		} 
		return ls;
	}
}
