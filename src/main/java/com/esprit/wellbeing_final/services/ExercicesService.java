package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.Exercice;

import java.util.List;

public interface ExercicesService {
    List<Exercice> getExercicesByCategory(String cat);
}
