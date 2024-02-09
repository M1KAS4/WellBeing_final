package com.esprit.wellbeing_final.entities;

import java.util.List;


public interface RessourceService {
        List<Ressources> getAllRessources();
        Ressources getRessourceById(int id);
        void addRessource(Ressources ressource);
        void updateRessource(Ressources ressource);
        void deleteRessource(int id);

        void incrementRessourceViews(Ressources ressource);
    }
