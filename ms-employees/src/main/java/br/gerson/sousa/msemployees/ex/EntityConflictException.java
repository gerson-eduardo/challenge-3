package br.gerson.sousa.msemployees.ex;

public class EntityConflictException extends RuntimeException{
    public EntityConflictException(String message){
        super(message);
    }
}
