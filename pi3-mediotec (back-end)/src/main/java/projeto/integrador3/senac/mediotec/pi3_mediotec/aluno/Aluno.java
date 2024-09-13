package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.presenca.Presenca;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.usuario.Usuario;


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
    private Long id;
    
    @Size(min = 1, max = 100, message = "{usuario.cpf.size}")
    @Column(nullable = true, unique = true)
    private String cpf;
    
    @Column
    private boolean status;
    
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Endereco> enderecos;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Telefone> telefones;
    

    @Builder.Default
    @ManyToMany(mappedBy = "alunos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Turma> turmas = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Presenca> presencas;
    
    @ManyToOne
    private Coordenacao coordenacao;
    
    
    
    // Métodos extras para configurar as bilateralidades
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

