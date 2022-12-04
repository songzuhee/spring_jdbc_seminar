package com.umc.src.Web.repository;

import com.umc.src.Web.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
