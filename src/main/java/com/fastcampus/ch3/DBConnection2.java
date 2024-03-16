package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DBConnection2 {


    public static void main(String[] args) throws Exception {

    ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
    DataSource ds = ac.getBean(DataSource.class);
    Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

    System.out.println("conn = " + conn);
}
}
