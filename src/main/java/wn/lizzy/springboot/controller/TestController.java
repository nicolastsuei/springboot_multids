package wn.lizzy.springboot.controller;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  测试controller
 * TestController.java
 * @author cuilj  2017年3月9日--下午3:09:11
 *
 */
@RestController
public class TestController {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
    //没有指定为主数据源.
    @Autowired
    private DataSource dataSource;
   
    @Autowired
    @Qualifier("ds1")
    private DataSource dataSource1;
   
    @Autowired
    @Qualifier("ds2")
    private DataSource dataSource2;
   
    private JdbcTemplate jdbcTemplate;
   
   
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	logger.info("TestController.setJdbcTemplate()");
       jdbcTemplate.setDataSource(dataSource1);//设置dataSource
       this.jdbcTemplate = jdbcTemplate;
    }
 
    @RequestMapping("/get")
    public String get(){
       //观察控制台的打印信息.
    	logger.debug(dataSource.toString());
       return"ok";
    }
   
    @RequestMapping("/get1")
    public String get1(){
       //观察控制台的打印信息.
    	logger.debug(dataSource1.toString());
       return"ok.1";
    }
   
    @RequestMapping("/get2")
    public String get2(){
       //观察控制台的打印信息.
    	logger.debug(dataSource2.toString());
       return"ok.2";
    }
   
    @RequestMapping("/get3")
    public String get3(){
       //观察控制台的打印信息.
       JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource1);
       logger.debug(jdbcTemplate.getDataSource().toString());
       logger.debug(jdbcTemplate.toString());
        
       /*
         * Demo1只在test1中存在，test并没有此数据库;
         * 需要自己自己进行复制，不然会报错：Table 'test1.demo1' doesn't exist
         */
       String sql = "select *from Demo";
        jdbcTemplate.query(sql, new RowMapper<String>(){
 
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            	logger.info(rs.getLong("id")+"---"+rs.getString("name"));
                return"";
            }
 
        });
      
       return"ok.3";
    }
   
   
    @RequestMapping("/get4")
    public String get4(){
       //观察控制台的打印信息.
       System.out.println(jdbcTemplate.getDataSource());
       System.out.println(jdbcTemplate);
        
       /*
         * Demo1只在test1中存在，test并没有此数据库;
         * 需要自己自己进行复制，不然会报错：Table 'test1.demo1' doesn't exist
         */
       String sql = "select *from Demo1";
        jdbcTemplate.query(sql, new RowMapper<String>(){
 
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println(rs.getLong("id")+"---"+rs.getString("name"));
                return"";
            }
 
        });
      
       return"ok.4";
    }
}
