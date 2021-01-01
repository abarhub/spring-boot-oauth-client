package com.example.demo.service;

import com.example.demo.domain.Produit;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProduitService {

    private List<Produit> listeProduits=new ArrayList<>();

    @PostConstruct
    public void init(){
        Produit produit=new Produit();
        produit.setId(1L);
        produit.setNom("nom1");
        listeProduits.add(produit);
        produit=new Produit();
        produit.setId(2L);
        produit.setNom("nom2");
        listeProduits.add(produit);
    }

    public List<Produit> getListeProduit(){
        return ImmutableList.copyOf(listeProduits);
    }
}
