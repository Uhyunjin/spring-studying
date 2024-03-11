package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnection2Test {
    @Autowired
    DataSource ds;
    @Test
    public void insertUserTest() throws Exception {
        User user = new User("summery", "1234", "summer", "bbb@google.com", new Date(), "fb", new Date());
        int rowCnt = insertUser(user);

        assertTrue(rowCnt==1);
    }
    public int insertUser(User user)throws Exception{

        Connection conn = ds.getConnection();

        String sql = "insert into user values (?, ?, ?, ?, ?, ?, now())";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());

        int rowCnt = pstmt.executeUpdate();

        return rowCnt;
    }
    @Test
    public void main() throws Exception{

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn != null);
        //이 테스트가 성공했는지 assert문으로 확인한다
        //조건식이 true면 성공

    }

}