package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    // Lista todas as disciplinas
    public List<DisciplinaDTO> getAllDisciplinas() {
        return disciplinaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca disciplina pelo id
    public Optional<DisciplinaDTO> getDisciplinaById(Long id) {
        return disciplinaRepository.findById(id)
                .map(this::convertToDto);
    }

    // Cria nova disciplina
    public DisciplinaDTO saveDisciplina(Disciplina disciplina) {
        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);
        return convertToDto(savedDisciplina);
    }

    // Edita disciplina
    public DisciplinaDTO updateDisciplina(Long id, Disciplina disciplinaDetails) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        disciplina.setNome(disciplinaDetails.getNome());
        disciplina.setCarga_horaria(disciplinaDetails.getCarga_horaria());
        disciplina.setCoordenacao(disciplinaDetails.getCoordenacao());

        Disciplina updatedDisciplina = disciplinaRepository.save(disciplina);
        return convertToDto(updatedDisciplina);
    }

    // Deleta disciplina
    public void deleteDisciplina(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        disciplinaRepository.delete(disciplina);
    }

    // Converte Disciplina para DisciplinaDTO
    private DisciplinaDTO convertToDto(Disciplina disciplina) {
        return DisciplinaDTO.builder()
                .idDisciplina(disciplina.getId_disciplina())
                .nome(disciplina.getNome())
                .cargaHoraria(disciplina.getCarga_horaria())
                .coordenacao(disciplina.getCoordenacao() != null ? 
                    CoordenacaoDTO.builder()
                        .idCoordenacao(disciplina.getCoordenacao().getId_coordenacao())
                        .nome(disciplina.getCoordenacao().getNome())
                        .build() : null)
                .build();
    }
}
