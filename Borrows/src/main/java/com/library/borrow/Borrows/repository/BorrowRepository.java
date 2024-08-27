package com.library.borrow.Borrows.repository;

import com.library.borrow.Borrows.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository <Borrow,Integer> {
}
