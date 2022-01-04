package me.kodac.prototype.repository

import me.kodac.prototype.domain.SampleUser
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper {

    @Select(
        """
        select
          username, password, roles
        from
          users
        where
          username = #{username}
    """
    )
    fun findByUsername(username: String): SampleUser?

}