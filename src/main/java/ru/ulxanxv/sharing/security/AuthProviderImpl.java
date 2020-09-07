package ru.ulxanxv.sharing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ulxanxv.sharing.entities.Credential;
import ru.ulxanxv.sharing.repositories.CredentialRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name             = authentication.getName();
        Credential credential   = credentialRepository.findByName(name);

        if (credential == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        String password = authentication.getCredentials().toString();

        if (!passwordEncoder.matches(password, credential.getPassword())) {
            throw new BadCredentialsException("Bad credentials.");
        }

        return new UsernamePasswordAuthenticationToken(credential, null, new ArrayList<GrantedAuthority>());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
