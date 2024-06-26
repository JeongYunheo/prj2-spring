package com.prj2spring.mapper.comment;

import com.prj2spring.domain.comment.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("""
            INSERT INTO comment
            (board_id, member_id, comment)
            VALUES (#{boardId}, #{memberId}, #{comment})
            """)
    int insert(Comment comment);

    @Select("""
            SELECT c.id, m.nick_name, c.member_id, c.inserted, c.comment
            FROM comment c JOIN member m on c.member_id = m.id
            WHERE board_id = #{boardId}
            ORDER BY id
            """)
    List<Comment> selectAllByBoardId(Integer boardId);

    @Delete("""
            DELETE FROM comment
            WHERE id = #{id}
            """)
    int deleteById(Integer id);

    @Select("""
            SELECT *
            FROM comment
            WHERE id = #{id}
            """)
    Comment selectById(Integer id);

    @Delete("""
            DELETE FROM comment
            WHERE board_id = #{boardId}
            """)
    int deleteBoardId(Integer boardId);

    @Delete("""
            DELETE FROM comment
            WHERE member_id=#{memberId}
            """)
    int deleteMemberId(Integer memberId);

    @Update("""
            UPDATE comment
            SET comment = #{comment}
            WHERE id = #{id}
            """)
    int update(Comment comment);
}
