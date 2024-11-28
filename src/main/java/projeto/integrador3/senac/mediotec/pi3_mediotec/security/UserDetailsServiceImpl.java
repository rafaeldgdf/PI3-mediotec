package projeto.integrador3.senac.mediotec.pi3_mediotec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return coordenadorRepository.findByEmail(email)
            .map(UserDetailsImpl::build)
            .or(() -> professorRepository.findByEmail(email).map(UserDetailsImpl::build))
            .or(() -> alunoRepository.findByEmail(email).map(UserDetailsImpl::build))
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
    }
}
