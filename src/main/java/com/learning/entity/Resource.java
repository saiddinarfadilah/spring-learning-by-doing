package com.learning.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@ToString
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "spring_mst_source")
public class Resource {
    @Id
    @Column(name = "id_source", nullable = false, columnDefinition = "VARCHAR(100)", length = 100)
    private String idSource;
    @Column(name = "source_name", nullable = false, columnDefinition = "VARCHAR(100)", length = 100)
    private String sourceName;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        idSource = UUID.randomUUID().toString();
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Resource resource = (Resource) object;
        return getIdSource() != null && Objects.equals(getIdSource(), resource.getIdSource());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
