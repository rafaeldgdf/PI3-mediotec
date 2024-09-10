package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.emitente.Emitente;
import projeto.integrador3.senac.mediotec.pi3_mediotec.emitente.EmitenteRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.receptor.Receptor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.receptor.ReceptorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComunicadoService {

    @Autowired
    private ComunicadoRepository comunicadoRepository;

    @Autowired
    private EmitenteRepository emitenteRepository;

    @Autowired
    private ReceptorRepository receptorRepository;

    public ComunicadoDTO criarComunicado(ComunicadoDTO comunicadoDTO) {
        Emitente emitente = emitenteRepository.findById(comunicadoDTO.getEmitenteId()).orElseThrow();
        Receptor receptor = receptorRepository.findById(comunicadoDTO.getReceptorId()).orElseThrow();

        Comunicado comunicado = Comunicado.builder()
                .conteudo(comunicadoDTO.getConteudo())
                .dataEnvio(comunicadoDTO.getDataEnvio())
                .emitente(emitente)
                .receptor(receptor)
                .build();

        Comunicado salvo = comunicadoRepository.save(comunicado);
        comunicadoDTO.setIdComunicado(salvo.getId_comunicado());
        return comunicadoDTO;
    }

    public List<ComunicadoDTO> listarComunicadosPorProfessor(String cpf) {
        List<Comunicado> comunicados = comunicadoRepository.findByEmitente_Professor_Cpf(cpf);
        return comunicados.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ComunicadoDTO> listarComunicadosPorAluno(Long alunoId) {
        List<Comunicado> comunicados = comunicadoRepository.findByReceptor_Aluno_Id(alunoId);
        return comunicados.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ComunicadoDTO convertToDTO(Comunicado comunicado) {
        return ComunicadoDTO.builder()
                .idComunicado(comunicado.getId_comunicado())
                .conteudo(comunicado.getConteudo())
                .dataEnvio(comunicado.getDataEnvio())
                .emitenteId(comunicado.getEmitente().getId_emitente())
                .receptorId(comunicado.getReceptor().getId_receptor())
                .build();
    }
}
