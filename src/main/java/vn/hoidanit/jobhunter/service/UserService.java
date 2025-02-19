package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user){
        return this.userRepository.save(user);
    }   

    public void handleDeleteUser(long id){
        this.userRepository.deleteById(id);
    }

    public User fetchUserById(long id){
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()){
            return this.userRepository.findById(id).get();
        }
        return null;
    }

    public List<User> fetchAllUser(){
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User reqUser){
        User user = this.fetchUserById(reqUser.getId());
        if (user != null){
            user.setName(reqUser.getName());
            user.setEmail(reqUser.getEmail());
            user.setPassword(reqUser.getPassword());

            return this.userRepository.save(user);
        }
        return null;
    }
}
