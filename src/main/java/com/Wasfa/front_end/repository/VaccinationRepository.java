package com.Wasfa.front_end.repository;

import com.Wasfa.front_end.Entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Integer> {

    @Query(value = """
        SELECT v.* FROM vaccination v
        WHERE v.id_vaccin NOT IN (
            SELECT av.vaccination_id
            FROM animal_vaccination av
            WHERE av.animal_id = :idAnimal
        )
        AND (
            v.cible_sexe = 'TOUS'
            OR v.cible_sexe = (
                SELECT a.sexe
                FROM animal a
                WHERE a.id_animal = :idAnimal
                LIMIT 1
            )
        )
        AND (
            (
                SELECT TIMESTAMPDIFF(YEAR, a.date_naissance, CURDATE())
                FROM animal a
                WHERE a.id_animal = :idAnimal
                LIMIT 1
            ) >= v.cible_age
        )
        """, nativeQuery = true)
    List<Vaccination> findVaccinsNonInjectesParAnimal(@Param("idAnimal") String idAnimal);
}

