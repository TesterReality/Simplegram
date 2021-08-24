package com.simplegram.repository;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.ChatMessage;
import com.simplegram.entity.ChatRoom;
import com.simplegram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.StyledEditorKit;
import java.util.List;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long>, JpaSpecificationExecutor<ChatMember> {
    List<ChatMember> findAllIdChatByIdUser(String userId);
    List<ChatMember> findAllIdUserByIdChat(String chatRoomId);
    List<ChatMember> findAllByIdUserAndIdChat(String userId,String chatRoomId);
    List<ChatMember> findAllByIdChatAndIdUserNotContaining(String chatRoomId,String userId);
}
