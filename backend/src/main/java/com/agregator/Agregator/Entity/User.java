    package com.agregator.Agregator.Entity;

    import com.agregator.Agregator.Enums.UserRole;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String email; // Email пользователя

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private UserRole role; // Роль пользователя
    }
