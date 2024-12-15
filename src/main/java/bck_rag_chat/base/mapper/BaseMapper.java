package bck_rag_chat.base.mapper;

import bck_rag_chat.base.entity.BaseEntity;

import java.util.UUID;

public interface BaseMapper<T extends BaseEntity> {
    void insert(T entity);
    void update(T entity);
    void delete(UUID id);
    T findById(UUID id);
}
