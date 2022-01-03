package com.anhdungpham.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="resource")
@Getter
@Setter
@ToString
public class ResourceEntity extends BaseEntity implements Serializable {

    @Column(name = "title")
    private String title;

    @Column(name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}
