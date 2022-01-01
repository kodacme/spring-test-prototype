package me.kodac.prototype.repository

import me.kodac.prototype.domain.Fruit
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface SampleMapper {

    @Select("""
        select 
          *
        from
          fruits
        where
          id = #{id}
    """)
    fun findById(id: String): Fruit
}