package bck_instapic.base.mapper;

import bck_instapic.base.entity.BaseEntity;

import java.util.UUID;

public interface BaseMapper<T extends BaseEntity> {
    void insert(T entity);
    void update(T entity);
    void delete(UUID id);
    T findById(UUID id);
}
