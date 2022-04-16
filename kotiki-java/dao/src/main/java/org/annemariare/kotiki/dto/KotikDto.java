package org.annemariare.kotiki.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.annemariare.kotiki.entity.KotikEntity;
import org.annemariare.kotiki.enums.Color;

import java.util.Date;

public class KotikDto {
    private final Long id;
    private final String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private final Date birthdayDate;
    private final String breed;
    private final Color color;
    private final OwnerDto owner;


    public KotikDto(KotikEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.birthdayDate = entity.getBirthdayDate();
        this.breed = entity.getBreed();
        this.color = entity.getColor();
        this.owner = OwnerDto.entityToDto(entity.getOwner());
    }

    public KotikDto(Long id, String name, Date birthdayDate, String breed, Color color, OwnerDto owner) {
        this.id = id;
        this.name = name;
        this.birthdayDate = birthdayDate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
    }

    public static KotikDto entityToDto(KotikEntity entity) {
        KotikDto dto = new KotikDto(entity);
        return dto;
    }

    public static KotikEntity dtoToEntity(KotikDto dto) {
        KotikEntity entity = new KotikEntity(dto.getId(), dto.getName(), dto.getBirthdayDate(), dto.getBreed(), dto.getColor(), OwnerDto.dtoToEntity(dto.getOwner()));
        return entity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public String getBreed() {
        return breed;
    }

    public Color getColor() {
        return color;
    }

    public OwnerDto getOwner() {
        return owner;
    }
}
