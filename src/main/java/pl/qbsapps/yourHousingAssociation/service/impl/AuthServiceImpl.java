package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.qbsapps.yourHousingAssociation.exception.UserNotActivatedException;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.AccountStatus;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.request.AuthenticationRequest;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.security.JwtTokenUtils;
import pl.qbsapps.yourHousingAssociation.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenUtils jwtTokenUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public Authentication auth(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getLogin(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(authenticationRequest.getLogin())
                .orElseThrow(UserNotFoundException::new);

        if (user.getStatus().equals(AccountStatus.INACTIVE)) {
            throw new UserNotActivatedException();
        }

        return authentication;
    }

    @Override
    public String getAuthenticationToken(Authentication authentication) {
        return jwtTokenUtils.generateToken(authentication);
    }

    @Override
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
    }
}
