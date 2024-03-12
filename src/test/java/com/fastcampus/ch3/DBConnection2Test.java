package com.fastcampus.ch3;

import com.mysql.cj.protocol.Resultset;
import org.junit.Test;
import org.junit.runner.Result;
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
import java.sql.ResultSet;
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
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt==1);
    }

    @Test
    public void selectUserTest() throws Exception{
        deleteAll();
        User user = new User("asdf", "1234", "summer", "bbb@google.com", new Date(), "fb", new Date());
        int rowCnt = insertUser(user);
        User user2 = selectUser("asdf");
        assertTrue(user.getId().equals("asdf"));

    }

    @Test
    public void deleteUserTest() throws Exception{

        deleteAll();

        int rowCnt = deleteUser("asdf");
        assertTrue(rowCnt == 0);


        User user = new User("asdf", "1234", "summer", "bbb@google.com", new Date(), "fb", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt==1);

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt==1);

        assertTrue(selectUser(user.getId())==null);

    }

    @Test
    public void updateUserTest() throws Exception{
        deleteAll();

        int rowCnt = updateUser("asdf", "winter");
        assertTrue(rowCnt == 0);


        User user = new User("asdf", "1234", "summer", "bbb@google.com", new Date(), "fb", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt==1);

        rowCnt = updateUser("asdf", "winter");
        assertTrue(rowCnt==1);

    }
    public int updateUser(String id, String name) throws Exception {

        Connection conn = ds.getConnection();
        String sql = "update user set name = ? where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, id);

        int rowCnt = pstmt.executeUpdate();

        return rowCnt;
    }

    public int deleteUser(String id) throws Exception{
        Connection conn = ds.getConnection();
        String sql = "delete from user where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,id);
        return pstmt.executeUpdate();
    }

    private void deleteAll() throws Exception{
        Connection conn = ds.getConnection();

        String sql = "delete from user";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
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
    public User selectUser(String id) throws Exception{
        Connection conn = ds.getConnection();
        String sql = "select * from user where id= ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery(); //select resultset(테이블)반환

        if(rs.next()){
            User user = new User();
            user.setId(rs.getString(1));
            user.setId(rs.getString(2));
            user.setId(rs.getString(3));
            user.setId(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setId(rs.getString(6));
            user.setReg_date(rs.getDate(7));
            return user;
        }
        return null;
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