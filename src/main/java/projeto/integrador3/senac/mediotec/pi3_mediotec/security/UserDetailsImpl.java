package projeto.integrador3.senac.mediotec.pi3_mediotec.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import projeto.integrador3.senac.mediotec.pi3_mediotec.usuario.Usuario;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;
    private String role;

    public UserDetailsImpl(String email, String senha, String role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public static UserDetailsImpl build(Usuario user) {
        return new UserDetailsImpl(
            user.getEmail(),
            user.getSenha(),
            user.getRole()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
