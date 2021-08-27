package com.simplegram.repository;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.ChatMessage;
import com.simplegram.entity.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long>, JpaSpecificationExecutor<ChatMember> {

    /*
    @Query("select b.idChat from ChatMember b where b.idUser = :uuid")
    List<String> test1(@Param("uuid") String userUUID);*/


    //@Query(value = "select distinct c.chatRoom.id from ChatMessage c where c.chatRoom.id  IN (select b.idChat from ChatMember b where b.idUser = :uuid) GROUP BY c.chatRoom.id order by c.date desc", nativeQuery = true)
    // @Query(value = "select distinct c.chatRoom.id from ChatMessage c inner join ChatMember m on c.chatRoom.id = m.idChat where m.idUser = :uuid and max(c.date)")
    //работает @Query(value = "select distinct c from ChatMessage c where c.chatRoom.id  IN (select b.idChat from ChatMember b where b.idUser = :uuid) order by c.date desc")
    //@Query(value = "select distinct c.chatRoom.id from ChatMessage c inner join ChatRoom r on r.id = (select distinct c.chatRoom.id from ChatMessage c where c.chatRoom.id  IN ((select b.idChat from ChatMember b where b.idUser = :uuid) order by c.date desc")
    @Query(value = "select distinct c from ChatMessage c where c.chatRoom.id  IN (select b.idChat from ChatMember b where b.idUser = :uuid)  order by c.date desc")
    List<ChatMessage> test(@Param("uuid") String userUUID, Pageable pageable);

    @Query(value = "select c from ChatMessage c where c.chatRoom.id =:roomId order by c.date ASC ")
    List<ChatMessage> getAllMessageChat(@Param("roomId") String roomId, Pageable pageable);

    @Query("select c from ChatRoom c where c.id IN (select b.idChat from ChatMember b where b.idUser = :uuid) order by c.dateLastMessage desc")
    List<ChatRoom> getAllUserRoomsFromIdUser(@Param("uuid") String userUUID, Pageable pageable);


    List<ChatMember> findAllIdChatByIdUser(String userId, Pageable pageable);

    List<ChatMember> findAllIdUserByIdChat(String chatRoomId);

    List<ChatMember> findAllByIdUserAndIdChat(String userId, String chatRoomId);

    List<ChatMember> findAllByIdChatAndIdUserNotContaining(String chatRoomId, String userId);


}
