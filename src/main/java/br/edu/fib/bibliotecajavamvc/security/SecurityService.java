package br.edu.fib.bibliotecajavamvc.security;

import org.springframework.security.core.userdetails.User;

public interface SecurityService {

    User findLoggedInUser();

}
