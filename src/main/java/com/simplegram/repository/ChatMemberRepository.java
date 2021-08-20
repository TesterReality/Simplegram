package com.simplegram.repository;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {

    @Query("select b.idChat from ChatMember b where b.idUser = :uuid")
    List<String> findAllRoomIDByUserUUID(@Param("uuid") String userUUID);

    @Query("select b.idUser from ChatMember b where b.idChat = :chatRoomID")
    List<String> findUsersIDByChatRoomID(@Param("chatRoomID") String chatRoomUUID);

    @Query("select new java.lang.Boolean(count(*) > 0) from ChatMember c where c.idUser =(select u.id from User u where u.login = :userLogin)  AND  c.idChat=:chatRoomID ")
    Boolean isUserAlreadyExitsInChat(@Param("chatRoomID") String chatRoomUUID,@Param("userLogin") String userLogin);
}
