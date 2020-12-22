package com.kodstar.backend.service;

public interface Converter<Dto, Entity> {
    Dto convertToDTO(Entity entity);
    Entity convertToEntity(Dto dto);

}
