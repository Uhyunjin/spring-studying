package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class A1Dao {

    @Autowired
    DataSource ds;

    public int insert(int key, int value) throws Exception{
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement("insert into a1 values(?,?)");
            pstmt.setInt(1, key);
            pstmt.setInt(2, value);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    private void close(AutoCloseable... acs) {
        for(AutoCloseable ac : acs)
            try{ if(ac!=null) ac.close(); } catch (Exception e) { e.printStackTrace();}
    }
}
