package me.kodac.prototype.repository

import me.kodac.prototype.domain.Producer
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ProducerMapper {

    @Insert("""
        insert into
          producers (id, address, lastname, firstname)
        values
          (#{id}, #{address}, #{lastname}, #{firstname})
    """)
    fun save(producer: Producer)
}