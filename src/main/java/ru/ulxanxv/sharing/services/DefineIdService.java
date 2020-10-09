package ru.ulxanxv.sharing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.CannotCreateTransactionException;
import ru.ulxanxv.sharing.repositories.CredentialRepository;

public class DefineIdService implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    protected Long authenticatedId;

    protected final CredentialRepository credentialRepository;

    @Autowired
    public DefineIdService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent interactiveAuthenticationSuccessEvent) {
        User auth = ((User) interactiveAuthenticationSuccessEvent.getAuthentication().getPrincipal());

        if (auth != null) {
            String userName = auth.getUsername();
            try {
                this.authenticatedId = credentialRepository.findByName(userName).getId();

            } catch (CannotCreateTransactionException ignored) {}
        }
    }

}
