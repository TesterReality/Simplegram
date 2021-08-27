package com.simplegram.repository;

import com.simplegram.entity.ChatRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID>, JpaSpecificationExecutor<ChatRoom> {
    Boolean existsById(String uuid);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ChatRoom getChatRoomById(String roomId);

    @Modifying
    @Query("update ChatRoom room set room.lastMessage =:lastMessage,room.dateLastMessage=:lastDate  where room=:roomObj")
    void updateRoomLastMessageData(@Param("roomObj") ChatRoom roomObj, @Param("lastMessage") String message, @Param("lastDate")LocalDateTime lastMessageDate);

}
