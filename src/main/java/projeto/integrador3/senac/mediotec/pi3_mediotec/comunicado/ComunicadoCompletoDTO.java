package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ComunicadoCompletoDTO {
    private Long id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataEnvio;
    private String remetente;
    private List<AlunoResumidoComTurmaDTO> alunos;
    private List<TurmaResumida2DTO> turmas;

    // Construtor vazio
    public ComunicadoCompletoDTO() {}

    // Construtor completo
    public ComunicadoCompletoDTO(Long id, String titulo, String conteudo, LocalDateTime dataEnvio, String remetente,
                                  List<AlunoResumidoComTurmaDTO> alunos, List<TurmaResumida2DTO> turmas) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataEnvio = dataEnvio;
        this.remetente = remetente;
        this.alunos = alunos;
        this.turmas = turmas;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public List<AlunoResumidoComTurmaDTO> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<AlunoResumidoComTurmaDTO> alunos) {
        this.alunos = alunos;
    }

    public List<TurmaResumida2DTO> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<TurmaResumida2DTO> turmas) {
        this.turmas = turmas;
    }
}
