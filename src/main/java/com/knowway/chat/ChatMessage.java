package com.knowway.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knowway.common.entity.BaseEntity;
import com.knowway.departmentstore.domain.DepartmentStore;
import com.knowway.user.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Long messageId;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    @JoinColumn(name = "department_store_id")
    @ManyToOne
    @JsonIgnore
    private DepartmentStore departmentStore;

    @Column(name = "message_content", nullable = false, length = 255)
    private String messageContent;

    @Column(name = "message_nickname", nullable = false, length = 255)
    private String messageNickname;

    public void updateDepartmentStore(DepartmentStore departmentStore) {
        this.departmentStore = departmentStore;
    }

}