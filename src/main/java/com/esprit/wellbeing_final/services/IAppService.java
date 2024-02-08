package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.Rendevouz;

import java.util.List;

public interface IAppService<T> {

    public void add(T t) ;
    public List<T> afficher();
    void update(T t,int id);
    public Rendevouz afficherById();

}
