package com.simplegram.repository;

import com.simplegram.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    Boolean existsById(String uuid);

    @Query("select b.name from ChatRoom b where b.id = :roomId")
    String getRoomNameById(@Param("roomId") String uuid);

}
