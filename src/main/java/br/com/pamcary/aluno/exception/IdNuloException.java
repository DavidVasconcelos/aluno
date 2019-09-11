package br.com.pamcary.aluno.exception;

public class IdNuloException extends RuntimeException {

    private static final long serialVersionUID = -7349968708103244475L;

    public IdNuloException() {
        super();
    }

    public IdNuloException(final String message) {
        super(message);
    }
}
