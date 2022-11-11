package com.vladveretilnyk.workshop.application;

import com.vladveretilnyk.workshop.application.status.CompletionStatus;
import com.vladveretilnyk.workshop.application.status.PaymentStatus;
import com.vladveretilnyk.workshop.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "application")
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private CompletionStatus completionStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private double price;

    @ManyToMany(mappedBy = "applications")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "application")
    private List<ApplicationFeedback> feedbacks = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Application that = (Application) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}