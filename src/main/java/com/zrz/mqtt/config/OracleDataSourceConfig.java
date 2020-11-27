package com.zrz.mqtt.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//@Configuration
//指明了扫描dao层，并且给dao层注入指定的SqlSessionTemplate
//@MapperScan(basePackages = "com.wyait.manage.test1", sqlSessionTemplateRef  = "test1SqlSessionTemplate")
public class OracleDataSourceConfig {

    /**
     * 创建datasource对象
     * @return
     */
    //@Bean(name = "test1DataSource")
    //@ConfigurationProperties(prefix = "slave.datasource.test1")// prefix值必须是application.properteis中对应属性的前缀
    public DataSource test1DataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建sql工程
     * @param dataSource
     * @return
     * @throws Exception
     */
    //@Bean(name = "test1SqlSessionFactory")
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //对应mybatis.type-aliases-package配置
        bean.setTypeAliasesPackage("com.wyait.manage.pojo");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        //开启驼峰映射
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    /**
     * 配置事务管理
     * @param dataSource
     * @return
     */
    //@Bean(name = "test1TransactionManager")
    public DataSourceTransactionManager test1TransactionManager(@Qualifier("test1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * sqlSession模版，用于配置自动扫描pojo实体类
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    //@Bean(name = "test1SqlSessionTemplate")
    public SqlSessionTemplate test1SqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
