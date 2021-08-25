<%@ page import="static company.TomcatDBCPMain.*" %>
<%@ page import="company.DBCPConn" %><%--
  Created by IntelliJ IDEA.
  User: sjinc
  Date: 2021-08-25
  Time: 오전 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<%
    createTable("OTW_USER");
    selectTable("OTW_USER");
    createTable("OTW_COMPANY");
    selectTable("OTW_COMPANY");
    //테이블 삽입
    insertTable("OTW_USER", "1", "otw1");
    insertTable("OTW_USER", "2", "otw2");
    selectTable("OTW_USER");
    insertTable("OTW_COMPANY", "1", "sj1");
    insertTable("OTW_COMPANY", "2", "sj2");
    selectTable("OTW_COMPANY");

    //테이블 검색
    selectOneTable("OTW_USER", "1", "otw1");


    //테이블 비우기
    deleteTable("OTW_USER");
    selectTable("OTW_USER");
    deleteTable("OTW_COMPANY");
    selectTable("OTW_COMPANY");

    //테이블 삭제
    dropTable("OTW_USER");
    dropTable("OTW_COMPANY");
%>
</body>
</html>
