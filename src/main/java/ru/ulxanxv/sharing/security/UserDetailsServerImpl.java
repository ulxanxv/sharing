package ru.ulxanxv.sharing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import ru.ulxanxv.sharing.models.Credential;
import ru.ulxanxv.sharing.repositories.CredentialRepository;

@Service("detailsService")
public class UserDetailsServerImpl implements UserDetailsService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Credential credential = credentialRepository.findByName(login);

        UserBuilder userBuilder = null;

        if (credential != null) {
            userBuilder = org.springframework.security.core.userdetails.User.withUsername(login);
            userBuilder.password(credential.getPassword());
            userBuilder.authorities(credential.getRole());
        } else {
            throw new UsernameNotFoundException("User : " + login + " not found");
        }

        return userBuilder.build();

    }

}
