import {Component, OnInit} from '@angular/core';
import {ProduitService} from "./service/produit.service";
import {ProduitModel} from "./model/produit.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'frontend';

  listeProduits: ProduitModel[]=[];

  constructor(private produitService: ProduitService) {

  }

  ngOnInit(): void {
    this.produitService.getProduits().subscribe(data=> {
      this.listeProduits=data;
    });
  }



}
