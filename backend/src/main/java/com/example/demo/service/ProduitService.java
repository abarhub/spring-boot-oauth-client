package com.example.demo.service;

import com.example.demo.domain.Produit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProduitService {

    private List<Produit> listeProduits=new ArrayList<>();

    public List<Produit> getListeProduit(){
        return Collections.unmodifiableList(listeProduits);
    }
}
