package vn.hoidanit.jobhunter.controller;

import vn.hoidanit.jobhunter.util.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.dto.LoginDTO;
import vn.hoidanit.jobhunter.domain.dto.ResLoginDTO;

@RestController
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {

        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        /*
         * Khi authenticate, nó sẽ gọi authenticationManagerBuilder lấy ra object (ở đây là ProviderManager) để authenticate
         * Trong ProviderManager, nó sẽ duyệt qua các provider để xem cái nào hỗ trợ kiểu token gửi lên ở đây sẽ là DaoAuthenticationProvider
         * Authenticate của AbstractUserDetailsAuthenticationProvider sẽ được gọi, tại đây nó sẽ lấy từ cache nếu đã đăng nhập cho nhanh
         * nếu không nó sẽ gọi tới DaoAuthenticationProvider để truy vấn từ db, sau khi lấy được user thành công, nó sẽ check xem account
         * có bị lock, disable, expire hay không, rồi mới đến check password
         * 
         */

        String accessToken = this.securityUtil.createToken(authentication);
        ResLoginDTO resLoginDTO = new ResLoginDTO();
        resLoginDTO.setAccessToken(accessToken);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().body(resLoginDTO);
    }
}
