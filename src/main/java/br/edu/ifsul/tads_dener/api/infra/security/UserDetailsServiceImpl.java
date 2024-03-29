package br.edu.ifsul.tads_dener.api.infra.security;

import br.edu.ifsul.tads_dener.api.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//Classe responsável por gerenciar os usuários da aplicação. Em outras palavras, a aplicação fica responsável por esta gerência
@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRep.findByLogin(username);
//        if(username.equals("user")){
//            return User.withUsername(username).password(encoder.encode("user")).roles("USER").build();
//        }else if(username.equals("admin")){
//            return User.withUsername(username).password(encoder.encode("admin")).roles("USER", "ADMIN").build();
//        }
//        throw new UsernameNotFoundException("Usuario inexistente.");
    }

    //Utilizado para pegar o encode da senha e salvar na tabela User
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123"));
    }

}