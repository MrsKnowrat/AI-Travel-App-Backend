package com.example.travelactivityapp.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"User\"", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "username"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
    @NotBlank(message = "Email address is required") // checks email address to have an @ and .
    @Email(message = "Email address should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password", nullable = false, length = 255)
    @JsonIgnore // ensures that the password will not console log during login
    private String password;

    @Embedded
    @Column(name = "address", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL) // 1:1 relationship with Profile class and reflects that profile will be created when user is created.
    @JoinColumn(name = "profile_id", nullable = false, unique = true)
    private Profile profile;

    // help with infinite loop by loading itineraries but dont reload the user and their itineraries over and over
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // user owns the relationship
    private List<Itinerary> itineraries = new ArrayList<>();
}
