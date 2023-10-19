package br.gerson.sousa.msvoting.ex;

public class TimeExceededException extends RuntimeException{
    public TimeExceededException(String message){
        super(message);
    }
}