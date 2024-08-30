package projeto.integrador3.senac.mediotec.pi3_mediotec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.TurmaDisciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.TurmaDisciplinaId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.TurmaDisciplinaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaDisciplinaService {

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    // Lista todas as TurmaDisciplinas
    public List<TurmaDisciplina> getAllTurmaDisciplinas() {
        return turmaDisciplinaRepository.findAll();
    }

    // Busca TurmaDisciplina pelo id (composto)
    public Optional<TurmaDisciplina> getTurmaDisciplinaById(TurmaDisciplinaId id) {
        return turmaDisciplinaRepository.findById(id);
    }

    // Cria nova TurmaDisciplina
    public TurmaDisciplina saveTurmaDisciplina(TurmaDisciplina turmaDisciplina) {
        return turmaDisciplinaRepository.save(turmaDisciplina);
    }

    // Edita TurmaDisciplina
    public TurmaDisciplina updateTurmaDisciplina(TurmaDisciplinaId id, TurmaDisciplina turmaDisciplinaDetails) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplina não encontrada"));

        turmaDisciplina.setProfessor(turmaDisciplinaDetails.getProfessor());

        return turmaDisciplinaRepository.save(turmaDisciplina);
    }

    // Deleta TurmaDisciplina
    public void deleteTurmaDisciplina(TurmaDisciplinaId id) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplina não encontrada"));
        turmaDisciplinaRepository.delete(turmaDisciplina);
    }
}
