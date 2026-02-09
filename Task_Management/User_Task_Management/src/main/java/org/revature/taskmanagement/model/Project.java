package org.revature.taskmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.revature.taskmanagement.enums.ProjectStatus;
import org.revature.taskmanagement.model.audit.Auditable;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
}
