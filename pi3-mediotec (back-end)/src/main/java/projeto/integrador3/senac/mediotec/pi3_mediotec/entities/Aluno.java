package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aluno")
public class Aluno extends Usuario {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula_aluno")
    private Long id_aluno;
    
    @Size(min = 11, max = 11, message = "{usuario.cpf.size}")
    @Column(nullable = true, unique = true)
    private String cpf;
    
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Endereco> enderecos;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Telefone> telefones;
    
    @JsonIgnore
    @ManyToOne 
   	private Coordenacao coordenacao;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "alunos", cascade = CascadeType.ALL)
    private Set<Turma> turmas;

    
    @JsonIgnore
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Presenca> presencas;
    
    
    //funcoes para configurar as bilateridades 
    public void addEndereco(Endereco endereco) {
        endereco.setAluno(this); 
        this.enderecos.add(endereco);
    }

    public void addTelefone(Telefone telefone) {
        telefone.setAluno(this); 
        this.telefones.add(telefone);
    }
    
    public void addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.getAlunos().add(this);
    }


}
