package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

public class TurmaResumida2DTO {
    private Long id;
    private String nome;
    private String anoEscolar;
    private String turno;

    // Construtor vazio
    public TurmaResumida2DTO() {}

    // Construtor completo
    public TurmaResumida2DTO(Long id, String nome, String anoEscolar, String turno) {
        this.id = id;
        this.nome = nome;
        this.anoEscolar = anoEscolar;
        this.turno = turno;
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

    public String getAnoEscolar() {
        return anoEscolar;
    }

    public void setAnoEscolar(String anoEscolar) {
        this.anoEscolar = anoEscolar;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
