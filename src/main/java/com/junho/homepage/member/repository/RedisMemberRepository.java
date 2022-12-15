package com.junho.homepage.member.repository;

import com.junho.homepage.member.RedisMember;
import org.springframework.data.repository.CrudRepository;

public interface RedisMemberRepository extends CrudRepository<RedisMember, String> {
}
