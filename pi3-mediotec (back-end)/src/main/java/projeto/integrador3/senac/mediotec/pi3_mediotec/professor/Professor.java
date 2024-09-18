package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.usuario.Usuario;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professor")
public class Professor extends Usuario {
    
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull(message = "{usuario.cpf.notnull}")
    @Column(nullable = false, unique = true)
    private String cpf;
    
    @ManyToOne
    @JoinColumn(name = "id_coordenacao")
    private Coordenacao coordenacao;
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = true)
    private Set<Endereco> enderecos;
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = true)
    private Set<Telefone> telefones;
    
    @Column
    private boolean status;
    
    @JsonIgnore
    @OneToMany(mappedBy = "professor")
    private Set<TurmaDisciplinaProfessor> turmaDisciplinaProfessores; // Atualizado para refletir a nova entidade
    
    // Funções para configurar as bilateralidades
    public void addEndereco(Endereco endereco) {
        endereco.setProfessor(this); 
        this.enderecos.add(endereco);
    }

    public void addTelefone(Telefone telefone) {
        telefone.setProfessor(this); 
        this.telefones.add(telefone);
    }
    
    public void addTurmaDisciplinaProfessor(TurmaDisciplinaProfessor tdp) {
        tdp.setProfessor(this); 
        this.turmaDisciplinaProfessores.add(tdp);
    }
}

