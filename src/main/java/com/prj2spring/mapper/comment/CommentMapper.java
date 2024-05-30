package com.prj2spring.mapper.comment;

import com.prj2spring.domain.comment.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
            SELECT id, board_id, member_id, comment, inserted
            FROM comment
            """)
    List<Comment> list(Integer boardId);
}
