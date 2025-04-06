package com.jasper.Jasper_Report_application.repository;

import com.jasper.Jasper_Report_application.entities.BookInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInformationRepo extends JpaRepository<BookInformation, Integer> {

}
