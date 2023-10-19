package br.gerson.sousa.msvoting.ex;

public class EntityConflictException extends RuntimeException{
    public EntityConflictException(String message){
        super(message);
    }
}