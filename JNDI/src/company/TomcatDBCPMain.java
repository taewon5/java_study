package company;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.postgresql.ds.PGSimpleDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class VO{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

public class TomcatDBCPMain {
    public static void createTable(String tableName) throws Exception {
        try {
            Connection connection = DBCPConn.getConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sb= new StringBuilder();
            String sql=sb.append("CREATE TABLE IF NOT EXISTS "+tableName+"(")
                    .append("ID int,")
                    .append("NAME varchar(20)")
                    .append(")").toString();

            boolean re = stmt.execute(sql);
            System.out.println(tableName+" 테이블 생성 성공");

            stmt.close();
            //connection.close();
            DBCPConn.close();
        }
        catch (SQLException e) {
            System.out.println("테이블 생성 실패");
        }
    }
    public static List<VO> selectTable(String tableName) throws Exception {
        List<VO>result=new ArrayList<>();
        try {
            Connection connection = DBCPConn.getConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sb= new StringBuilder();
            String sql=sb.append("SELECT ID,NAME from "+tableName).toString();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("ID\tNAME");
            System.out.println("-------------------");

            if(rs.next()){
                do{
                    String id = rs.getString(1);
                    String name = rs.getString(2);
                    System.out.println(id + "\t" + name);
                    VO vo = new VO();
                    vo.setId(Integer.parseInt(id));
                    vo.setName(name);
                    result.add(vo);
                }while(rs.next());
            }
            else{
                System.out.println("빈 테이블");
            }
            System.out.println("-------------------");
            System.out.println();
            rs.close();
            stmt.close();
            //connection.close();
            DBCPConn.close();
        }
        catch (SQLException e) {
            System.out.println("테이블 검색 실패");
        }
        return result;
    }
    public static VO selectOneTable(String tableName,String id,String name) throws Exception {
        VO result = new VO();
        try {
            Connection connection = DBCPConn.getConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sb= new StringBuilder();
            String sql=sb.append("SELECT ID,NAME from "+tableName)
                    .append(" where ID="+id+"and NAME='"+name+"'").toString();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                result.setId(Integer.parseInt(rs.getString(1)));
                result.setName(rs.getString(2));
            }
            else{
            }
            rs.close();
            stmt.close();
            //connection.close();
            DBCPConn.close();
        }
        catch (SQLException e) {
            System.out.println("테이블 단일검색 실패");
        }
        return result;
    }
    public static void insertTable(String tableName,String id,String name) throws Exception {
        try {
            Connection connection = DBCPConn.getConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sb= new StringBuilder();
            String sql=sb.append("INSERT INTO "+tableName+"(ID,NAME) VALUES(?,?)").toString();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1,Integer.parseInt(id));
            psmt.setString(2,name);
            int r = psmt.executeUpdate();

            System.out.println(tableName+" insert 저장성공");

            psmt.close();
            stmt.close();
            //connection.close();
            DBCPConn.close();
        }
        catch (SQLException e) {
            System.out.println("insert 실패");
        }
    }
    public static void deleteTable(String tableName) throws Exception {
        try {
            Connection connection = DBCPConn.getConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sb= new StringBuilder();
            String sql=sb.append("DELETE FROM "+tableName).toString();
            int re= stmt.executeUpdate(sql);

            System.out.println(tableName+" 테이블 비움");

            stmt.close();
            //connection.close();
            DBCPConn.close();
        }
        catch (SQLException e) {
            System.out.println("delete 실패");
        }
    }
    public static void dropTable(String tableName) throws Exception {
        try {
            Connection connection = DBCPConn.getConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sb= new StringBuilder();
            String sql=sb.append("DROP TABLE "+tableName).toString();
            boolean re = stmt.execute(sql);

            System.out.println(tableName+" 테이블을 삭제했습니다.");

            stmt.close();
            //connection.close();
            DBCPConn.close();
        }
        catch (SQLException e) {
            System.out.println("테이블 삭제 실패");
        }
    }
    public static void main(String[] args) throws Exception {
        List<VO> resultUser = new ArrayList<>();
        List<VO> resultCompany = new ArrayList<>();
        VO result = new VO();
        //테이블 생성
        createTable("OTW_USER");
        selectTable("OTW_USER");
        createTable("OTW_COMPANY");
        selectTable("OTW_COMPANY");
        //테이블 삽입
        insertTable("OTW_USER","1","otw1");
        insertTable("OTW_USER","2","otw2");
        resultUser=selectTable("OTW_USER");
        insertTable("OTW_COMPANY","1","sj1");
        insertTable("OTW_COMPANY","2","sj2");
        resultCompany=selectTable("OTW_COMPANY");

        //테이블 검색
        result=selectOneTable("OTW_USER","1","otw1");
        System.out.println(result.toString());

        //테이블 비우기
        deleteTable("OTW_USER");
        selectTable("OTW_USER");
        deleteTable("OTW_COMPANY");
        selectTable("OTW_COMPANY");

        //테이블 삭제
        dropTable("OTW_USER");
        dropTable("OTW_COMPANY");
        selectTable("OTW_USER");
        Iterator iter1 = resultUser.iterator();
        Iterator iter2 = resultCompany.iterator();
        while(iter1.hasNext()){
            System.out.println(iter1.next());
        }
        while (iter2.hasNext()) {
            System.out.println(iter2.next());
        }
    }
}