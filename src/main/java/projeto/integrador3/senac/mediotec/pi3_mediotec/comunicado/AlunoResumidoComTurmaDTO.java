package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;

public class AlunoResumidoComTurmaDTO {
    private Long id;
    private String nome;
    private String ultimoNome;
    private TurmaResumida2DTO turma;

    // Construtor vazio
    public AlunoResumidoComTurmaDTO() {}

    // Construtor completo
    public AlunoResumidoComTurmaDTO(Long id, String nome, String ultimoNome, TurmaResumida2DTO turma) {
        this.id = id;
        this.nome = nome;
        this.ultimoNome = ultimoNome;
        this.turma = turma;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public TurmaResumida2DTO getTurma() {
        return turma;
    }

    public void setTurma(TurmaResumida2DTO turma) {
        this.turma = turma;
    }
}
