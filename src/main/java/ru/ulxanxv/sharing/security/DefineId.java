package ru.ulxanxv.sharing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.CannotCreateTransactionException;
import ru.ulxanxv.sharing.repositories.CredentialRepository;

public abstract class DefineId implements ApplicationListener<AuthenticationSuccessEvent> {

    protected Long authenticatedId;

    protected final CredentialRepository credentialRepository;

    @Autowired
    public DefineId(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        User auth = ((User) authenticationSuccessEvent.getAuthentication().getPrincipal());

        if (auth != null) {
            String userName = auth.getUsername();
            try {
                this.authenticatedId = credentialRepository.findByName(userName).getId();

            } catch (CannotCreateTransactionException ignored) {}
        }
    }

}
