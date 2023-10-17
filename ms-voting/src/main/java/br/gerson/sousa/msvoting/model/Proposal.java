package br.gerson.sousa.msvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String creationDate;
    private String endingDate;
    private boolean result;

    public Proposal(String name, String description, String creationDate, String endingDate, boolean result) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.endingDate = endingDate;
        this.result = result;
    }
}
