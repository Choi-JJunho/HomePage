package com.junho;

import com.junho.homepage.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class HomepageApplication {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(HomepageApplication.class, args);
    }
/*

    @Bean
    public ApplicationRunner applicationRunner() {
        SignUpRequest admin = SignUpRequest.builder()
                .account("admin")
                .password("1234")
                .name("admin")
                .email("admin@admin.com")
                .build();

        SignUpRequest manager = SignUpRequest.builder()
                .account("manager")
                .password("1234")
                .name("manager")
                .email("manager@admin.com")
                .build();

        SignUpRequest user = SignUpRequest.builder()
                .account("user")
                .password("1234")
                .name("user")
                .email("user@admin.com")
                .build();

        Member adminM = MemberMapper.INSTANCE.toMemberEntity(admin);
        adminM.setRoles(Collections.singletonList(
                Authority.builder()
                        .name(AuthorityType.ROLES.ADMIN)
                        .build())
        );

        Member managerM = MemberMapper.INSTANCE.toMemberEntity(manager);
        managerM.setRoles(Collections.singletonList(
                Authority.builder()
                        .name(AuthorityType.ROLES.MANAGER)
                        .build())
        );

        Member userM = MemberMapper.INSTANCE.toMemberEntity(user);
        userM.setRoles(Collections.singletonList(
                Authority.builder()
                        .name(AuthorityType.ROLES.USER)
                        .build())
        );

        memberRepository.save(adminM);
        memberRepository.save(managerM);
        memberRepository.save(userM);

        return args -> System.out.println("save init Members");
    }
*/

}
