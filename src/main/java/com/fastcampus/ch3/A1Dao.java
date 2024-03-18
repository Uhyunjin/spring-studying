package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
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
//            conn = ds.getConnection();
            //transaction 사용을 위해 datasource connection 변경
            conn = DataSourceUtils.getConnection(ds);

            pstmt = conn.prepareStatement("insert into a1 values(?,?)");
            pstmt.setInt(1, key);
            pstmt.setInt(2, value);

            //커넥션 찍어보기
            System.out.println(conn);
            //원래 각각의 insert마다 다른 값이 찍히는데
            // txmanager가 get connetion을 따로 하는데도 같은 값이 찍히도록 해준다

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
//            close(conn, pstmt);
            close(pstmt);
            DataSourceUtils.releaseConnection(conn, ds);
            //트랜잭션 매니저가 닫을지 아닌지를 판단
        }
    }

    private void close(AutoCloseable... acs) {
        for(AutoCloseable ac : acs)
            try{ if(ac!=null) ac.close(); } catch (Exception e) { e.printStackTrace();}
    }

    public void deleteAll() throws Exception{
        Connection conn = ds.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("delete from a1");
        pstmt.executeUpdate();
        close(pstmt);

    }
}
