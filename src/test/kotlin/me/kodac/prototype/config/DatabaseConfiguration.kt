package me.kodac.prototype.config

import com.github.springtestdbunit.bean.DatabaseConfigBean
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@TestConfiguration
class DatabaseConfiguration {

    @Bean
    fun databaseDataSourceConnectionFactoryBean(): DatabaseDataSourceConnectionFactoryBean {
        return object : DatabaseDataSourceConnectionFactoryBean(dataSource()) {
            init {
                setDatabaseConfig(dbunitDatabaseConfig())
            }
        }
    }

    @Bean
    fun dbunitDatabaseConfig(): DatabaseConfigBean {
        return object : DatabaseConfigBean() {
            init {
                allowEmptyFields = true
                datatypeFactory = PostgresqlDataTypeFactory()
            }
        }
    }

    @Bean
    @Profile("test")
    fun dataSource(): DataSource {
        val container = ContainerHolder.getContainer()
        val ds = DriverManagerDataSource()
        ds.apply {
            url = container.jdbcUrl
            username = container.username
            password = container.password
            setDriverClassName(container.driverClassName)
        }
        return ds
    }

}