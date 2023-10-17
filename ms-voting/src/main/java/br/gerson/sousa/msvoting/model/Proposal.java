package br.gerson.sousa.msvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String creationDate;
    private String endingDate;
    private boolean result;

    public Proposal() {}

    public Proposal(String name, String description, String creationDate, String endingDate, boolean result) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.endingDate = endingDate;
        this.result = result;
    }

    public Proposal(Long id, String name, String description, String creationDate, String endingDate, boolean result) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.endingDate = endingDate;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
