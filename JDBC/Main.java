package com.company;

import org.postgresql.util.PSQLException;

import java.sql.*;
import java.lang.*;
import java.util.*;

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
class Main {
    public static Connection getConnection() throws Exception{
        //Class.forName("org.postgresql.Driver");

        try {
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        String     connurl  = "DBURL";
        String     user     = "USER";
        String     password = "PW";
        Connection connection = DriverManager.getConnection(connurl, user, password);
        return connection;
    }

    public static void createTable(String tableName) throws Exception {
        try {
            Connection connection = Main.getConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sb= new StringBuilder();
            String sql=sb.append("CREATE TABLE IF NOT EXISTS "+tableName+"(")
                    .append("ID int,")
                    .append("NAME varchar(20)")
                    .append(")").toString();

            boolean re = stmt.execute(sql);
            System.out.println(tableName+" 테이블 생성 성공");

            stmt.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<VO> selectTable(String tableName) throws Exception {
        List<VO>result=new ArrayList<>();
        try {
            Connection connection = Main.getConnection();
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
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static VO selectOneTable(String tableName,String id,String name) throws Exception {
        VO result = new VO();
        try {
            Connection connection = Main.getConnection();
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
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void insertTable(String tableName,String id,String name) throws Exception {
        try {
            Connection connection = Main.getConnection();
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
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteTable(String tableName) throws Exception {
        try {
            Connection connection = Main.getConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sb= new StringBuilder();
            String sql=sb.append("DELETE FROM "+tableName).toString();
            int re= stmt.executeUpdate(sql);

            System.out.println(tableName+" 테이블 비움");

            stmt.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void dropTable(String tableName) throws Exception {
        try {
            Connection connection = Main.getConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sb= new StringBuilder();
            String sql=sb.append("DROP TABLE "+tableName).toString();
            boolean re = stmt.execute(sql);

            System.out.println(tableName+" 테이블을 삭제했습니다.");

            stmt.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        List<VO> resultUser = new ArrayList<>();
        List<VO> resultCompany = new ArrayList<>();
        VO result = new VO();
        createTable("OTW_USER");
        selectTable("OTW_USER");
        createTable("OTW_COMPANY");
        selectTable("OTW_COMPANY");

        insertTable("OTW_USER","1","otw1");
        insertTable("OTW_USER","2","otw2");
        resultUser=selectTable("OTW_USER");
        insertTable("OTW_COMPANY","1","sj1");
        insertTable("OTW_COMPANY","2","sj2");
        resultCompany=selectTable("OTW_COMPANY");

        result=selectOneTable("OTW_USER","1","otw1");
        System.out.println(result.toString());
        deleteTable("OTW_USER");
        selectTable("OTW_USER");
        deleteTable("OTW_COMPANY");
        selectTable("OTW_COMPANY");

        dropTable("OTW_USER");
        dropTable("OTW_COMPANY");
        selectTable("OTW_USER");
        for(int i=0;i<resultUser.size();i++){
            System.out.println(resultUser.get(i).getId()+"\t"+resultUser.get(i).getName());
        }
        for(int i=0;i<resultCompany.size();i++){
            System.out.println(resultCompany.get(i).getId()+"\t"+resultCompany.get(i).getName());
        }
    }
}




