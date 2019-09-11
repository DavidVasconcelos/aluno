package br.com.pamcary.aluno.exception;

public class AlunoInexistenteException extends RuntimeException {

    private static final long serialVersionUID = -5224135240990235997L;

    public AlunoInexistenteException() {
        super();
    }

    public AlunoInexistenteException(final String message) {
        super(message);
    }


}