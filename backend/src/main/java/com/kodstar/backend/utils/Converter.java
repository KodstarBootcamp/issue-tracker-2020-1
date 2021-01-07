package com.kodstar.backend.utils;

public interface Converter<Dto, Entity> {
    Dto convertToDTO(Entity entity);
    Entity convertToEntity(Dto dto);

}
